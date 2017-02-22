package cstu.handypharmacy.webService;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cstu.handypharmacy.model.ResponseStatus;
import cstu.handypharmacy.model.User;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UsersService {
    private static final String TAG = "UsersService";

    private static final String linkGetUser = ServerName.BASE_URL + "getUsersData.php";

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
    public static void getUsersData(final NoParaWebServiceCallback callback) {
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
                        parseJsonData(jsonObject.getJSONArray("users"));
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

            User user = new User();

            user.setId(json.getString("user_id"));
            user.setName(json.getString("name"));
            user.setEmail(json.getString("email"));

            user.useres.add(user);

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