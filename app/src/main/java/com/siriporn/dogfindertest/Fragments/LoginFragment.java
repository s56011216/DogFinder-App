package com.siriporn.dogfindertest.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.iid.FirebaseInstanceId;
import com.siriporn.dogfindertest.Cache;
import com.siriporn.dogfindertest.Converter;
import com.siriporn.dogfindertest.DogFinderApplication;
import com.siriporn.dogfindertest.MainActivity;
import com.siriporn.dogfindertest.Models.ResponseFormat;
import com.siriporn.dogfindertest.Models.User;
import com.siriporn.dogfindertest.R;
import com.siriporn.dogfindertest.RESTServices.Implement.DeviceServiceImp;
import com.siriporn.dogfindertest.RESTServices.Implement.UserServiceImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.data;
import static android.R.attr.name;
import static android.R.attr.tag;
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.facebook.login.widget.ProfilePictureView.TAG;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginFragment extends Fragment {

    Bundle bFacebookData;

    private TextView mTextDetails;
    private CallbackManager mCallbackManager;
    private AccessTokenTracker mTokenTracker;
    private ProfileTracker mProfileTracker;
    private FacebookCallback<LoginResult> mCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            Log.d("VIVZ", "onSuccess");
            Profile profile = Profile.getCurrentProfile();
            mTextDetails.setText(constructWelcomeMessage(profile));

            //get data from fb
            accessToken = loginResult.getAccessToken().getToken();
            token_exp = loginResult.getAccessToken().getExpires();
            Log.i("accessToken", accessToken);
            Log.i("accessToken_exp", token_exp.toString());

            GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    Log.i("LoginActivity", response.toString());
                    // Get facebook data from login
                    bFacebookData = getFacebookData(object);
                    connectToServer();
                }
            });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id, first_name, last_name, email, birthday");
            request.setParameters(parameters);
            request.executeAsync();
        }
        //end
        @Override
        public void onCancel() {
            Log.d("VIVZ", "onCancel");
        }

        @Override
        public void onError(FacebookException e) {
            Log.d("VIVZ", "onError " + e);
        }
    };

    private String id, firstname, lastname, name, email, accessToken, pic_uri;
    private Date birth_date, token_exp;

    public void connectToServer() {
        User user = new User();
        user.setFb_id(id);
        user.setFb_name(name);
        //user.setEmail(email);
        user.setBirth_date(birth_date);
        user.setFb_token(accessToken);
        user.setFb_token_exp(token_exp);
        user.setFb_profile_image(pic_uri);
        SharedPreferences sp = DogFinderApplication.getContext().getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit().clear();
        editor.commit();

        UserServiceImp.getInstance().login(user, new Callback<ResponseFormat>() {
            @Override
            public void onResponse(Call<ResponseFormat> call, Response<ResponseFormat> response) {

                SharedPreferences sp = DogFinderApplication.getContext().getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("token", response.body().getPayload().get("token").toString());
                editor.commit();

                //send id to profile fragment
                User user = Converter.toPOJO(response.body().getPayload().get("user_data"), User.class);
                DogFinderApplication.setUser(user);

                String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                DeviceServiceImp.getInstance().updateFCMToken(refreshedToken, new Callback<ResponseFormat>() {
                    @Override
                    public void onResponse(Call<ResponseFormat> call, Response<ResponseFormat> response) {
                        if (response.body().isSuccess()) {

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseFormat> call, Throwable t) {
                        if (1 == 1) {

                        }
                    }
                });

                Intent intent = new Intent(getActivity(),MainActivity.class);
                //intent.putExtra("id",user.getId());
                startActivity(intent);
            }
            @Override
            public void onFailure(Call<ResponseFormat> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }


    private Bundle getFacebookData(JSONObject object) {
        try {
            Bundle bundle = new Bundle();
            id = object.getString("id");
            //email = object.getString("email");
            firstname = object.getString("first_name");
            lastname = object.getString("last_name");
            name = firstname +" "+ lastname;


            SimpleDateFormat dataFormat = new SimpleDateFormat("MM/dd/yyyy");
            try {
                birth_date = dataFormat.parse(object.getString("birthday"));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=200");
                Log.i("profile_pic", profile_pic + "");
                pic_uri = profile_pic.toString();
                bundle.putString("profile_pic", profile_pic.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));

            return bundle;

        } catch (JSONException e) {
            Log.d(TAG, "Error parsing JSON");
            return null;
        }
    }

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCallbackManager = CallbackManager.Factory.create();
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());

        setupTokenTracker();
        setupProfileTracker();

        mTokenTracker.startTracking();
        mProfileTracker.startTracking();

    }

    private void setupTokenTracker() {
        mTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                Log.d("VIVZ", "" + currentAccessToken);
            }
        };
    }

    private void setupProfileTracker() {
        mProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                Log.d("VIVZ", "" + currentProfile);
                mTextDetails.setText(constructWelcomeMessage(currentProfile));
            }
        };
    }

     @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
         setupTextDetails(view);
         setupLoginButton(view);
     }

    @Override
    public void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
        mTextDetails.setText(constructWelcomeMessage(profile));
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode ,Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        mCallbackManager.onActivityResult(requestCode,resultCode,data);
    }

    private void setupTextDetails(View view) {
        mTextDetails = (TextView) view.findViewById(R.id.text_details);
    }

    private void setupLoginButton(View view) {
        LoginButton mButtonLogin = (LoginButton) view.findViewById(R.id.login_button);
        mButtonLogin.setFragment(this);
        mButtonLogin.setReadPermissions(Arrays.asList("user_birthday"));
        mButtonLogin.registerCallback(mCallbackManager, mCallback);
    }

    private String constructWelcomeMessage(Profile profile) {
        StringBuffer stringBuffer = new StringBuffer();
        if (profile != null) {
             stringBuffer.append(profile.getName());

        }
        return stringBuffer.toString();
    }



}
