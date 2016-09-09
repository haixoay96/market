package app.android.thaihn.market.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.MissingFormatArgumentException;

import app.android.thaihn.market.Piece;
import app.android.thaihn.market.R;
import app.android.thaihn.market.adapter.ArrayAdapterRow;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Button btDienthoai;
    private Button btLaptop;
    private Button btTainghe;
    private Button btLoa;
    private Button btTivi;
    private Button btOhter;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private FloatingActionButton floatingActionButton;
    private TextView nameuser;
    private Button change;
    private Button created;
    private Button logout;
    private Button quit;
    private Button feedback;
    public static ArrayList<Piece> pieceArrayList;
    private  Firebase firebase;


    public void initiView() {
        btDienthoai = (Button) findViewById(R.id.app_dienthoai);
        btLaptop = (Button) findViewById(R.id.app_laptop);
        btTainghe = (Button) findViewById(R.id.app_tainghe);
        btLoa = (Button) findViewById(R.id.app_loa);
        btTivi = (Button) findViewById(R.id.app_tivi);
        btOhter = (Button) findViewById(R.id.app_other);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        change = (Button) findViewById(R.id.nav_change);
        created = (Button) findViewById(R.id.nav_created);
        logout = (Button) findViewById(R.id.nav_logout);
        quit = (Button) findViewById(R.id.nav_quit);
        feedback = (Button) findViewById(R.id.nav_feedback);
        nameuser = (TextView) findViewById(R.id.nav_user);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("account", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        pieceArrayList = new ArrayList<Piece>();
        initiView();
        setSupportActionBar(toolbar);
        nameuser.setText(sharedPreferences.getString("user", "Lỗi thì phải"));

        Firebase.setAndroidContext(this);
        firebase = new Firebase("https://marketingonline.firebaseio.com");
        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String title = dataSnapshot.child("title").getValue().toString();
                String category = dataSnapshot.child("category").getValue().toString();
                String image =  dataSnapshot.child("image").getValue().toString();
                String user = dataSnapshot.child("user").getValue().toString();
                String phoneNumber = dataSnapshot.child("phoneNumber").getValue().toString();
                String descrip = dataSnapshot.child("descrip").getValue().toString();
                    Piece piece = new Piece(title, category, image, user, phoneNumber,descrip);
                    pieceArrayList.add(piece);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChangeActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);

            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:haixoay96@gmail.com"));
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                finish();

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.remove("user");
                editor.remove("pass");
                editor.commit();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                finish();

            }
        });
        created.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreatedActivity.class);
                intent.putExtra("user", sharedPreferences.getString("user", "Vô danh"));
           //     intent.putExtra("piece", pieceArrayList);
                //Toast.makeText(MainActivity.this,sharedPreferences.getString("user","Vo danh"),Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(GravityCompat.START);
                startActivity(intent);
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UpImageActivity.class);
                intent.putExtra("user", sharedPreferences.getString("user", "Vô danh"));
                startActivity(intent);
            }
        });
        btTivi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DisplayListProductActivity.class);
                intent.putExtra(Piece.CATEGORY, Piece.TIVI);
            //    intent.putExtra("piece", pieceArrayList);
                startActivity(intent);
            }
        });
        btDienthoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DisplayListProductActivity.class);
                intent.putExtra(Piece.CATEGORY, Piece.DIENTHOAI);
             //   intent.putExtra("piece", pieceArrayList);
                startActivity(intent);
            }
        });
        btLaptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DisplayListProductActivity.class);
                intent.putExtra(Piece.CATEGORY, Piece.LAPTOP);
              //  intent.putExtra("piece", pieceArrayList);
                startActivity(intent);

            }
        });
        btTainghe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DisplayListProductActivity.class);
                intent.putExtra(Piece.CATEGORY, Piece.TAINGHE);
              //  intent.putExtra("piece", pieceArrayList);
                startActivity(intent);
            }
        });
        btLoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DisplayListProductActivity.class);
                intent.putExtra(Piece.CATEGORY, Piece.LOA);
             //   intent.putExtra("piece", pieceArrayList);
                startActivity(intent);
            }
        });
        btOhter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DisplayListProductActivity.class);
                intent.putExtra(Piece.CATEGORY, Piece.DOKHAC);
               // intent.putExtra("piece", pieceArrayList);
                startActivity(intent);
            }
        });
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){

            @Override
            public void onDrawerClosed(View v){
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

}
