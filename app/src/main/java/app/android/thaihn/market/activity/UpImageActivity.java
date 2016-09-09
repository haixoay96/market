package app.android.thaihn.market.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Base64OutputStream;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;

import app.android.thaihn.market.Piece;
import app.android.thaihn.market.R;

public class UpImageActivity extends AppCompatActivity {
    private Firebase firebase;
    private ImageView imageView;
    private ImageView camera;
    private EditText title;
    private Spinner category;
    private EditText descrip;
    private EditText phonenumber;
    private TextView upLoad;
    private String linkImage = null;
    private String user;
    private ImageView imageDemo;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 2);
        }
    }



    public void initiView(){
        imageView = (ImageView) findViewById(R.id.up_image);
        title = (EditText) findViewById(R.id.up_title);
        category = (Spinner) findViewById(R.id.up_category);
        descrip= (EditText) findViewById(R.id.up_descrip);
        phonenumber = (EditText) findViewById(R.id.up_phonenumber);
        upLoad = (TextView) findViewById(R.id.up_dang);
        imageDemo = (ImageView) findViewById(R.id.up_image_demo);
        camera = (ImageView) findViewById(R.id.up_camera);
    }
    public byte[] bitmapToByte(Bitmap bitmap){
         //   Bitmap bitmap = BitmapFactory.decodeFile(path);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    public byte[] pathToByte(String path) {
       /* ImageView imageView = new ImageView(this);
        Picasso.with(this).load(path).resize(300,300).into(imageView);*/
        //Drawable drawable = Drawable.createFromPath(path);


    /* //   Bitmap bitmap = BitmapFactory.decodeFile(path);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;*/
        FileInputStream fileInputStream = null;

        File file = new File(path);

        byte[] bFile = new byte[(int) file.length()];

        //convert file into array of bytes
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fileInputStream.read(bFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bFile;
    }

    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_image);
        user = getIntent().getStringExtra("user");
        Firebase.setAndroidContext(this);
        firebase = new Firebase("https://marketingonline.firebaseio.com");
        initiView();
        ArrayList<String> stringArrayList = new ArrayList<String>();
        stringArrayList.add(Piece.TIVI);
        stringArrayList.add(Piece.LAPTOP);
        stringArrayList.add(Piece.DIENTHOAI);
        stringArrayList.add(Piece.TAINGHE);
        stringArrayList.add(Piece.LOA);
        stringArrayList.add(Piece.DOKHAC);

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner, stringArrayList);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(stringArrayAdapter);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,1);
            }
        });
        upLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(linkImage!=null&&!title.getText().toString().equals("")&&!phonenumber.getText().toString().equals("")&&!descrip.getText().toString().equals("")) {
                    Piece piece = new Piece(title.getText().toString(),category.getSelectedItem().toString(),linkImage,user,phonenumber.getText().toString(),descrip.getText().toString());
                    firebase.push().setValue(piece, new Firebase.CompletionListener() {
                        @Override
                        public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                            if (firebaseError != null) {
                                Toast.makeText(UpImageActivity.this, "UpLoad Error", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(UpImageActivity.this, "UpLoad Success", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    finish();
                }
                else {
                    Toast.makeText(UpImageActivity.this,"Chưa nhập đủ thông tin",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {

                String filePath = null;
                try {
                    filePath = getPath(this, data.getData());
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
               // byte[] bytes = pathToByte(filePath);
            //    linkImage = Base64.encodeToString(bytes, Base64.DEFAULT);
                final File file = new File(filePath);
                Picasso.with(this).load(file).resize(100, 100).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        imageDemo.setImageBitmap(bitmap);
                        byte [] bytes = bitmapToByte(bitmap);
                        linkImage = Base64.encodeToString(bytes, Base64.DEFAULT);
                        Toast.makeText(UpImageActivity.this,"Xong", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        Toast.makeText(UpImageActivity.this,"Loi", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
            }


            ///
            if (requestCode == 2 && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                byte[] bytes = bitmapToByte(imageBitmap);
                linkImage = bytes.toString();
                linkImage = Base64.encodeToString(bytes, Base64.DEFAULT);
                imageDemo.setImageBitmap(imageBitmap);
            }
        }
    }



}
