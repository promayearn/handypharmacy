package cstu.handypharmacy.webService;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cstu.handypharmacy.model.Pharmacy;
import cstu.handypharmacy.model.ResponseStatus;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PharmacyService {
    private static final String TAG = "PharmacyService";

    private static final String linkLatLng = ServerName.BASE_URL + "addLatLng.php";
    private static final String linkGetDrug = ServerName.BASE_URL + "getPharmacyData.php";

    private static final OkHttpClient mClient = new OkHttpClient();
    private static ResponseStatus mResponseStatus;

    public interface WebServiceCallback {
        void onFailure(IOException e);

        void onResponse(ResponseStatus responseStatus);
    }

    public interface NoParaWebServiceCallback {
        void onFailure(IOException e);

        void onResponse(ResponseStatus responseStatus);
    }

    public static void save(double lat, double lng, final WebServiceCallback callback) {
        FormBody.Builder builder = new FormBody.Builder();

        // lat
        builder.add("lat", String.valueOf(lat));
        // lng
        builder.add("lng", String.valueOf(lng));

        RequestBody formBody = builder.build();

        Request request = new Request.Builder()
                .url(linkLatLng)
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
                    int success = jsonObject.getInt("success");

                    // มีการ get ค่าจาก json ได้
                    if (success == 1) {
                        mResponseStatus = new ResponseStatus(true, jsonObject.getString("message"));
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


    // ส่ง http request เพื่อเอาข้อมูล  storedata
    public static void getStoreData(final NoParaWebServiceCallback callback) {
        Request request = new Request.Builder()
                .url(linkGetDrug)
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
                        parseJsonData(jsonObject.getJSONArray("store_data"));
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

    private static void parseJsonData(JSONArray district_data) throws JSONException {

        for (int i = 0; i < district_data.length(); i++) {
            JSONObject json = district_data.getJSONObject(i);

            Pharmacy pharmacy = new Pharmacy();

            pharmacy.setStore_name(json.getString("store_name"));
            pharmacy.setStore_tel(json.getString("store_tel"));
            pharmacy.setStore_addr(json.getString("store_address"));
            pharmacy.setDay_open(json.getString("day_open"));
            pharmacy.setOpenTime(json.getString("time_open"));
            pharmacy.setCloseTime(json.getString("time_close"));
            pharmacy.setProvince(json.getInt("province_id"));
            pharmacy.setAmphur(json.getInt("amphur_id"));
            pharmacy.setDistrict(json.getInt("district_id"));
            pharmacy.setPostcode(json.getString("post_number"));
            pharmacy.setLat(json.getDouble("lat"));
            pharmacy.setLng(json.getDouble("lng"));
            pharmacy.setStatus(json.getInt("status"));
            pharmacy.setQuality(json.getInt("quality"));

            Pharmacy.pharmacies.add(pharmacy);
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

