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
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import cstu.handypharmacy.adapter.AmphurAdapter;
import cstu.handypharmacy.adapter.DistrictAdapter;
import cstu.handypharmacy.adapter.ProvinceAdapter;
import cstu.handypharmacy.controller.Address;
import cstu.handypharmacy.controller.RegisMember;
import cstu.handypharmacy.model.Amphur;
import cstu.handypharmacy.model.District;
import cstu.handypharmacy.model.Pharmacy;
import cstu.handypharmacy.model.Province;

public class RegisterDrugStoreActivity extends AppCompatActivity implements View.OnClickListener {

    /*
    * seqid, store_name, store_addr
    * , store_tel, day_open, openTime, closeTime
    * , province, lat, lng
    * */

    private static String TAG = "RegisterDrugStoreActivity";

    private final static int SELECT_PHOTO = 12345;

    private final static int REQUEST_READ_STORAGE_RESULT = 1;

    private TextView txtName1;
    private TextView txtAddr;
    private TextView txtTel;
    private TextView dayOpen;
    private TextView openTime;
    private TextView closeTime;
    private Spinner spinnerProvince;
    private Spinner spinnerAmphur;
    private Spinner spinnerDistict;
    private TextView postcode;
    private TextView txtLat;
    private TextView txtLng;
    private Button loadLocation;
    private Button btnConfirm;
    private Pharmacy pharmacy;
    private ImageView attachImages;
    private File images;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regis_drug);

        pharmacy = Pharmacy.getInstance();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_READ_STORAGE_RESULT);
            }
        }

        // สร้าง list สำหรับเก็บข้อมูล
        List<String> items = Arrays.asList(getResources().getStringArray(R.array.day_arrays));


        loadLocation = (Button) findViewById(R.id.loadLocation);
        loadLocation.setOnClickListener(this);
        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(this);
        txtName1 = (TextView) findViewById(R.id.txtName1);
        txtAddr = (TextView) findViewById(R.id.txtAddr);
        txtTel = (TextView) findViewById(R.id.txtTel);
        dayOpen = (TextView) findViewById(R.id.dayopen);
        openTime = (TextView) findViewById(R.id.openTime);
        closeTime = (TextView) findViewById(R.id.closeTime);
        postcode = (TextView) findViewById(R.id.postcode);
        spinnerProvince = (Spinner) findViewById(R.id.provincespinner);
        spinnerAmphur = (Spinner) findViewById(R.id.amphurSpinner);
        spinnerDistict = (Spinner) findViewById(R.id.districtSpinner);
        txtLat = (TextView) findViewById(R.id.txtLat);
        txtLng = (TextView) findViewById(R.id.txtLng);

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

        setData();


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
                Address.getAmphur(Province.provinces.get(position).getProId(),new Address.NoParaDataCallback() {
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
                if(Amphur.amphurs.size() != 0) {
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
                // your code here
            }
        });


        spinnerDistict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(District.districts.size() != 0) {
                    District.getInstance().setDisId(District.districts.get(position).getDisId());
                    District.getInstance().setDisName(District.districts.get(position).getDisName());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
            String[] filePath = { MediaStore.Images.Media.DATA };
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
        switch(v.getId()) {

            case R.id.loadLocation:
                getData();
                Intent intent = new Intent(v.getContext(), StoreRegisterMap.class);
                startActivity(intent);
                break;

            case R.id.btnConfirm:

                if (txtName1 == null || txtName1.length()==0 || txtName1.equals("")) {
                    Log.i(TAG, "Name cannot be Empty");
                    //Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
                    showNotEmptyError((EditText) txtName1);
                }
                else if (txtAddr == null || txtAddr.length()==0 || txtAddr.equals("")) {
                    Log.i(TAG, "Address cannot be Empty");
                    //Toast.makeText(this, "Please enter your LastName", Toast.LENGTH_SHORT).show();
                    showNotEmptyError((EditText) txtAddr);
                }
                else if (txtTel == null || txtTel.length()==0 || txtTel.equals("")) {
                    Log.i(TAG, "Tel cannot be Empty");
                    //Toast.makeText(this, "Please enter your LastName", Toast.LENGTH_SHORT).show();
                    showNotEmptyError((EditText) txtTel);
                }
                else if (dayOpen == null || dayOpen.length()==0 || dayOpen.equals("")) {
                    Log.i(TAG, "Day Open cannot be Empty");
                    //Toast.makeText(this, "Please enter your LastName", Toast.LENGTH_SHORT).show();
                    showNotEmptyError((EditText) dayOpen);
                }
                else if (openTime == null || openTime.length()==0 || openTime.equals("")) {
                    Log.i(TAG, "Time Open cannot be Empty");
                    //Toast.makeText(this, "Please enter your LastName", Toast.LENGTH_SHORT).show();
                    showNotEmptyError((EditText) openTime);
                }
                else if (closeTime == null || closeTime.length()==0 || closeTime.equals("")) {
                    Log.i(TAG, "Time close cannot be Empty");
                    //Toast.makeText(this, "Please enter your LastName", Toast.LENGTH_SHORT).show();
                    showNotEmptyError((EditText) closeTime);
                }
                else if (postcode == null || postcode.length()==0 || postcode.equals("")) {
                    Log.i(TAG, "postcode cannot be Empty");
                    //Toast.makeText(this, "Please enter your LastName", Toast.LENGTH_SHORT).show();
                    showNotEmptyError((EditText) postcode);
                }

                else {
                    pharmacy.setStore_name(txtName1.getText().toString());
                    pharmacy.setStore_addr(txtAddr.getText().toString());
                    pharmacy.setStore_tel(txtTel.getText().toString());
                    pharmacy.setDay_open(dayOpen.getText().toString());
                    pharmacy.setOpenTime(openTime.getText().toString());
                    pharmacy.setCloseTime(closeTime.getText().toString());
                    pharmacy.setProvince(Integer.parseInt(Province.getInstance().getProId()));
                    pharmacy.setAmphur(Integer.parseInt(Amphur.getInstance().getAmphurId()));
                    pharmacy.setDistrict(Integer.parseInt(District.getInstance().getDisId()));
                    pharmacy.setPostcode(postcode.getText().toString());
                    pharmacy.setLat(Double.parseDouble(txtLat.getText().toString()));
                    pharmacy.setLng(Double.parseDouble(txtLng.getText().toString()));
                    pharmacy.setImages(images);

                    final ProgressDialog progress = new ProgressDialog(v.getContext());
                    progress.setMessage("Saving...");
                    progress.setIndeterminate(true);
                    progress.setCancelable(false);
                    progress.show();

                    RegisMember.regisDrugstore(pharmacy, new RegisMember.NoParaDataCallback() {
                        @Override
                        public void onFinish() {
                            Intent intent = new Intent(v.getContext(), HomePharmacyActivity.class);
                            startActivity(intent);
                            progress.dismiss();
                        }
                    });
                }
                break;

        }
    }

    private void getData() {
        pharmacy.setLat(Double.parseDouble(txtLat.getText().toString()));
        pharmacy.setLng(Double.parseDouble(txtLng.getText().toString()));
    }

    private void setData() {
        txtLat.setText("" + pharmacy.getLat());
        txtLng.setText("" + pharmacy.getLng());
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
