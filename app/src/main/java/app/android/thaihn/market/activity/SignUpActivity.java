package app.android.thaihn.market.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

import app.android.thaihn.market.R;

public class SignUpActivity extends AppCompatActivity {
    private  EditText user;
    private EditText pass;
    private EditText repass;
    private Toolbar toolbar;
    private TextView signup;
    private Firebase firebase;

    private int key;
    public void initiView(){
        user  = (EditText) findViewById(R.id.edt_user_sign);
        pass = (EditText) findViewById(R.id.edt_pass_sign);
        repass = (EditText) findViewById(R.id.edt_repass_sign);
        signup = (TextView) findViewById(R.id.txt_sign);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initiView();
        Firebase.setAndroidContext(this);
        firebase = new Firebase("https://marketingonline.firebaseio.com");
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pass.getText().toString().equals(repass.getText().toString())) {


                    firebase.createUser(user.getText().toString(), pass.getText().toString(), new Firebase.ValueResultHandler<Map<String, Object>>() {
                        @Override
                        public void onSuccess(Map<String, Object> result) {
                            Toast.makeText(SignUpActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.putExtra("user", user.getText().toString());
                            intent.putExtra("pass", pass.getText().toString());
                            setResult(1, intent);
                            finish();
                        }

                        @Override
                        public void onError(FirebaseError firebaseError) {
                            Toast.makeText(SignUpActivity.this, firebaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    Toast.makeText(SignUpActivity.this, "Mật Khẩu Không Khớp", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home : {
                Intent intent_to_home = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent_to_home);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    private void getData() {
        key = getIntent().getIntExtra("KEY", -1);
    }
}
