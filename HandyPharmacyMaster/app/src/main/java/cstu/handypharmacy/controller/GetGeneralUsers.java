package cstu.handypharmacy.controller;

import android.util.Log;

import java.io.IOException;

import cstu.handypharmacy.model.ResponseStatus;
import cstu.handypharmacy.webService.GeneralUsersService;

/**
 * Created by Seeuintupro on 8/6/2559.
 */
public class GetGeneralUsers {
    private static final String TAG = "getGeneralUsers";



    public interface DataCallback {
        void onFinish();
    }


    public static void getGeneralUsersData(final DataCallback callback) {
        GeneralUsersService.getGeneralUsersData(new GeneralUsersService.NoParaWebServiceCallback(){
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
