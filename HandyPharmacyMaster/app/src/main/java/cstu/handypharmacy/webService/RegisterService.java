package cstu.handypharmacy.webService;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cstu.handypharmacy.model.LoginUser;
import cstu.handypharmacy.model.Member;
import cstu.handypharmacy.model.Pharmacist;
import cstu.handypharmacy.model.Pharmacy;
import cstu.handypharmacy.model.ResponseStatus;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterService {

    private static final String TAG = "RegisterService";

    private static final MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpeg");

    private static final String linkREgis = ServerName.BASE_URL + "regisMember.php";
    private static final String linkRegisPin = ServerName.BASE_URL + "regisPin.php";
    private static final String linkChkRegis = ServerName.BASE_URL + "getRegisterStatus.php";
    private static final String linkRegisPharma = ServerName.BASE_URL + "regisPharmacist.php";
    private static final String linkRegisDrug = ServerName.BASE_URL + "regisPharmacy.php";

    private static final OkHttpClient mClient = new OkHttpClient();
    private static ResponseStatus mResponseStatus;
    private static LoginUser login = LoginUser.getInstance();


    public interface WebServiceCallback {
        void onFailure(IOException e);

        void onResponse(ResponseStatus responseStatus, LoginUser loginUser);
    }

    public interface NoParaWebServiceCallback {
        void onFailure(IOException e);

        void onResponse(ResponseStatus responseStatus);
    }

    //ลงทะเบียน สมาชิกทั่วไป
    public static void regis(Member members, final WebServiceCallback callback) {
        login.setUsrEmail(members.getEtEmail());
        login.setUsrPwd(members.getEtPwd());
        login.setMobileId(members.getMobileId());
        login.setName(members.getEtName());
        login.setLastName(members.geteLastName());
        login.setUserType(0);
        if(members.getImages()==null) {
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("email", members.getEtEmail())
                    .addFormDataPart("password", members.getEtPwd())
                    .addFormDataPart("device_id", members.getMobileId())
                    .addFormDataPart("f_name", members.getEtName())
                    .addFormDataPart("l_name", members.geteLastName())
                    .addFormDataPart("gender", members.geteGender())
                    .addFormDataPart("date_of_birth", members.getEtBirthday())
                    .addFormDataPart("blood_type", members.geteBlood())
                    .addFormDataPart("weight", members.getEtWeight())
                    .addFormDataPart("height", members.getEtHeight())
                    .addFormDataPart("drug_allergy", members.getEtDrugAllergy())
                    .addFormDataPart("congenital_disease", members.getEtDisease())
                    .build();


            Request request = new Request.Builder()
                    .url(linkREgis)
                    .post(requestBody)
                    .build();

            mClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    new Handler(Looper.getMainLooper()).post(
                            new Runnable() {
                                @Override
                                public void run() {
                                    callback.onFailure(e);
                                }
                            }
                    );
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String jsonResult = response.body().string();
                    Log.d(TAG, jsonResult);

                    try {
                        JSONObject jsonObject = new JSONObject(jsonResult);
                        ///
                        int success = jsonObject.getInt("success");

                        // มีการ get ค่าจาก json ได้
                        if (success == 1) {
                            login.setStatLog(2);
                            mResponseStatus = new ResponseStatus(true, jsonObject.getString("message"));
                        } else if (success == 0) {
                            login.setStatLog(-1);
                            mResponseStatus = new ResponseStatus(false, jsonObject.getString("message"));
                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "Error parsing JSON.");
                        e.printStackTrace();
                    }

                    new Handler(Looper.getMainLooper()).post(
                            new Runnable() {
                                @Override
                                public void run() {
                                    callback.onResponse(mResponseStatus, login);
                                }
                            }
                    );
                }
            });
        } else {
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("email", members.getEtEmail())
                    .addFormDataPart("password", members.getEtPwd())
                    .addFormDataPart("device_id", members.getMobileId())
                    .addFormDataPart("f_name", members.getEtName())
                    .addFormDataPart("l_name", members.geteLastName())
                    .addFormDataPart("gender", members.geteGender())
                    .addFormDataPart("date_of_birth", members.getEtBirthday())
                    .addFormDataPart("blood_type", members.geteBlood())
                    .addFormDataPart("weight", members.getEtWeight())
                    .addFormDataPart("height", members.getEtHeight())
                    .addFormDataPart("drug_allergy", members.getEtDrugAllergy())
                    .addFormDataPart("congenital_disease", members.getEtDisease())
                    .addFormDataPart("img_name", members.getImages().getName(), RequestBody.create(MEDIA_TYPE_JPG, members.getImages()))
                    .build();


            Request request = new Request.Builder()
                    .url(linkREgis)
                    .post(requestBody)
                    .build();

            mClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    new Handler(Looper.getMainLooper()).post(
                            new Runnable() {
                                @Override
                                public void run() {
                                    callback.onFailure(e);
                                }
                            }
                    );
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String jsonResult = response.body().string();
                    Log.d(TAG, jsonResult);

                    try {
                        JSONObject jsonObject = new JSONObject(jsonResult);
                        ///
                        int success = jsonObject.getInt("success");

                        // มีการ get ค่าจาก json ได้
                        if (success == 1) {
                            login.setStatLog(2);
                            mResponseStatus = new ResponseStatus(true, jsonObject.getString("message"));
                        } else if (success == 0) {
                            login.setStatLog(-1);
                            mResponseStatus = new ResponseStatus(false, jsonObject.getString("message"));
                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "Error parsing JSON.");
                        e.printStackTrace();
                    }

                    new Handler(Looper.getMainLooper()).post(
                            new Runnable() {
                                @Override
                                public void run() {
                                    callback.onResponse(mResponseStatus, login);
                                }
                            }
                    );
                }
            });
        }
    }


    // ลงทะเบียนพิน
    public static void regisPinn(final String pinn, final WebServiceCallback callback) {
        FormBody.Builder builder = new FormBody.Builder();
        // เลขเครื่อง
        builder.add("pin", pinn);
        builder.add("device_id", LoginUser.getInstance().getMobileId());
        builder.add("user_type", "" + LoginUser.getInstance().getUserType());

        RequestBody formBody = builder.build();

        Request request = new Request.Builder()
                .url(linkRegisPin)
                .post(formBody)
                .build();

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                new Handler(Looper.getMainLooper()).post(
                        new Runnable() {
                            @Override
                            public void run() {
                                callback.onFailure(e);
                            }
                        }
                );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String jsonResult = response.body().string();
                Log.d(TAG, jsonResult);

                try {
                    JSONObject jsonObject = new JSONObject(jsonResult);
                    int success = jsonObject.getInt("success");

                    // มีการ get ค่าจาก json ได้
                    if (success == 1) {
                        login.setStatLog(1);
                        login.setPinn(pinn);
                        mResponseStatus = new ResponseStatus(true, jsonObject.getString("message"));
                    } else if (success == 0) {
                        login.setStatLog(2);
                        mResponseStatus = new ResponseStatus(true, jsonObject.getString("message"));
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Error parsing JSON.");
                    e.printStackTrace();
                }

                new Handler(Looper.getMainLooper()).post(
                        new Runnable() {
                            @Override
                            public void run() {
                                callback.onResponse(mResponseStatus, login);
                            }
                        }
                );
            }
        });
    }


    //เช็คว่าเครื่องมีการลงทะเบียนหรือไม่
    public static void chkLogin(LoginUser loginUser, final WebServiceCallback callback) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("email", loginUser.getUsrEmail());

        RequestBody formBody = builder.build();

        Request request = new Request.Builder()
                .url(linkChkRegis)
                .post(formBody)
                .build();

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                new Handler(Looper.getMainLooper()).post(
                        new Runnable() {
                            @Override
                            public void run() {
                                callback.onFailure(e);
                            }
                        }
                );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String jsonResult = response.body().string();
                Log.d(TAG, jsonResult);

                try {
                    JSONObject jsonObject = new JSONObject(jsonResult);
                    ///
                    int success = jsonObject.getInt("registed");

                    //linkChkRegis

                    // มีการ get ค่าจาก json ได้
                    login.setStatLog(success);
                    if (success == 1) {
                        login.setStatLog(1);
                        login.setUsrID(jsonObject.getInt("user_id"));
                        login.setUsrEmail(jsonObject.getString("email"));
                        login.setUsrPwd(jsonObject.getString("password"));
                        login.setMobileId(jsonObject.getString("device_id"));
                        login.setPinn(jsonObject.getString("pin"));
                        login.setName(jsonObject.getString("f_name"));
                        login.setLastName(jsonObject.getString("l_name"));
                        login.setUserType(jsonObject.getInt("user_type"));
                        login.setStatus(jsonObject.getInt("status"));
                        Log.e(TAG, "Hello World" + login.getStatus());
                        mResponseStatus = new ResponseStatus(true, "");
                    } else if (success == 0) {
                        mResponseStatus = new ResponseStatus(false, "");
                        login.setStatLogin(-1);
                        Log.i(TAG, "Device not registed.");
                    }

                } catch (JSONException e) {
                    Log.e(TAG, "Error parsing JSON.");
                    e.printStackTrace();
                }

                new Handler(Looper.getMainLooper()).post(
                        new Runnable() {
                            @Override
                            public void run() {
                                callback.onResponse(mResponseStatus, login);
                            }
                        }
                );
            }
        });
    }

    // ลงทะเบียน pharmacist
    public static void regisPharmacist(Pharmacist pharmacist, final WebServiceCallback callback) {

        login.setMobileId(pharmacist.getDevileID());
        login.setName(pharmacist.getName());
        login.setLastName(pharmacist.getLastName());
            if(pharmacist.getImages()==null) {
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("device_id", pharmacist.getDevileID())
                        .addFormDataPart("f_name", pharmacist.getName())
                        .addFormDataPart("l_name", pharmacist.getLastName())
                        .addFormDataPart("gender", "" + pharmacist.getGender())
                        .addFormDataPart("citizen_id", pharmacist.getCitizenID())
                        .addFormDataPart("pharmacist_id", pharmacist.getPharmacyID())
                        .addFormDataPart("address", pharmacist.getAddr())
                        .addFormDataPart("province_id", "" + pharmacist.getProvince())
                        .addFormDataPart("amphur_id", "" + pharmacist.getAmphur())
                        .addFormDataPart("district_id", "" + pharmacist.getDistrict())
                        .addFormDataPart("post_number", "" + pharmacist.getPostcode())
                        .addFormDataPart("tel", pharmacist.getTel())
                        .addFormDataPart("email", pharmacist.getEmail())
                        .addFormDataPart("password", pharmacist.getPassword())
                        .addFormDataPart("status", "" + pharmacist.getStatus())
                        .build();

                Request request = new Request.Builder()
                        .url(linkRegisPharma)
                        .post(requestBody)
                        .build();

                mClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, final IOException e) {
                        new Handler(Looper.getMainLooper()).post(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        callback.onFailure(e);
                                    }
                                }
                        );
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String jsonResult = response.body().string();
                        Log.d(TAG, jsonResult);

                        try {
                            JSONObject jsonObject = new JSONObject(jsonResult);
                            ///
                            int success = jsonObject.getInt("success");

                            // มีการ get ค่าจาก json ได้
                            if (success == 1) {
                                login.setStatLog(2);
                                mResponseStatus = new ResponseStatus(true, jsonObject.getString("message"));
                            } else if (success == 0) {
                                login.setStatLog(-1);
                                mResponseStatus = new ResponseStatus(false, jsonObject.getString("message"));
                            }
                        } catch (JSONException e) {
                            Log.e(TAG, "Error parsing JSON.");
                            e.printStackTrace();
                        }

                        new Handler(Looper.getMainLooper()).post(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        callback.onResponse(mResponseStatus, login);
                                    }
                                }
                        );
                    }
                });
            } else {
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("device_id", pharmacist.getDevileID())
                        .addFormDataPart("f_name", pharmacist.getName())
                        .addFormDataPart("l_name", pharmacist.getLastName())
                        .addFormDataPart("gender", "" + pharmacist.getGender())
                        .addFormDataPart("citizen_id", pharmacist.getCitizenID())
                        .addFormDataPart("pharmacist_id", pharmacist.getPharmacyID())
                        .addFormDataPart("address", pharmacist.getAddr())
                        .addFormDataPart("province_id", "" + pharmacist.getProvince())
                        .addFormDataPart("amphur_id", "" + pharmacist.getAmphur())
                        .addFormDataPart("district_id", "" + pharmacist.getDistrict())
                        .addFormDataPart("post_number", "" + pharmacist.getPostcode())
                        .addFormDataPart("tel", pharmacist.getTel())
                        .addFormDataPart("email", pharmacist.getEmail())
                        .addFormDataPart("password", pharmacist.getPassword())
                        .addFormDataPart("status", "" + pharmacist.getStatus())
                        .addFormDataPart("img_name", pharmacist.getImages().getName(), RequestBody.create(MEDIA_TYPE_JPG, pharmacist.getImages()))
                        .build();

                Request request = new Request.Builder()
                        .url(linkRegisPharma)
                        .post(requestBody)
                        .build();

                mClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, final IOException e) {
                        new Handler(Looper.getMainLooper()).post(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        callback.onFailure(e);
                                    }
                                }
                        );
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String jsonResult = response.body().string();
                        Log.d(TAG, jsonResult);

                        try {
                            JSONObject jsonObject = new JSONObject(jsonResult);
                            ///
                            int success = jsonObject.getInt("success");

                            // มีการ get ค่าจาก json ได้
                            if (success == 1) {
                                login.setStatLog(2);
                                mResponseStatus = new ResponseStatus(true, jsonObject.getString("message"));
                            } else if (success == 0) {
                                login.setStatLog(-1);
                                mResponseStatus = new ResponseStatus(false, jsonObject.getString("message"));
                            }
                        } catch (JSONException e) {
                            Log.e(TAG, "Error parsing JSON.");
                            e.printStackTrace();
                        }

                        new Handler(Looper.getMainLooper()).post(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        callback.onResponse(mResponseStatus, login);
                                    }
                                }
                        );
                    }
                });
            }
    }

    // ลงทะเบียน ร้านยา
    public static void regisDrug(Pharmacy pharmacy, final NoParaWebServiceCallback callback) {

        if(pharmacy.getImages()==null) {
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("store_name", pharmacy.getStore_name())
                    .addFormDataPart("pharmacist_id", String.valueOf(login.getUsrID()))
                    .addFormDataPart("store_address", pharmacy.getStore_addr())
                    .addFormDataPart("store_tel", "" + pharmacy.getStore_tel())
                    .addFormDataPart("day_open", pharmacy.getDay_open())
                    .addFormDataPart("time_open", pharmacy.getOpenTime())
                    .addFormDataPart("time_close", pharmacy.getCloseTime())
                    .addFormDataPart("province_id", "" + pharmacy.getProvince())
                    .addFormDataPart("amphur_id", "" + pharmacy.getAmphur())
                    .addFormDataPart("district_id", "" + pharmacy.getDistrict())
                    .addFormDataPart("post_number", "" + pharmacy.getPostcode())
                    .addFormDataPart("lat", "" + pharmacy.getLat())
                    .addFormDataPart("lng", "" + pharmacy.getLng())
                    .build();

            Request request = new Request.Builder()
                    .url(linkRegisDrug)
                    .post(requestBody)
                    .build();

            mClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    new Handler(Looper.getMainLooper()).post(
                            new Runnable() {
                                @Override
                                public void run() {
                                    callback.onFailure(e);
                                }
                            }
                    );
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String jsonResult = response.body().string();
                    Log.d(TAG, jsonResult);

                    try {
                        JSONObject jsonObject = new JSONObject(jsonResult);
                        ///
                        int success = jsonObject.getInt("success");

                        // มีการ get ค่าจาก json ได้
                        if (success == 1) {
                            login.setStatLog(2);
                            mResponseStatus = new ResponseStatus(true, jsonObject.getString("message"));
                        } else if (success == 0) {
                            login.setStatLog(-1);
                            mResponseStatus = new ResponseStatus(false, jsonObject.getString("message"));
                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "Error parsing JSON.");
                        e.printStackTrace();
                    }

                    new Handler(Looper.getMainLooper()).post(
                            new Runnable() {
                                @Override
                                public void run() {
                                    callback.onResponse(mResponseStatus);
                                }
                            }
                    );
                }
            });
        } else {RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("store_name", pharmacy.getStore_name())
                .addFormDataPart("pharmacist_id", String.valueOf(login.getUsrID()))
                .addFormDataPart("store_address", pharmacy.getStore_addr())
                .addFormDataPart("store_tel", "" + pharmacy.getStore_tel())
                .addFormDataPart("day_open", pharmacy.getDay_open())
                .addFormDataPart("time_open", pharmacy.getOpenTime())
                .addFormDataPart("time_close", pharmacy.getCloseTime())
                .addFormDataPart("province_id", "" + pharmacy.getProvince())
                .addFormDataPart("amphur_id", "" + pharmacy.getAmphur())
                .addFormDataPart("district_id", "" + pharmacy.getDistrict())
                .addFormDataPart("post_number", "" + pharmacy.getPostcode())
                .addFormDataPart("lat", "" + pharmacy.getLat())
                .addFormDataPart("lng", "" + pharmacy.getLng())
                .addFormDataPart("img_name", pharmacy.getImages().getName(), RequestBody.create(MEDIA_TYPE_JPG, pharmacy.getImages()))
                .build();

        Request request = new Request.Builder()
                .url(linkRegisDrug)
                .post(requestBody)
                .build();

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                new Handler(Looper.getMainLooper()).post(
                        new Runnable() {
                            @Override
                            public void run() {
                                callback.onFailure(e);
                            }
                        }
                );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String jsonResult = response.body().string();
                Log.d(TAG, jsonResult);

                try {
                    JSONObject jsonObject = new JSONObject(jsonResult);
                    ///
                    int success = jsonObject.getInt("success");

                    // มีการ get ค่าจาก json ได้
                    if (success == 1) {
                        login.setStatLog(2);
                        mResponseStatus = new ResponseStatus(true, jsonObject.getString("message"));
                    } else if (success == 0) {
                        login.setStatLog(-1);
                        mResponseStatus = new ResponseStatus(false, jsonObject.getString("message"));
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Error parsing JSON.");
                    e.printStackTrace();
                }

                new Handler(Looper.getMainLooper()).post(
                        new Runnable() {
                            @Override
                            public void run() {
                                callback.onResponse(mResponseStatus);
                            }
                        }
                );
            }
        });
    }
    }

}
