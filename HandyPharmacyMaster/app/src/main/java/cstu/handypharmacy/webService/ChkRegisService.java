package cstu.handypharmacy.webService;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cstu.handypharmacy.model.LoginUser;
import cstu.handypharmacy.model.ResponseStatus;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChkRegisService {


    private static final String TAG = "ChkRegisService";

    private static final String linkREgis = ServerName.BASE_URL + "regisMember.php";

    private static final OkHttpClient mClient = new OkHttpClient();
    private static ResponseStatus mResponseStatus;

    private static LoginUser loginUser = LoginUser.getInstance();

    public interface WebServiceCallback {
        void onFailure(IOException e);

        void onResponse(ResponseStatus responseStatus, LoginUser loginUser);
    }

    public static void chkLogin(String deviceId, final WebServiceCallback callback) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("deviceId", deviceId);

        RequestBody formBody = builder.build();

        Request request = new Request.Builder()
                .url(linkREgis)
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
                    loginUser.setStatLog(success);

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
                                callback.onResponse(mResponseStatus, loginUser);
                            }
                        }
                );
            }
        });
    }


}
