package app.android.thaihn.market.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import app.android.thaihn.market.Piece;
import app.android.thaihn.market.R;
import app.android.thaihn.market.adapter.ArrayAdapterRow;

public class DetailActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView title;
    private TextView descrip;
    private TextView numberphone;
    private Button call;
    private Button send;
    private Piece piece;

    public void initiView(){
        imageView = (ImageView) findViewById(R.id.detail_image);
        title = (TextView) findViewById(R.id.detail_title);
        descrip = (TextView) findViewById(R.id.detail_descrip);
        numberphone = (TextView) findViewById(R.id.detail_numberphone);
        call = (Button) findViewById(R.id.detail_call);
        send = (Button) findViewById(R.id.detail_send);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initiView();
      // / piece = (Piece) getIntent().getSerializableExtra("piece");
        /*Random random = new Random();
        final File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), ("anhtam"+random.nextInt(100000) +".png") );
        byte [] bytes = Base64.decode(Piece.cache.getImage(),Base64.DEFAULT);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fos.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread thread =  new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                file.delete();
            }
        });
        Picasso.with(this).load(file).resize(300,300).into(imageView);
        thread.start();*/
        byte [] bytes = Base64.decode(Piece.cache.getImage(),Base64.DEFAULT);
        //Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        Bitmap bitmap =  ArrayAdapterRow.decodeSampledBitmapFromResource(bytes,100,100);
        imageView.setImageBitmap(bitmap);
        title.setText( "Tiêu đề: "+Piece.cache.getTitle());
        descrip.setText("Miêu tả: "+Piece.cache.getDescrip());
        numberphone.setText("Số điện thoại: "+Piece.cache.getPhoneNumber());
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("smsto:"+Piece.cache.getPhoneNumber());
                Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                startActivity(it);

            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri= Uri.parse("tel:"+Piece.cache.getPhoneNumber());
                Intent i=new Intent(Intent.ACTION_CALL, uri);
                startActivity(i);
            }
        });
    }

}
