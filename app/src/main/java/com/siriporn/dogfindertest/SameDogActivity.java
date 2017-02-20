package com.siriporn.dogfindertest;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.siriporn.dogfindertest.CustomAdapter.CustomAdapterDog;
import com.siriporn.dogfindertest.CustomAdapter.CustomAdapterSameDog;
import com.siriporn.dogfindertest.Models.Dog;
import com.siriporn.dogfindertest.Models.LostAndFound;
import com.siriporn.dogfindertest.Models.ResponseFormat;
import com.siriporn.dogfindertest.RESTServices.Implement.DogServiceImp;
import com.siriporn.dogfindertest.RESTServices.Interface.DogService;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.siriporn.dogfindertest.MainActivity.context;

public class SameDogActivity extends AppCompatActivity {
    String[] itemsPic;
    String[] pic;
    private Uri mImageCaptureUri;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_same_dog);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Context context = this;
        //get parameter
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
//                Intent intent = getIntent();
//                String positions = intent.getStringExtra("SelectRowDog");
//                pic = getIntent().getExtras().getStringArray("Pic");
                Dog chosen_dog = Cache.getInstance().get("chosen_dog");
//                int position = Integer.parseInt(positions);
//                mImageCaptureUri = Uri.fromFile(new File(Environment
//                        .getExternalStorageDirectory(),"TempPic.jpg"));
//                String uri = "http://161.246.6.240:10100/server" + pic[position];
//                File file = new File(mImageCaptureUri.getPath());
                try {
//                    Bitmap theBitmap =
//                            Glide.with(context)
//                                    .load(uri)
//                                    .asBitmap()
//                                    .into(231, 231)
//                                    .get();
//                    OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
//                    theBitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
//                    os.close();
//
//
//                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//                    MultipartBody.Part body = MultipartBody.Part.createFormData("path", file.getName(), requestFile);

//                    Response<ResponseFormat> response = DogServiceImp.getInstance().getSimilarDogFound("oil", body);
                    Response<ResponseFormat> response = DogServiceImp.getInstance().getSimilarDogFound(chosen_dog);

                    ArrayList<String> stockList = new ArrayList<>();
                    ArrayList<String> stockUri = new ArrayList<>();

                    final LostAndFound[] lostAndFounds = Converter.toPOJO(response.body().getPayload().get("lost_and_founds"), LostAndFound[].class);

                    for(LostAndFound lostAndFound: lostAndFounds) {
                        Dog dog = lostAndFound.getDog();
                        stockList.add(dog.getName());
                        stockUri.add(dog.getImages()[0]);
                    }

                    // INFORMATION convert List<String> to String[]
                    String[] items = new String[stockList.size()];
                    items = stockList.toArray(items);
                    // URI convert List<String> to String[]
                    itemsPic = new String[stockUri.size()];
                    itemsPic = stockUri.toArray(itemsPic);

                    GridView gridview = (GridView) findViewById(R.id.gridview);
                    CustomAdapterSameDog cus = new CustomAdapterSameDog( items, itemsPic);
                    runOnUiThread(new Runnable() {
                        GridView gridview;
                        CustomAdapterSameDog cus;

                        @Override
                        public void run() {
                            gridview.setAdapter(cus);
                        }

                        public Runnable setParams(GridView gridview, CustomAdapterSameDog cus){
                            this.gridview = gridview;
                            this.cus = cus;
                            return this;
                        }
                    }.setParams(gridview,cus));
                    //When Clicked
                    gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                                long arg3) {
                            // TODO Auto-generated method stub
                            //Toast.makeText(getActivity(),"row : "+ position,Toast.LENGTH_SHORT).show();
                            /**
                             * Send position for showing in Dog detail on next page (MyDogDetail)
                             */
                            Intent myIntent = new Intent(context, FoundPostDetail.class);
                            Cache.getInstance().put("lostAndFound", lostAndFounds[position]);
                            startActivity(myIntent);

                            String positions = Integer.toString(position);
                            Log.i("position",positions);
                                    /*Intent myIntent = new Intent(getActivity(), MyDogDetail.class);
                                    myIntent.putExtra("SelectRowDog", positions);
                                    myIntent.putExtra("Pic",itemsPic);
                                    startActivity(myIntent);*/
                        }

                    });

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        /**
         * same dog
         */


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

}
