package app.android.thaihn.market.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import app.android.thaihn.market.Piece;
import app.android.thaihn.market.R;

import static app.android.thaihn.market.adapter.ArrayAdapterRow.decodeSampledBitmapFromResource;

/**
 * Created by haixo on 4/17/2016.
 */
public class ArrayAdapterRow extends ArrayAdapter<Piece> {
    public static ArrayAdapterRow arrayAdapterRow;
    private ArrayList<Piece> pieceArrayList;
    private Context context;

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
    public static Bitmap decodeSampledBitmapFromResource(byte [] bytes,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);
    }
    public ArrayAdapterRow(Context context,List<Piece> objects) {
        super(context, -1, objects);
        this.pieceArrayList = (ArrayList<Piece>) objects;
        this.context =  context;
    }

    public void setPieceArrayList(ArrayList<Piece> pieceArrayList) {
        this.pieceArrayList = pieceArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.row_layout_list_product,parent, false);
        TextView title = (TextView) row.findViewById(R.id.row_title);
        title.setText(pieceArrayList.get(position).getTitle());
        ImageView imageView = (ImageView) row.findViewById(R.id.row_image);
        /*Random random = new Random();
        final File  file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), ("anhcache" + random.nextInt(100000)+".png") );
    //    byte [] bytes = Base64.decode(pieceArrayList.get(position).getImage(),Base64.DEFAULT);
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
        //  Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
               file.delete();


            }
        });*/
        try{
            byte [] bytes = Base64.decode(pieceArrayList.get(position).getImage(),Base64.DEFAULT);

            Bitmap bitmap  = decodeSampledBitmapFromResource(bytes,100,100);

            imageView.setImageBitmap(bitmap);
        }
        catch (OutOfMemoryError e){
            Toast.makeText(getContext(), "Lỗi ", Toast.LENGTH_SHORT).show();

        }

       // Picasso.with(context).load(file).resize(150,150).into(imageView);
       // thread.start();


        TextView descrip = (TextView) row.findViewById(R.id.row_descrip);
        descrip.setText(pieceArrayList.get(position).getDescrip());
        TextView phonenumber = (TextView) row.findViewById(R.id.row_phonenumber);
        phonenumber.setText("Liên hệ: "+ pieceArrayList.get(position).getPhoneNumber());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return row;
    }
}