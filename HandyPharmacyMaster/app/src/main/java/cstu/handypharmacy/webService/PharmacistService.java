package cstu.handypharmacy.webService;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cstu.handypharmacy.model.Pharmacist;
import cstu.handypharmacy.model.ResponseStatus;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Seeuintupro on 8/6/2559.
 */
public class PharmacistService {
    private static final String TAG = "PharmacistService";

    private static final String linkGetUser = ServerName.BASE_URL + "getPharmacistData.php";

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

    // ส่ง http request เพื่อเอาข้อมูล
    public static void getPharmacistData(final NoParaWebServiceCallback callback) {
        Request request = new Request.Builder()
                .url(linkGetUser)
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
                        parseJsonData(jsonObject.getJSONArray("pharmacist_data"));
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

            Pharmacist pharmacist = new Pharmacist();

            pharmacist.setUserId(json.getString("user_id"));
            pharmacist.setPin(json.getString("pin"));
            pharmacist.setName(json.getString("f_name"));
            pharmacist.setLastName(json.getString("l_name"));
            pharmacist.setGender(json.getString("gender"));
            pharmacist.setCitizenID(json.getString("citizen_id"));
            pharmacist.setPharmacyID(json.getString("pharmacist_id"));
            pharmacist.setAddr(json.getString("address"));
            pharmacist.setProvince(json.getInt("province_id"));
            pharmacist.setAmphur(json.getInt("amphur_id"));
            pharmacist.setDistrict(json.getInt("district_id"));
            pharmacist.setPostcode(json.getString("post_number"));
            pharmacist.setTel(json.getString("tel"));
            pharmacist.setEmail(json.getString("email"));
            pharmacist.setPassword(json.getString("password"));
            pharmacist.setStatus(json.getInt("status"));
            pharmacist.setImg_name(json.getString("filename"));

            pharmacist.pharmacists.add(pharmacist);

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
