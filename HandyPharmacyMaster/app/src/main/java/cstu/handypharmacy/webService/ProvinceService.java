package cstu.handypharmacy.webService;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cstu.handypharmacy.model.Amphur;
import cstu.handypharmacy.model.District;
import cstu.handypharmacy.model.Province;
import cstu.handypharmacy.model.ResponseStatus;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProvinceService {

    private static final String TAG = "ProvinceService";

    private static final String linkProvince = ServerName.BASE_URL + "getProvince.php";
    private static final String linkAmphur = ServerName.BASE_URL + "getAmphur.php";
    private static final String linkDistict = ServerName.BASE_URL + "getDistrict.php";

    private static final OkHttpClient mClient = new OkHttpClient();
    private static ResponseStatus mResponseStatus;

    public interface NoParaWebServiceCallback {
        void onFailure(IOException e);
        void onResponse(ResponseStatus responseStatus);
    }

    // ส่ง http request เพื่อเอาข้อมูลตำบล
    public static void getDistrict(final String amphurId, final NoParaWebServiceCallback callback) {

        // เกท อำเภอต้องส่งค่า จังหวัดไปด้วย
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("amphurId", amphurId)
                .build();

        Request request = new Request.Builder()
                .url(linkDistict)
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
                delay(3);

                final String jsonResult = response.body().string();
                Log.d(TAG, jsonResult);

                try {
                    JSONObject jsonObject = new JSONObject(jsonResult);
                    int success = jsonObject.getInt("success");

                    if (success == 1) {
                        mResponseStatus = new ResponseStatus(true, null);
                        parseJsonDistrictData(jsonObject.getJSONArray("district_data"));
                    } else if (success == 0) {
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

    // ส่ง http request เพื่อเอาข้อมูลอำเภอ
    public static void getAmphur(final String proId, final NoParaWebServiceCallback callback) {

        // เกท อำเภอต้องส่งค่า จังหวัดไปด้วย
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("proId", proId)
                .build();

        Request request = new Request.Builder()
                .url(linkAmphur)
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
                delay(3);

                final String jsonResult = response.body().string();
                Log.d(TAG, jsonResult);

                try {
                    JSONObject jsonObject = new JSONObject(jsonResult);
                    int success = jsonObject.getInt("success");

                    if (success == 1) {
                        mResponseStatus = new ResponseStatus(true, null);
                        parseJsonAmphurData(jsonObject.getJSONArray("amphur_data"));
                    } else if (success == 0) {
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

    // ส่ง http request เพื่อเอาข้อมูลจังหวัด
    public static void getProvince(final NoParaWebServiceCallback callback) {
        Request request = new Request.Builder()
                .url(linkProvince)
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
                delay(3);

                final String jsonResult = response.body().string();
                Log.d(TAG, jsonResult);

                try {
                    JSONObject jsonObject = new JSONObject(jsonResult);
                    int success = jsonObject.getInt("success");

                    if (success == 1) {
                        mResponseStatus = new ResponseStatus(true, null);
                        parseJsonProvinceData(jsonObject.getJSONArray("province_data"));
                    } else if (success == 0) {
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

    /// ดึงข้อมูล ตำบลใส่ arraylist
    private static void parseJsonDistrictData(JSONArray district_data) throws JSONException {

        for (int i = 0; i < district_data.length(); i++) {
            JSONObject json = district_data.getJSONObject(i);

            District district = new District();

            district.setDisId(json.getString("districtId"));
            district.setDisName(json.getString("districtName"));

            District.districts.add(district);
        }

    }

    /// ดึงข้อมูล อำเภอใส่ arraylist
    private static void parseJsonAmphurData(JSONArray amphur_data) throws JSONException {
        for (int i = 0; i < amphur_data.length(); i++) {
            JSONObject json = amphur_data.getJSONObject(i);

            Amphur amphur = new Amphur();

            amphur.setAmphurId(json.getString("amphurId"));
            amphur.setAmphurName(json.getString("amphurName"));

            Amphur.amphurs.add(amphur);
        }
    }

    /// ดึงข้อมูล จังหวัดใส่ arraylist
    private static void parseJsonProvinceData(JSONArray jsonArrayData) throws JSONException {
        for (int i = 0; i < jsonArrayData.length(); i++) {
            JSONObject json = jsonArrayData.getJSONObject(i);

            Province province = new Province();

            province.setProId(json.getString("proId"));
            province.setProName(json.getString("proName"));

            Province.provinces.add(province);
        }
    }

    private static void delay(int second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
