package cstu.handypharmacy.webService;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cstu.handypharmacy.model.Drug;
import cstu.handypharmacy.model.ResponseStatus;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DrugService {

    private static final String TAG = "DrugService";

    private static final String linkDrug = ServerName.BASE_URL + "getDrugData.php";

    private static final OkHttpClient mClient = new OkHttpClient();
    private static ResponseStatus mResponseStatus;

    public interface NoParaWebServiceCallback {
        void onFailure(IOException e);
        void onResponse(ResponseStatus responseStatus);
    }

    public static void getDrug(final NoParaWebServiceCallback callback) {
        Request request = new Request.Builder()
                .url(linkDrug)
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

                    if (success == 1) {
                        mResponseStatus = new ResponseStatus(true, null);
                        parseJsonData(jsonObject.getJSONArray("drug_data"));
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

    /// ดึงข้อมูลยาใส่ arraylist
    private static void parseJsonData(JSONArray jsonArrayData) throws JSONException {

        // ล้างข้อมูลเก่าออกก่อน
        Drug.drugs.clear();

        for (int i = 0; i < jsonArrayData.length(); i++) {
            JSONObject json = jsonArrayData.getJSONObject(i);

            Drug drug = new Drug();

            drug.setDrug_id(json.getString("drug_id"));
            drug.setDrug_normal_name(json.getString("drug_normal_name"));
            drug.setDrug_trade_name(json.getString("drug_trade_name"));
            drug.setDrug_dose(json.getString("drug_dose"));
            drug.setDrug_type(json.getString("drug_type"));
            drug.setDrug_properties(json.getString("drug_properties"));
            drug.setDrug_how_use(json.getString("drug_how_use"));
            drug.setDrug_pharm_know(json.getString("drug_pharm_know"));
            drug.setDrug_forget(json.getString("drug_forget"));
            drug.setDrug_side_effect(json.getString("drug_side_effect"));
            drug.setDrug_dang_effect(json.getString("drug_dang_effect"));
            drug.setDrug_keep(json.getString("drug_keep"));

            Drug.drugs.add(drug);
        }
    }

}
