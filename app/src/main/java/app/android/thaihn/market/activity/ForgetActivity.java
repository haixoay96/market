package app.android.thaihn.market.activity;

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

public class ForgetActivity extends AppCompatActivity {
    private EditText editText;
    private TextView textView;
    private Toolbar toolbar;
    public void initiView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        editText = (EditText) findViewById(R.id.edt_mail_forget);
        textView = (TextView) findViewById(R.id.txt_send_forget);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        initiView();
        setSupportActionBar(toolbar);
        Firebase.setAndroidContext(this);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase ref = new Firebase("https://marketingonline.firebaseio.com");
                ref.resetPassword(editText.getText().toString(), new Firebase.ResultHandler() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(ForgetActivity.this, "Check mail to recieve pass",Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onError(FirebaseError firebaseError) {
                        Toast.makeText(ForgetActivity.this, firebaseError.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}
