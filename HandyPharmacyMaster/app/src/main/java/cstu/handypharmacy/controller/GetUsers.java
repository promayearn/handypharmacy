package cstu.handypharmacy.controller;

import android.util.Log;

import java.io.IOException;

import cstu.handypharmacy.model.ResponseStatus;
import cstu.handypharmacy.webService.UsersService;

/**
 * Created by Seeuintupro on 7/6/2559.
 */
public class GetUsers {
    private static final String TAG = "getUsers";



    public interface DataCallback {
        void onFinish();
    }


    public static void getUsersData(final DataCallback callback) {
        UsersService.getUsersData(new UsersService.NoParaWebServiceCallback() {
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