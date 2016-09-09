package app.android.thaihn.market.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import app.android.thaihn.market.R;

public class ChangeActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private EditText oldpass;
    private EditText newpass;
    private EditText renewpass;
    private TextView doi;
    private Firebase firebase;
    private Toolbar toolbar;
    public void initiView(){
        oldpass = (EditText) findViewById(R.id.change_oldpass);
        newpass = (EditText) findViewById(R.id.change_newpass);
        renewpass = (EditText) findViewById(R.id.changerenewpass);
        doi = (TextView) findViewById(R.id.change_doi);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        initiView();
        Firebase.setAndroidContext(this);
        setSupportActionBar(toolbar);
        sharedPreferences = getSharedPreferences("account", MODE_PRIVATE);
        firebase = new Firebase("https://marketingonline.firebaseio.com");
        doi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newpass.getText().toString().equals(renewpass.getText().toString())){

                        firebase.changePassword(sharedPreferences.getString("user","Lỗi Thì Phải"), oldpass.getText().toString(), newpass.getText().toString(), new Firebase.ResultHandler() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(ChangeActivity.this, "Đổi Mật Khẩu Thành Công", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onError(FirebaseError firebaseError) {
                            Toast.makeText(ChangeActivity.this,firebaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    Toast.makeText(ChangeActivity.this, "Mật Khẩu Chưa Khớp", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
