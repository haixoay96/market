package app.android.thaihn.market.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

import app.android.thaihn.market.R;

public class LoginActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Toolbar toolbar;
    private EditText edt_user;
    private EditText edt_pass;
    private TextView txt_login;
    private TextView txt_forget;
    private TextView txt_sign_up;
    private String user;
    private String pass;

    private void initView() {
        // find view by id
        edt_pass = (EditText) findViewById(R.id.edt_pass_login);
        edt_user = (EditText) findViewById(R.id.edt_user_login);
        txt_login = (TextView) findViewById(R.id.txt_login);
        txt_forget = (TextView) findViewById(R.id.txt_forget_login);
        txt_sign_up = (TextView) findViewById(R.id.txt_sign_up_login);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txt_login.setOnClickListener(click_login);
        txt_sign_up.setOnClickListener(click_to_sign_up);
        txt_forget.setOnClickListener(click_to_forget);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Firebase.setAndroidContext(this);
        initView();
        setSupportActionBar(toolbar);
        sharedPreferences = getSharedPreferences("account", MODE_PRIVATE);
        editor = sharedPreferences.edit();



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(resultCode==1){
                edt_user.setText(data.getStringExtra("user"));
                edt_pass.setText(data.getStringExtra("pass"));
            }
        }
    }

    private View.OnClickListener click_to_forget = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this, ForgetActivity.class);
            intent.putExtra("KEY", 0);
            startActivity(intent);
        }
    };

    private View.OnClickListener click_to_sign_up = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivityForResult(intent, 1);
        }
    };

    private View.OnClickListener click_login = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            user = edt_user.getText().toString();
            pass = edt_pass.getText().toString();
            Firebase ref = new Firebase("https://marketingonline.firebaseio.com");
            ref.authWithPassword(user, pass, new Firebase.AuthResultHandler() {
                @Override
                public void onAuthenticated(AuthData authData) {
                    Toast.makeText(LoginActivity.this,"User ID: " + authData.getUid() + ", Provider: " + authData.getProvider(), Toast.LENGTH_SHORT ).show();
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class );
                    intent.putExtra("user",edt_user.getText().toString());
                    intent.putExtra("pass", edt_pass.getText().toString());
                    editor.remove("user");
                    editor.remove("pass");
                    editor.commit();
                    editor.putString("user", edt_user.getText().toString());
                    editor.putString("pass", edt_pass.getText().toString());
                    editor.commit();
                    startActivity(intent);
                    finish();
                }
                @Override
                public void onAuthenticationError(FirebaseError firebaseError) {
                    Toast.makeText(LoginActivity.this, firebaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        }
    };
}
