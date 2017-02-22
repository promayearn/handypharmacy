package cstu.handypharmacy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cstu.handypharmacy.controller.RegisMember;
import cstu.handypharmacy.model.LoginUser;
import cstu.handypharmacy.model.Member;

public class UserRegisActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "UserRegisActivity";

    private final static int SELECT_PHOTO = 12345;

    private EditText etName, eLastName, etWeight, etHeight, etDrugAllergy, etDisease;

    private EditText etEmail, etPwd, etPwdCon;

    private DateDisplayPicker etBirthday;

    private ImageView attachImages;
    private File images;

    RadioGroup etGender;
    Spinner etBlood;
    Button bRegister;

    private final static int REQUEST_READ_STORAGE_RESULT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_regis);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_READ_STORAGE_RESULT);
            }
        }

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPwd = (EditText) findViewById(R.id.etPwd);
        etPwdCon = (EditText) findViewById(R.id.etPwdCon);

        //ชื่อ
        etName = (EditText) findViewById(R.id.etName);
        //นามสกุล
        eLastName = (EditText) findViewById(R.id.etLastName);
        //เพศ
        etGender = (RadioGroup) findViewById(R.id.radioGrp);
        //วันเกิด
        etBirthday = (DateDisplayPicker) findViewById(R.id.clientEditCreate_BirthDateDayPicker);
        //หมู่เลือด
        etBlood = (Spinner) findViewById(R.id.spinner1);
        //น้ำหนัก
        etWeight = (EditText) findViewById(R.id.etWeight);
        //ความสูง
        etHeight = (EditText) findViewById(R.id.etHeight);
        //ยาที่แพ้
        etDrugAllergy = (EditText) findViewById(R.id.etDrugAllergy);
        //โรคประจำตัว
        etDisease = (EditText) findViewById(R.id.etDisease);
        attachImages = (ImageView) findViewById(R.id.attachImages);

        attachImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

                try {
                    images = File.createTempFile("img", ".jpg", dir);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });

        bRegister = (Button) findViewById(R.id.bRegister);
        bRegister.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Here we need to check if the activity that was triggers was the Image Gallery.
        // If it is the requestCode will match the LOAD_IMAGE_RESULTS value.
        // If the resultCode is RESULT_OK and there is some data we know that an image was picked.
        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null) {
            // Let's read picked image data - its URI
            Uri pickedImage = data.getData();
            // Let's read picked image path using content resolver
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(images);
                fos.write(bytes.toByteArray());
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            attachImages.setImageBitmap(bitmap);

            cursor.close();

        }

    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.bRegister:

                int age =0;
                // ตัดวันที่ออกเป็น สามส่วน m/d/y
                if(etBirthday.getText().toString()==""){
                    etBirthday.setText("6/16/2016");
                    Log.d(TAG,""+etBirthday.getText().toString());
                    String[] dob = etBirthday.getText().toString().split("/", -1);
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    age = year - Integer.parseInt(dob[2]);
                }
                else{
                    String[] dob = etBirthday.getText().toString().split("/", -1);
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    age = year - Integer.parseInt(dob[2]);
                }

                String selBlood = etBlood.getSelectedItem().toString();
                String selection = "";
                if (etGender.getCheckedRadioButtonId() != -1) {
                    int radioButtonID = etGender.getCheckedRadioButtonId();
                    View radioButton = etGender.findViewById(radioButtonID);
                    int idx = etGender.indexOfChild(radioButton);
                    RadioButton btn = (RadioButton) etGender.getChildAt(idx);
                    selection = (String) btn.getText().toString();
                }

                Member members = new Member();


                //Check register empty
                if (etEmail == null || etEmail.length()==0 || etEmail.equals("")) {
                    Log.i(TAG, "Email cannot be Empty");
                    showNotEmptyError((EditText) etEmail);
                    Toast.makeText(this, "Please enter your Email", Toast.LENGTH_SHORT).show();
                }
                else if (isValidEmail(etEmail.getText().toString())==false) {
                    Log.i(TAG, "Re Enter Email");
                    Toast.makeText(this, "You did not enter a correct Email", Toast.LENGTH_SHORT).show();
                    showError((EditText) etEmail);
                }
                else if (etPwd == null || etPwd.length()==0 || etPwd.equals("")) {
                    Log.i(TAG, "Password cannot be Empty");
                    showNotEmptyError((EditText) etPwd);
                    Toast.makeText(this, "Please enter your Password", Toast.LENGTH_SHORT).show();
                }
                else if (etPwdCon == null || etPwdCon.length()==0 || etPwdCon.equals("")) {
                    Log.i(TAG, "Passwordcon cannot be Empty");
                    showNotEmptyError((EditText) etPwdCon);
                    Toast.makeText(this, "Please confirm your Password", Toast.LENGTH_SHORT).show();
                }
                else if(Ismatch(etPwd.getText().toString(),etPwdCon.getText().toString())==false){ //If the password is not match
                    Log.i(TAG, "Password not match");
                    Toast.makeText(this, "Your confirm password is not match", Toast.LENGTH_SHORT).show();
                    showNotEqualError((EditText) etPwdCon);
                }
                else if (etName == null || etName.length()==0 || etName.equals("")) {
                    Log.i(TAG, "Name cannot be Empty");
                    showNotEmptyError((EditText) etName);
                    Toast.makeText(this, "Please enter your Name", Toast.LENGTH_SHORT).show();
                }
                else if (eLastName == null || eLastName.length()==0 || eLastName.equals("")) {
                    Log.i(TAG, "LastName cannot be Empty");
                    showNotEmptyError((EditText) eLastName);
                    Toast.makeText(this, "Please enter your Lastname", Toast.LENGTH_SHORT).show();
                }
                //เช็คอายุ ไม่เกิน 15 ปี
                else if(age <= 15) {
                    Log.i(TAG, "Age must more than 15 years");
                    Toast.makeText(v.getContext(), "Your age must not less then 15 years.. ", Toast.LENGTH_SHORT).show();
                }
                else if (etWeight == null || etWeight.length()==0 || etWeight.equals("")) {
                    Log.i(TAG, "Weight cannot be Empty");
                    showNotEmptyError((EditText) etWeight);
                    Toast.makeText(this, "Please enter your Weight", Toast.LENGTH_SHORT).show();
                }
                else if (etHeight == null || etHeight.length()==0 || etHeight.equals("")) {
                    Log.i(TAG, "Height cannot be Empty");
                    showNotEmptyError((EditText) etHeight);
                    Toast.makeText(this, "Please enter your Height", Toast.LENGTH_SHORT).show();
                } else {

                    members.setEtEmail(etEmail.getText().toString());
                    members.setEtPwd(etPwd.getText().toString());
                    members.setEtName(etName.getText().toString());
                    members.seteLastName(eLastName.getText().toString());
                    members.setMobileId(Settings.Secure.getString(v.getContext().getContentResolver(), Settings.Secure.ANDROID_ID));
                    members.seteGender(selection);
                    members.seteBlood(selBlood);
                    members.setEtBirthday(etBirthday.getText().toString());
                    members.setEtHeight(etHeight.getText().toString());
                    members.setEtWeight(etWeight.getText().toString());
                    members.setEtDrugAllergy(etDrugAllergy.getText().toString());
                    members.setEtDisease(etDisease.getText().toString());
                    if (images != null) {
                        members.setImages(images);
                    } else {
                    }

                    Log.i(TAG, etBirthday.getText().toString());
                    final ProgressDialog progress = new ProgressDialog(v.getContext());
                    progress.setMessage("Saving...");
                    progress.setIndeterminate(true);
                    progress.setCancelable(false);
                    progress.show();

                    RegisMember.register(members, new RegisMember.DataCallback() {
                        @Override
                        public void onFinish(LoginUser loginUser) {

                            if (loginUser.getStatLog() == 2) {
                                Intent intent = new Intent(v.getContext(), PinEntryView.class);
                                startActivity(intent);
                            } else if (loginUser.getStatLog() == -1) {
                                Toast.makeText(v.getContext(), "Fail to register!! ", Toast.LENGTH_LONG);
                            }

                            progress.dismiss();
                            Log.i(TAG, "insert complete " + loginUser.getMobileId());
                        }
                    });
                }
                    break;
        }
    }

    private boolean isValidEmail(String email) {
        boolean isValid = false;
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    private void showError(EditText mEditText) {
        mEditText.setError(mEditText.getText().toString() + " is not correct");
    }


    private void showNotEmptyError(EditText mEditText) {
        mEditText.setError(mEditText.getText().toString() + " Can not be empty.");
        return;
    }

    private void showNotEqualError(EditText mEditText) {
        mEditText.setError(mEditText.getText().toString() + " Password not match");
        return;
    }
    private boolean Ismatch(String str1,String str2){
        if(str1.equals(str2)){
            return true;
        }
        else{
            return false;
        }
    }

}
