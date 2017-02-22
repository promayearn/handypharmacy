package cstu.handypharmacy.controller;

import android.util.Log;

import java.io.IOException;

import cstu.handypharmacy.model.ResponseStatus;
import cstu.handypharmacy.webService.DrugService;

public class DrugController {

    private static final String TAG = "DrugController";

    public interface NoParaDataCallback {
        void onFinish();
    }

    /// จึงข้อมูลยา
    public static void getDrug(final NoParaDataCallback callback) {
        DrugService.getDrug(new DrugService.NoParaWebServiceCallback() {
            @Override
            public void onFailure(IOException e) {
                Log.e(TAG, "error data");
            }

            @Override
            public void onResponse(ResponseStatus responseStatus) {
                callback.onFinish();
            }
        });
    }

}
