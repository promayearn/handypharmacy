package cstu.handypharmacy.controller;

import android.util.Log;

import java.io.IOException;

import cstu.handypharmacy.model.ResponseStatus;
import cstu.handypharmacy.webService.PharmacistService;

/**
 * Created by Seeuintupro on 8/6/2559.
 */
public class GetPharmacist {
    private static final String TAG = "getPharmacist";



    public interface DataCallback {
        void onFinish();
    }


    public static void getPharmacistData(final DataCallback callback) {
        PharmacistService.getPharmacistData(new PharmacistService.NoParaWebServiceCallback(){
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
