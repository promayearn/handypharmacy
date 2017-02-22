package cstu.handypharmacy.controller;

import android.util.Log;

import java.io.IOException;

import cstu.handypharmacy.model.Pharmacist;
import cstu.handypharmacy.model.Pharmacy;
import cstu.handypharmacy.model.LoginUser;
import cstu.handypharmacy.model.Member;
import cstu.handypharmacy.model.ResponseStatus;
import cstu.handypharmacy.webService.RegisterService;

public class RegisMember {

    private static final String TAG = "RegisMember";

    public interface DataCallback {
        void onFinish(LoginUser loginUser);
    }

    public interface NoParaDataCallback {
        void onFinish();
    }

    /// ลงทะเบียนผู้ใช้
    public static void register(Member members, final DataCallback callback) {
        RegisterService.regis(members, new RegisterService.WebServiceCallback() {
            @Override
            public void onFailure(IOException e) {
                Log.e(TAG, "error data");
            }

            @Override
            public void onResponse(ResponseStatus responseStatus, LoginUser loginUser) {
                if (responseStatus.success) {
                    callback.onFinish(loginUser);
                } else {
                    Log.i(TAG, "check code" + responseStatus.message);
                }
            }
        });
    }

    // ทะเบียน pinn
    public static void reggisPinn(String pinn, final DataCallback callback) {
        RegisterService.regisPinn(pinn, new RegisterService.WebServiceCallback() {
            @Override
            public void onFailure(IOException e) {
                Log.e(TAG, "error data");
            }

            @Override
            public void onResponse(ResponseStatus responseStatus, LoginUser loginUser) {
                if (responseStatus.success) {
                    callback.onFinish(loginUser);
                } else {
                    Log.i(TAG, "check code" + responseStatus.message);
                }
            }
        });
    }

    //ตรวจสอบข้อมูลการลงทะเบียน
    public static void chkRegis(LoginUser loginUser, final DataCallback callback) {
        RegisterService.chkLogin(loginUser, new RegisterService.WebServiceCallback() {
            @Override
            public void onFailure(IOException e) {
                Log.e(TAG, "error data");
            }

            @Override
            public void onResponse(ResponseStatus responseStatus, LoginUser loginUser) {
                callback.onFinish(loginUser);
                Log.i(TAG, "deviceID: " + loginUser.getMobileId() + " pin: " + loginUser.getPinn());
            }
        });
    }

    /// ลงทะเบียน เภสัช
    public static void regisPharmacist(Pharmacist pharmacist, final DataCallback callback) {
        RegisterService.regisPharmacist(pharmacist, new RegisterService.WebServiceCallback() {
            @Override
            public void onFailure(IOException e) {
                Log.e(TAG, "error data");
            }

            @Override
            public void onResponse(ResponseStatus responseStatus, LoginUser loginUser) {
                callback.onFinish(loginUser);
                Log.i(TAG, "deviceID: " + loginUser.getMobileId() + " pin: " + loginUser.getPinn());
            }
        });
    }

    /// ลงทะเบียนร้านยา
    public static void regisDrugstore(Pharmacy pharmacy, final NoParaDataCallback callback) {
        RegisterService.regisDrug(pharmacy, new RegisterService.NoParaWebServiceCallback() {
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
