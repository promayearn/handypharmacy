package cstu.handypharmacy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.*;
import android.widget.Toast;

import cstu.handypharmacy.controller.RegisMember;
import cstu.handypharmacy.model.LoginUser;

public class PinEntryView extends Activity {

    private static String TAG = "PinEntryView";

    String userEntered;

    String EnterPwd;
    String ReEnterPwd;

    final int PIN_LENGTH = 4;
    boolean keyPadLockedFlag = false;
    Context appContext;

    TextView statusView;

    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    Button button9;
    Button btnOK;
    EditText passwordInput;
    ImageButton imageView2;
    LoginUser loginUser;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appContext = this;
        userEntered = "";

        EnterPwd = "";
        ReEnterPwd = "";

        loginUser = LoginUser.getInstance();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.set_pin);

        //Typeface xpressive=Typeface.createFromAsset(getAssets(), "fonts/XpressiveBold.ttf");

        statusView = (TextView) findViewById(R.id.statusview);
        passwordInput = (EditText) findViewById(R.id.editText);

        if (loginUser.getStatLog() == 1) {
            statusView.setText("Enter your PIN");
        }


        button0 = (Button) findViewById(R.id.button0);
        //button0.setTypeface(xpressive);
        button0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (chkText())
                    passwordInput.setText(passwordInput.getText() + "0");
            }
        });

        button1 = (Button) findViewById(R.id.button1);
        //button1.setTypeface(xpressive);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (chkText())
                    passwordInput.setText(passwordInput.getText() + "1");
            }
        });

        button2 = (Button) findViewById(R.id.button2);
        //button2.setTypeface(xpressive);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (chkText())
                    passwordInput.setText(passwordInput.getText() + "2");
            }
        });


        button3 = (Button) findViewById(R.id.button3);
        //button3.setTypeface(xpressive);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (chkText())
                    passwordInput.setText(passwordInput.getText() + "3");
            }
        });

        button4 = (Button) findViewById(R.id.button4);
        //button4.setTypeface(xpressive);
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (chkText())
                    passwordInput.setText(passwordInput.getText() + "4");
            }
        });

        button5 = (Button) findViewById(R.id.button5);
        //button5.setTypeface(xpressive);
        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (chkText())
                    passwordInput.setText(passwordInput.getText() + "5");
            }
        });

        button6 = (Button) findViewById(R.id.button6);
        //button6.setTypeface(xpressive);
        button6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (chkText())
                    passwordInput.setText(passwordInput.getText() + "6");
            }
        });

        button7 = (Button) findViewById(R.id.button7);
        //button7.setTypeface(xpressive);
        button7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (chkText())
                    passwordInput.setText(passwordInput.getText() + "7");
            }
        });

        button8 = (Button) findViewById(R.id.button8);
        //button8.setTypeface(xpressive);
        button8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (chkText())
                    passwordInput.setText(passwordInput.getText() + "8");
            }
        });

        button9 = (Button) findViewById(R.id.button9);
        //button9.setTypeface(xpressive);
        button9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (chkText())
                    passwordInput.setText(passwordInput.getText() + "9");
            }
        });

        btnOK = (Button) findViewById(R.id.btnOK);
        //buttonDelete.setTypeface(xpressive);
        btnOK.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                if (passwordInput.length() > 3) {
                    if (passwordInput.length() == 4) {
                        if (loginUser.getPinn().equals("")) {
                            checkPinWithoutLogin(v);
                        } else {
                            checckPinWithLogin(v);
                        }

                    }
                } else {
                    initiatePopupWindow("Number Mininum length is 4");
                    //Toast.makeText(getApplicationContext(), "Number Mininum length is 4", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //Back space passcode
        imageView2 = (ImageButton) findViewById(R.id.imageView2);
        imageView2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (passwordInput.length() > 0) {
                    userEntered = "" + passwordInput.getText();
                    userEntered = userEntered.substring(0, userEntered.length() - 1);
                    passwordInput.setText(userEntered);
                } else {
                    initiatePopupWindow("Please enter Passcode");
                    //Toast.makeText(getApplicationContext(), "Please enter Passcode", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean chkText() {
        if (passwordInput.getText().length() >= 4) {
            return false;
        } else {
            return true;
        }
    }


    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        //App not allowed to go back to Parent activity until correct pin entered.
        return;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.activity_pin_entry_view, menu);
        return true;
    }


    private class LockKeyPadOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            for (int i = 0; i < 2; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            statusView.setText("");

            //Roll over
            passwordInput.setText("");
            ;

            userEntered = "";

            keyPadLockedFlag = false;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }

    protected void checckPinWithLogin(View v) {
        if (loginUser.getPinn().equals(passwordInput.getText().toString())) {
            if (LoginUser.getInstance().getUserType() == 0) {
                Log.i(TAG, "Go Home");
                Intent Pinsetting = new Intent(v.getContext(), HomeActivity.class);
                startActivity(Pinsetting);
            } else {
                Log.i(TAG, "Go HomePhar");
                Intent Pinsetting = new Intent(v.getContext(), HomePharmacyActivity.class);
                startActivity(Pinsetting);
            }
        } else {
            initiatePopupWindow("Incorrect PIN");
            //Toast.makeText(getApplicationContext(), "Incorrect PIN", Toast.LENGTH_SHORT).show();
        }
    }

    protected void checkPinWithoutLogin(final View v) {
        if (EnterPwd.equals("")) {
            EnterPwd = passwordInput.getText().toString();
            Toast.makeText(getApplicationContext(), "Confirm your Passcode again", Toast.LENGTH_SHORT).show();
            passwordInput.setText("");
        } else {
            ReEnterPwd = passwordInput.getText().toString();
            ;

            if (EnterPwd.equals(ReEnterPwd)) {
                if (loginUser.getStatLogin() == 1) {

                    // ตรวจสอบสว่า pin ที่ ดึงมาจาก server กับ pin ที่กรอก ตรงกันไหม
                    if (passwordInput.getText().toString().equals(loginUser.getPinn())) {

                        if (loginUser.getStatLog() == 1) {

                            Log.i(TAG, "Usertype: " + LoginUser.getInstance().getUserType());

                            if (LoginUser.getInstance().getUserType() == 0) {
                                Log.i(TAG, "Go Home");
                                Intent Pinsetting = new Intent(v.getContext(), HomeActivity.class);
                                startActivity(Pinsetting);
                            } else {
                                Log.i(TAG, "Go HomePhar");
                                Toast.makeText(this, "Please Wait for Approval by Admin", Toast.LENGTH_SHORT).show();
                                Intent Pinsetting = new Intent(v.getContext(), LoginActivity.class);
                                startActivity(Pinsetting);
                            }
                        } else {
                            Toast.makeText(v.getContext(), "Fail to register!! ", Toast.LENGTH_LONG);
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Check again!!!", Toast.LENGTH_SHORT).show();
                        passwordInput.setText("");
                    }

                } else { //if(loginUser.getStatLogin() == 1) {

                    final ProgressDialog progress = new ProgressDialog(v.getContext());
                    progress.setMessage("Saving...");
                    progress.setIndeterminate(true);
                    progress.setCancelable(false);
                    progress.show();

                    RegisMember.reggisPinn(passwordInput.getText().toString(), new RegisMember.DataCallback() {
                        @Override
                        public void onFinish(LoginUser loginUser) {
                            if (loginUser.getStatLog() == 1) {
                                if (LoginUser.getInstance().getUserType() == 0) {
                                    Intent Pinsetting = new Intent(v.getContext(), HomeActivity.class);
                                    startActivity(Pinsetting);
                                } else {
                                    Intent Pinsetting = new Intent(v.getContext(), LoginActivity.class);
                                    Toast.makeText(v.getContext(), "Please Wait for Approval by Admin", Toast.LENGTH_LONG);
                                    startActivity(Pinsetting);
                                }
                            } else {
                                Toast.makeText(v.getContext(), "Fail to register!!", Toast.LENGTH_LONG);
                            }
                            progress.dismiss();
                        }
                    });
                } //if(loginUser.getStatLogin() == 1) {
            } else {

                // reset ค่าทุกครั้งเมื่อกรอก รหัสอันที่สองไม่ตรง
                EnterPwd = "";
                ReEnterPwd = "";
                initiatePopupWindow("Not same, Please try again...");
                //Toast.makeText(getApplicationContext(), " Not same, Please try again... ", Toast.LENGTH_SHORT).show();
            }
            passwordInput.setText("");
        }

    }

    public void reCheckPasscode(final String pass) {

        btnOK.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {

                if (passwordInput.length() > 3) {

                    if (passwordInput.length() == 4) {

                        if (passwordInput.getText().toString().equals(pass)) {

                            if (loginUser.getStatLogin() == 1) {

                                // ตรวจสอบสว่า pin ที่ ดึงมาจาก server กับ pin ที่กรอก ตรงกันไหม
                                if (passwordInput.getText().toString().equals(loginUser.getPinn())) {

                                    if (loginUser.getStatLog() == 1) {

                                        Log.i(TAG, "Usertype: " + LoginUser.getInstance().getUserType());

                                        if (LoginUser.getInstance().getUserType() == 0) {
                                            Log.i(TAG, "Go Home");
                                            Intent Pinsetting = new Intent(v.getContext(), HomeActivity.class);
                                            startActivity(Pinsetting);
                                        } else {
                                            Log.i(TAG, "Go HomePhar");
                                            Intent Pinsetting = new Intent(v.getContext(), HomePharmacyActivity.class);
                                            startActivity(Pinsetting);
                                        }
                                    } else {
                                        Toast.makeText(v.getContext(), "Fail to register!! ", Toast.LENGTH_LONG);
                                    }

                                } else {
                                    Toast.makeText(getApplicationContext(), "Check again!!!", Toast.LENGTH_SHORT).show();
                                    passwordInput.setText("");
                                }

                            } else { //if(loginUser.getStatLogin() == 1) {

                                final ProgressDialog progress = new ProgressDialog(v.getContext());
                                progress.setMessage("Saving...");
                                progress.setIndeterminate(true);
                                progress.setCancelable(false);
                                progress.show();

                                RegisMember.reggisPinn(passwordInput.getText().toString(), new RegisMember.DataCallback() {
                                    @Override
                                    public void onFinish(LoginUser loginUser) {
                                        if (loginUser.getStatLog() == 1) {
                                            if (LoginUser.getInstance().getUserType() == 0) {
                                                Intent Pinsetting = new Intent(v.getContext(), HomeActivity.class);
                                                startActivity(Pinsetting);
                                            } else {
                                                Intent Pinsetting = new Intent(v.getContext(), HomePharmacyActivity.class);
                                                startActivity(Pinsetting);
                                            }
                                        } else {
                                            Toast.makeText(v.getContext(), "Fail to register!! ", Toast.LENGTH_LONG);
                                        }
                                        progress.dismiss();
                                    }
                                });
                            } //if(loginUser.getStatLogin() == 1) {

                        } else { //if(passwordInput.getText().toString().equals(pass)){
                            initiatePopupWindow("Not same");
                            //Toast.makeText(getApplicationContext(), "Not same", Toast.LENGTH_SHORT).show();
                            passwordInput.setText("");
                        } //if(passwordInput.getText().toString().equals(pass)){
                    }
                } else { //if (passwordInput.length() == 4) {
                    initiatePopupWindow("Number Mininum length is 4");
                    //Toast.makeText(getApplicationContext(), "Number Mininum length is 4" + LoginUser.getInstance().getPinn(), Toast.LENGTH_SHORT).show();
                } //if (passwordInput.length() == 4) {

            } ///public void onClick(final View v) {
        });

    }

    private PopupWindow pw;
    private void initiatePopupWindow(CharSequence a) {
        try {
            //We need to get the instance of the LayoutInflater, use the context of this activity
            LayoutInflater inflater = (LayoutInflater) PinEntryView.this
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


}

