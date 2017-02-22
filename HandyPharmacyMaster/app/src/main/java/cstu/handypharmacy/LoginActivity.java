package cstu.handypharmacy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import cstu.handypharmacy.controller.Address;
import cstu.handypharmacy.controller.RegisMember;
import cstu.handypharmacy.model.LoginUser;

public class LoginActivity extends Activity implements View.OnClickListener {

    private String TAG = "LoginActivity";

    private TextView Email = null;
    private TextView Password = null;
    private TextView RegisLink;
    private Button LoginBtn;
    private Intent main;

    private LoginUser loginUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        main = new Intent(LoginActivity.this, MainActivity.class);

        loginUser = LoginUser.getInstance();

        Log.i(TAG, "Email: " + LoginUser.getInstance());


        RegisLink = (TextView) findViewById(R.id.RegisLink);
        RegisLink.setOnClickListener(this);

        LoginBtn = (Button) findViewById(R.id.LoginBtn);
        LoginBtn.setOnClickListener(this);

        Email = (TextView) findViewById(R.id.LoginEmail);
        Password = (TextView) findViewById(R.id.LoginPassword);

        final ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Loading system data...");
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();

        Address.getProvince(new Address.NoParaDataCallback() {
            @Override
            public void onFinish() {
                progress.dismiss();
            }
        });

    }

    private PopupWindow pw;
    private void initiatePopupWindow(CharSequence a) {
        try {
            //We need to get the instance of the LayoutInflater, use the context of this activity
            LayoutInflater inflater = (LayoutInflater) LoginActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //Inflate the view from a predefined XML layout
            View layout = inflater.inflate(R.layout.popup_layout,
                    (ViewGroup) findViewById(R.id.popup_element));
            // create a 300px width and 470px height PopupWindow
            pw = new PopupWindow(layout, 1300, 400, true);
            pw.showAtLocation(layout, Gravity.CENTER, 0, 0);

            TextView mResultText = (TextView) layout.findViewById(R.id.server_status_text);
            mResultText.setText(a);
            Button cancelButton = (Button) layout.findViewById(R.id.end_data_send_button);
            cancelButton.setOnClickListener(cancel_button_click_listener);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener cancel_button_click_listener = new View.OnClickListener() {
        public void onClick(View v) {
            pw.dismiss();
        }
    };

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.RegisLink:
                startActivity(main);
                break;

            case R.id.LoginBtn:
                final ProgressDialog progress = new ProgressDialog(v.getContext());
                progress.setMessage("Wait for check registed...");
                progress.setIndeterminate(true);
                progress.setCancelable(false);
                progress.show();

                loginUser.setUsrEmail(Email.getText().toString());

                RegisMember.chkRegis(loginUser, new RegisMember.DataCallback() {
                    @Override
                    public void onFinish(LoginUser loginUser) {
                        progress.dismiss();

                        String inputPwd = Password.getText().toString();

                        if (loginUser.getUsrEmail().length() == 0 || inputPwd.length() == 0) {
                            initiatePopupWindow("Please Enter Username or Password");
                            //Toast.makeText(getApplicationContext(), "Please input Username or Password", Toast.LENGTH_SHORT).show();
                        } else if (inputPwd.equals(loginUser.getUsrPwd())) {
                            if(loginUser.getUserType() == 0) {
                                Intent pharmacy = new Intent(LoginActivity.this, PinEntryView.class);
                                startActivity(pharmacy);
                                HomeActivity.getemail(loginUser.getUsrEmail());
                            } else {
                                if(loginUser.getStatus() == 0){
                                    initiatePopupWindow("Please Wait for approve by Admin");
                                    //Toast.makeText(getApplicationContext(), "Please Wait for approve by Admin", Toast.LENGTH_SHORT).show();
                                } else{
                                    Intent pharmacy = new Intent(LoginActivity.this, PinEntryView.class);
                                    startActivity(pharmacy);
                                    HomePharmacyActivity.getemail(loginUser.getUsrEmail());
                                }
                            }
                        } else {
                            initiatePopupWindow("Incorrect Username or Password");
                            //Toast.makeText(getApplicationContext(), "Incorrect Username or Password", Toast.LENGTH_SHORT).show();

                        }

                    }
                });
                break;

        }
    }
}
