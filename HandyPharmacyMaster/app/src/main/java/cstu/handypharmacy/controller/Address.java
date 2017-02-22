package cstu.handypharmacy.controller;

import android.util.Log;

import java.io.IOException;

import cstu.handypharmacy.model.ResponseStatus;
import cstu.handypharmacy.webService.ProvinceService;

public class Address {

    private static final String TAG = "Address";

    public interface NoParaDataCallback {
        void onFinish();
    }

    /// ข้อมูลจังหวัด
    public static void getProvince(final NoParaDataCallback callback) {
        ProvinceService.getProvince(new ProvinceService.NoParaWebServiceCallback() {

            @Override
            public void onFailure(IOException e) {
                Log.e(TAG, "province error data");
            }

            @Override
            public void onResponse(ResponseStatus responseStatus) {
                if (responseStatus.success) {
                    callback.onFinish();
                } else {
                    Log.i(TAG, "Error: " + responseStatus.message);
                }
            }
        });
    }

    /// ข้อมูลอำเภอ
    public static void getAmphur(String proId, final NoParaDataCallback callback) {
        ProvinceService.getAmphur(proId, new ProvinceService.NoParaWebServiceCallback() {

            @Override
            public void onFailure(IOException e) {
                Log.e(TAG, "error data");
            }

            @Override
            public void onResponse(ResponseStatus responseStatus) {
                if (responseStatus.success) {
                    callback.onFinish();
                } else {
                    Log.i(TAG, "Error: " + responseStatus.message);
                }
            }
        });
    }

    /// ข้อมูลตำบล
    public static void getDistrict(String amphurId, final NoParaDataCallback callback) {
        ProvinceService.getDistrict(amphurId, new ProvinceService.NoParaWebServiceCallback() {

            @Override
            public void onFailure(IOException e) {
                Log.e(TAG, "error data");
            }

            @Override
            public void onResponse(ResponseStatus responseStatus) {
                if (responseStatus.success) {
                    callback.onFinish();
                } else {
                    Log.i(TAG, "Error: " + responseStatus.message);
                }
            }
        });
    }

}
