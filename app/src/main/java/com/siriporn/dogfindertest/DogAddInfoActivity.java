package com.siriporn.dogfindertest;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.siriporn.dogfindertest.Models.Dog;
import com.siriporn.dogfindertest.Models.User;
import com.siriporn.dogfindertest.RESTServices.Implement.DogServiceImp;
import com.siriporn.dogfindertest.RESTServices.Implement.UserServiceImp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DogAddInfoActivity extends AppCompatActivity {

    private Uri mImageCaptureUri;
    private ImageView mImageView;
    private AlertDialog dialog;
    private Bitmap photo;
    private String name, breed, note;
    private Integer age;
    private File file;

    private static final int PICK_FROM_CAMERA = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private static final int PICK_FROM_FILE = 3;
    EditText nameText, ageText, noticeText;
    TextView breedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_add_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        captureImageInitialization();

        nameText = (EditText) findViewById(R.id.nameText);
        ageText = (EditText) findViewById(R.id.ageText);
        noticeText = (EditText) findViewById(R.id.noticeText);
        breedView = (TextView) findViewById(R.id.breedText);

        // select breed
        breed = getIntent().getStringExtra("breed_select");
        breedView.setText(String.valueOf(breed)); // Data breed

        Button button = (Button) findViewById(R.id.captureButton);
        mImageView = (ImageView) findViewById(R.id.captureView );

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }

    private void captureImageInitialization() {
        /**
         * a selector dialog to display two image source options
         */
        final String[] items = new String[] { "Take from camera",
                "Select from gallery" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, items);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Select Image");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) { // pick from
                // camera
                if (item == 0) {
                    /**
                     * To take a photo from camera, pass intent action
                     * ‘MediaStore.ACTION_IMAGE_CAPTURE‘ to open the camera app.
                     */
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    /**
                     * Also specify the Uri to save the image on specified path
                     * and file name. Note that this Uri variable also used by
                     * gallery app to hold the selected image path.
                     */

                    mImageCaptureUri = Uri.fromFile(new File(Environment
                            .getExternalStorageDirectory(), "tmp_avatar_"
                            + String.valueOf(System.currentTimeMillis())
                            + ".jpg"));
                    intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
                            mImageCaptureUri);
                    Log.d("UriImage",mImageCaptureUri.toString());



                    try {
                        intent.putExtra("return-data", true);

                        startActivityForResult(intent, PICK_FROM_CAMERA);
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    // pick from file
                    /**
                     * To select an image from existing files, use
                     * Intent.createChooser to open image chooser. Android will
                     * automatically display a list of supported applications,
                     * such as image gallery or file manager.
                     */
                    Intent intent = new Intent();

                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);

                    startActivityForResult(Intent.createChooser(intent,
                            "Complete action using"), PICK_FROM_FILE);
                }
            }
        });

        dialog = builder.create();
    }


    public class CropOptionAdapter extends ArrayAdapter<CropOption> {
        private ArrayList<CropOption> mOptions;
        private LayoutInflater mInflater;

        public CropOptionAdapter(Context context, ArrayList<CropOption> options) {
            super(context, R.layout.crop_selector, options);

            mOptions = options;

            mInflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup group) {
            if (convertView == null)
                convertView = mInflater.inflate(R.layout.crop_selector, null);

            CropOption item = mOptions.get(position);

            if (item != null) {
                ((ImageView) convertView.findViewById(R.id.iv_icon))
                        .setImageDrawable(item.icon);
                ((TextView) convertView.findViewById(R.id.tv_name))
                        .setText(item.title);

                return convertView;
            }

            return null;
        }
    }

    public class CropOption {
        public CharSequence title;
        public Drawable icon;
        public Intent appIntent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case PICK_FROM_CAMERA:
                /**
                 * After taking a picture, do the crop
                 */
                doCrop();

                break;

            case PICK_FROM_FILE:
                /**
                 * After selecting image from files, save the selected path
                 */
                mImageCaptureUri = data.getData();

                doCrop();

                break;

            case CROP_FROM_CAMERA:
                Bundle extras = data.getExtras();
                /**
                 * After cropping the image, get the bitmap of the cropped image and
                 * display it on imageview.
                 */
                if (extras != null) {
                    photo = extras.getParcelable("data");
                    mImageView.setImageBitmap(photo);
                }

                file = new File(mImageCaptureUri.getPath());
                /**
                 * Delete the temporary image
                 */
                if (file.exists())
                    file.delete();

                break;

        }
    }

    private void doCrop() {
        final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();
        /**
         * Open image crop app by starting an intent
         * ‘com.android.camera.action.CROP‘.
         */
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");

        /**
         * Check if there is image cropper app installed.
         */
        List<ResolveInfo> list = getPackageManager().queryIntentActivities(
                intent, 0);

        int size = list.size();

        /**
         * If there is no image cropper app, display warning message
         */
        if (size == 0) {

            Toast.makeText(this, "Can not find image crop app",
                    Toast.LENGTH_SHORT).show();

            return;
        } else {
            /**
             * Specify the image path, crop dimension and scale
             */
            intent.setData(mImageCaptureUri);

            intent.putExtra("outputX", 200);
            intent.putExtra("outputY", 200);
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", true);
            /**
             * There is posibility when more than one image cropper app exist,
             * so we have to check for it first. If there is only one app, open
             * then app.
             */

            if (size == 1) {
                Intent i = new Intent(intent);
                ResolveInfo res = list.get(0);

                i.setComponent(new ComponentName(res.activityInfo.packageName,
                        res.activityInfo.name));

                startActivityForResult(i, CROP_FROM_CAMERA);
            } else {
                /**
                 * If there are several app exist, create a custom chooser to
                 * let user selects the app.
                 */
                for (ResolveInfo res : list) {
                    final CropOption co = new CropOption();

                    co.title = getPackageManager().getApplicationLabel(
                            res.activityInfo.applicationInfo);
                    co.icon = getPackageManager().getApplicationIcon(
                            res.activityInfo.applicationInfo);
                    co.appIntent = new Intent(intent);

                    co.appIntent
                            .setComponent(new ComponentName(
                                    res.activityInfo.packageName,
                                    res.activityInfo.name));

                    cropOptions.add(co);
                }

                CropOptionAdapter adapter = new CropOptionAdapter(
                        getApplicationContext(), cropOptions);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Choose Crop App");
                builder.setAdapter(adapter,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                startActivityForResult(
                                        cropOptions.get(item).appIntent,
                                        CROP_FROM_CAMERA);
                            }
                        });

                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {

                        if (mImageCaptureUri != null) {
                            getContentResolver().delete(mImageCaptureUri, null,
                                    null);
                            mImageCaptureUri = null;
                        }
                    }
                });

                AlertDialog alert = builder.create();

                alert.show();
            }
        }
    }

    public void searchClicked(View view) {
        Intent intent = new Intent(this,SearchBreed.class);
        startActivity(intent);

    }

    public void nextClicked(View view) {

        name = nameText.getText().toString();
        breed = breedView.getText().toString();
        age = Integer.parseInt( ageText.getText().toString());
        note = noticeText.getText().toString();

        Dog dog = new Dog();
        dog.setName(name);
        dog.setBleed(breed);
        dog.setAge(age);
        dog.setNote(note);


/*
        //add new dog
        DogServiceImp.getInstance().newDog(dog , new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                SharedPreferences sp = DogFinderApplication.getContext().getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                Map<String, Object> dog_data = (Map<String, Object>) response.body().get("payload");
                editor.putString("token", dog_data.get("token").toString());
                editor.commit();
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });

        //Upload Image
        DogServiceImp.getInstance().uploadImage(dog, file, new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                SharedPreferences sp = DogFinderApplication.getContext().getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                Map<String, Object> dog_data = (Map<String, Object>) response.body().get("payload");
                editor.putString("token", dog_data.get("token").toString());
                editor.commit();
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
*/
        //Intent intent = new Intent(this,MainActivity.class);
        //startActivity(intent);

    }
}
