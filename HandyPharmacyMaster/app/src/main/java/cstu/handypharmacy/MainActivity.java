package cstu.handypharmacy;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import cstu.handypharmacy.model.LoginUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "MainActivity";

    private Button btnUser;
    private Button btnPhar;
    private LoginUser loginUser = LoginUser.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        loginUser.setMobileId(Secure.getString(MainActivity.this.getContentResolver(), Secure.ANDROID_ID));

        btnUser = (Button) findViewById(R.id.userRegis);
        btnUser.setOnClickListener(this);

        btnPhar = (Button) findViewById(R.id.pharmacyRegis);
        btnPhar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.userRegis:
                loginUser.setUserType(0);
                Intent user = new Intent(v.getContext(), UserRegisActivity.class);
                startActivity(user);
                break;
            case R.id.pharmacyRegis:
                loginUser.setUserType(1);
                Intent phar = new Intent(v.getContext(), PharmacyRegisActivity.class);
                startActivity(phar);
                break;
        }
    }

}
