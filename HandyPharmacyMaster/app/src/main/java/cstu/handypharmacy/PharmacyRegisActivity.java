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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cstu.handypharmacy.adapter.AmphurAdapter;
import cstu.handypharmacy.adapter.DistrictAdapter;
import cstu.handypharmacy.adapter.ProvinceAdapter;
import cstu.handypharmacy.controller.Address;
import cstu.handypharmacy.controller.RegisMember;
import cstu.handypharmacy.model.Amphur;
import cstu.handypharmacy.model.District;
import cstu.handypharmacy.model.LoginUser;
import cstu.handypharmacy.model.Pharmacist;
import cstu.handypharmacy.model.Province;

public class PharmacyRegisActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "PharmacyRegisActivity";

    private final static int SELECT_PHOTO = 12345;

    private Button btnConfirm;
    private TextView Name;
    private TextView LastName;
    private RadioGroup Gender;
    private TextView CitizenID;
    private TextView PharmacyID;
    private TextView Addr;
    private Spinner spinnerProvince;
    private Spinner spinnerAmphur;
    private Spinner spinnerDistict;
    private TextView post_number;
    private TextView Tel;
    private TextView Email;
    private TextView Password;
    private TextView Passwordcon;
    private ImageView attachImages;
    private File images;

    private final static int REQUEST_READ_STORAGE_RESULT = 1;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_regis);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_READ_STORAGE_RESULT);
            }
        }

        Name = (TextView) findViewById(R.id.txtName1);
        LastName = (TextView) findViewById(R.id.txtLastName);
        Gender = (RadioGroup) findViewById(R.id.radioGrp);
        CitizenID = (TextView) findViewById(R.id.txtCitizenID);
        PharmacyID = (TextView) findViewById(R.id.txtPharmNum);
        Addr = (TextView) findViewById(R.id.txtAddr);
        spinnerProvince = (Spinner) findViewById(R.id.provincespinner);
        spinnerAmphur = (Spinner) findViewById(R.id.amphurSpinner);
        spinnerDistict = (Spinner) findViewById(R.id.districtSpinner);
        post_number = (TextView) findViewById(R.id.postcode);
        Tel = (TextView) findViewById(R.id.txtTel);
        Email = (TextView) findViewById(R.id.txtEmail);
        Password = (TextView) findViewById(R.id.txtPassword);
        Passwordcon = (TextView) findViewById(R.id.txtPasswordcon);
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

        ProvinceAdapter provinceAdapter = new ProvinceAdapter(
                this,
                R.layout.list_province,
                Province.provinces
        );

        spinnerProvince.setAdapter(provinceAdapter);
        spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, final View selectedItemView, final int position, long id) {
                final ProgressDialog progress = new ProgressDialog(selectedItemView.getContext());
                progress.setMessage("Loading amphur data...");
                progress.setIndeterminate(true);
                progress.setCancelable(false);
                progress.show();
                Amphur.amphurs.removeAll(Amphur.amphurs);
                Address.getAmphur(Province.provinces.get(position).getProId(), new Address.NoParaDataCallback() {
                    @Override
                    public void onFinish() {

                        Province.getInstance().setProId(Province.provinces.get(position).getProId());
                        Province.getInstance().setProName(Province.provinces.get(position).getProName());

                        AmphurAdapter amphurAdapter = new AmphurAdapter(
                                selectedItemView.getContext(),
                                R.layout.list_province,
                                Amphur.amphurs
                        );
                        spinnerAmphur.setAdapter(amphurAdapter);
                        progress.dismiss();
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        spinnerAmphur.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, final View selectedItemView, final int position, long id) {
                final ProgressDialog progress = new ProgressDialog(selectedItemView.getContext());
                progress.setMessage("Loading district data...");
                progress.setIndeterminate(true);
                progress.setCancelable(false);
                progress.show();
                District.districts.removeAll(District.districts);
                String ampId = "0";
                if (Amphur.amphurs.size() != 0) {
                    ampId = Amphur.amphurs.get(position).getAmphurId();

                    Address.getDistrict(ampId, new Address.NoParaDataCallback() {
                        @Override
                        public void onFinish() {

                            Amphur.getInstance().setAmphurId(Amphur.amphurs.get(position).getAmphurId());
                            Amphur.getInstance().setAmphurName(Amphur.amphurs.get(position).getAmphurName());

                            DistrictAdapter districtAdapter = new DistrictAdapter(
                                    selectedItemView.getContext(),
                                    R.layout.list_province,
                                    District.districts
                            );
                            spinnerDistict.setAdapter(districtAdapter);
                            progress.dismiss();
                        }
                    });

                } else {

                    progress.dismiss();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });


        spinnerDistict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (District.districts.size() != 0) {
                    District.getInstance().setDisId(District.districts.get(position).getDisId());
                    District.getInstance().setDisName(District.districts.get(position).getDisName());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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
            case R.id.btnConfirm:

                String selGen = "";
                if (Gender.getCheckedRadioButtonId() != -1) {
                    int radioButtonID = Gender.getCheckedRadioButtonId();
                    View radioButton = Gender.findViewById(radioButtonID);
                    int idx = Gender.indexOfChild(radioButton);
                    RadioButton btn = (RadioButton) Gender.getChildAt(idx);
                    selGen = btn.getText().toString();
                }

                Pharmacist pharmacist = new Pharmacist();

                // Check empty text field

               if (Name == null || Name.length()==0 || Name.equals("")) {
                    Log.i(TAG, "Name cannot be Empty");
                    //Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
                    showNotEmptyError((EditText) Name);
                }
                else if (LastName == null || LastName.length()==0 || LastName.equals("")) {
                    Log.i(TAG, "LastName cannot be Empty");
                    //Toast.makeText(this, "Please enter your LastName", Toast.LENGTH_SHORT).show();
                   showNotEmptyError((EditText) LastName);
                }
                else if (CitizenID == null || CitizenID.length()==0 || CitizenID.equals("")) {
                    Log.i(TAG, "Citizen ID cannot be Empty");
                    //Toast.makeText(this, "Please enter your CitizenID", Toast.LENGTH_SHORT).show();
                   showNotEmptyError((EditText) CitizenID);
                }
                else if (PharmacyID == null || PharmacyID.length()==0 || PharmacyID.equals("")) {
                    Log.i(TAG, "PharmacistID cannot be Empty");
                    //Toast.makeText(this, "Please enter your Pharmacy ID", Toast.LENGTH_SHORT).show();
                   showNotEmptyError((EditText) PharmacyID);
                }
                else if (Addr == null || Addr.length()==0 || Addr.equals("")) {
                    Log.i(TAG, "Addr cannot be Empty");
                    //Toast.makeText(this, "Please enter your Address", Toast.LENGTH_SHORT).show();
                   showNotEmptyError((EditText) Addr);
                }
                else if (post_number == null || post_number.length()==0 || post_number.equals("")) {
                    Log.i(TAG, "post_number cannot be Empty");
                    //Toast.makeText(this, "Please enter your post number", Toast.LENGTH_SHORT).show();
                   showNotEmptyError((EditText) post_number);
                }
                else if (Email == null || Email.length()==0 || Email.equals("")) {
                    Log.i(TAG, "Email cannot be Empty");
                    //Toast.makeText(this, "Please enter your Email", Toast.LENGTH_SHORT).show();
                   showNotEmptyError((EditText) Email);
                }
                else if (isValidEmail(Email.getText().toString())==false) {
                    Log.i(TAG, "Re Enter Email");
                    Toast.makeText(this, "You did not enter a correct Email", Toast.LENGTH_SHORT).show();
                    showError((EditText) Email);
                }
                else if (Password == null || Password.length()==0 || Password.equals("")) {
                    Log.i(TAG, "Password cannot be Empty");
                    //Toast.makeText(this, "Please enter your Password", Toast.LENGTH_SHORT).show();
                   showNotEmptyError((EditText) Password);
                }
                else if (Passwordcon == null || Passwordcon.length()==0 || Passwordcon.equals("")) {
                    Log.i(TAG, "Passwordcon cannot be Empty");
                    //Toast.makeText(this, "Please enter your Address", Toast.LENGTH_SHORT).show();
                   showNotEmptyError((EditText) Passwordcon);
                }
               else if(Ismatch(Password.getText().toString(),Passwordcon.getText().toString())==false){ //If the password is not match
                   Log.i(TAG, "Password not match");
                   Toast.makeText(this, "Your confirm password is not match", Toast.LENGTH_SHORT).show();
                   showNotEqualError((EditText) Passwordcon);
               }

                else {
                    pharmacist.setDevileID(Settings.Secure.getString(v.getContext().getContentResolver(), Settings.Secure.ANDROID_ID));
                    pharmacist.setName(Name.getText().toString());
                    pharmacist.setLastName(LastName.getText().toString());
                    pharmacist.setGender(selGen);
                    pharmacist.setCitizenID(CitizenID.getText().toString());
                    pharmacist.setPharmacyID(PharmacyID.getText().toString());
                    pharmacist.setAddr(Addr.getText().toString());
                    pharmacist.setTel(Tel.getText().toString());
                    pharmacist.setEmail(Email.getText().toString());
                    pharmacist.setPassword(Password.getText().toString());
                    pharmacist.setPostcode(post_number.getText().toString());
                    pharmacist.setDistrict(Integer.parseInt(District.getInstance().getDisId()));
                    pharmacist.setAmphur(Integer.parseInt(Amphur.getInstance().getAmphurId()));
                    pharmacist.setProvince(Integer.parseInt(Province.getInstance().getProId()));
                   if(images!=null) {
                       pharmacist.setImages(images);
                   } else {

                   }

                    final ProgressDialog progress = new ProgressDialog(v.getContext());
                    progress.setMessage("Saving...");
                    progress.setIndeterminate(true);
                    progress.setCancelable(false);
                    progress.show();

                    RegisMember.regisPharmacist(pharmacist, new RegisMember.DataCallback() {
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

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "PharmacyRegis Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://cstu.handypharmacy/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "PharmacyRegis Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://cstu.handypharmacy/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
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