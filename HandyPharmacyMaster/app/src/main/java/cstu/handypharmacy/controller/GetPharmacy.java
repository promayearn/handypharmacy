package cstu.handypharmacy.controller;

import android.util.Log;

import java.io.IOException;

import cstu.handypharmacy.model.ResponseStatus;
import cstu.handypharmacy.webService.PharmacyService;

public class GetPharmacy {

    private static final String TAG = "GetPharmacy";

    public interface DataCallback {
        void onFinish();
    }

    public static void saveLatLng(double lat, double lng, final DataCallback callback) {
        PharmacyService.save(lat, lng, new PharmacyService.WebServiceCallback() {
            @Override
            public void onFailure(IOException e) {
                Log.e(TAG, "error data");
            }

            @Override
            public void onResponse(ResponseStatus responseStatus) {
                if (responseStatus.success) {
                    callback.onFinish();
                } else {
                    Log.i(TAG, "check code" + responseStatus.message);
                }
            }
        });
    }

    public static void getStoreData(final DataCallback callback) {
        PharmacyService.getStoreData(new PharmacyService.NoParaWebServiceCallback() {
            @Override
            public void onFailure(IOException e) {
                Log.e(TAG, "error data");
            }

            @Override
            public void onResponse(ResponseStatus responseStatus) {
                if (responseStatus.success) {
                    callback.onFinish();
                } else {
                    Log.i(TAG, "check code" + responseStatus.message);
                }
            }
        });
    }

}

