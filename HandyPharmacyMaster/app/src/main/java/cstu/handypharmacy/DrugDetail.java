package cstu.handypharmacy;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import cstu.handypharmacy.controller.DrugController;
import cstu.handypharmacy.model.Drug;

/**
 * Created by Seeuintupro on 12/6/2559.
 */
public class DrugDetail extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "DrugDetail";

    private static String DrugName;

    private final static int REQUEST_READ_STORAGE_RESULT = 1;

    private TextView header;
    private TextView r11;
    private TextView r12;
    private TextView r21;
    private TextView r22;
    private TextView r31;
    private TextView r32;
    private TextView r41;
    private TextView r42;
    private TextView r51;
    private TextView r52;
    private TextView r61;
    private TextView r62;
    private TextView r71;
    private TextView r72;
    private TextView r81;
    private TextView r82;
    private TextView r91;
    private TextView r92;
    private TextView r101;
    private TextView r102;
    private TextView r111;
    private TextView r112;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Drug.drugs.clear();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drug_detail);
        Log.e(TAG, "Drugname : " + DrugName);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_READ_STORAGE_RESULT);
            }
        }

        header = (TextView) findViewById(R.id.header);
        r11 = (TextView) findViewById(R.id.row1_1);
        r12 = (TextView) findViewById(R.id.row1_2);
        r21 = (TextView) findViewById(R.id.row2_1);
        r22 = (TextView) findViewById(R.id.row2_2);
        r31 = (TextView) findViewById(R.id.row3_1);
        r32 = (TextView) findViewById(R.id.row3_2);
        r41 = (TextView) findViewById(R.id.row4_1);
        r42 = (TextView) findViewById(R.id.row4_2);
        r51 = (TextView) findViewById(R.id.row5_1);
        r52 = (TextView) findViewById(R.id.row5_2);
        r61 = (TextView) findViewById(R.id.row6_1);
        r62 = (TextView) findViewById(R.id.row6_2);
        r71 = (TextView) findViewById(R.id.row7_1);
        r72 = (TextView) findViewById(R.id.row7_2);
        r81 = (TextView) findViewById(R.id.row8_1);
        r82 = (TextView) findViewById(R.id.row8_2);
        r91 = (TextView) findViewById(R.id.row9_1);
        r92 = (TextView) findViewById(R.id.row9_2);
        r101 = (TextView) findViewById(R.id.row10_1);
        r102 = (TextView) findViewById(R.id.row10_2);
        r111 = (TextView) findViewById(R.id.row11_1);
        r112 = (TextView) findViewById(R.id.row11_2);

        DrugController.getDrug(new DrugController.NoParaDataCallback() {
            @Override
            public void onFinish() {
                Log.e(TAG, "Drug size " + Drug.drugs.size());
                for (int i = 0; i < Drug.drugs.size(); i++) {
                    if (Drug.drugs.get(i).getDrug_normal_name().equals(DrugName)) {
                        header.setText("Drug Information");
                        r11.setText("ชื่อสามัญ");
                        r12.setText(Drug.drugs.get(i).getDrug_normal_name());
                        r21.setText("ชื่อทางการค้า");
                        r22.setText(Drug.drugs.get(i).getDrug_trade_name());
                        r31.setText("ขนาด");
                        r32.setText(Drug.drugs.get(i).getDrug_dose());
                        r41.setText("ประเภท");
                        r42.setText(Drug.drugs.get(i).getDrug_type());
                        r51.setText("คุณสมบัติ");
                        r52.setText(Drug.drugs.get(i).getDrug_properties());
                        r61.setText("วิธีใช้");
                        r62.setText(Drug.drugs.get(i).getDrug_how_use());
                        r71.setText("สิ่งที่ควรแจ้งให้เภสัชกรทราบ");
                        r72.setText(Drug.drugs.get(i).getDrug_pharm_know());
                        r81.setText("หากลืมรับประทาน");
                        r82.setText(Drug.drugs.get(i).getDrug_forget());
                        r91.setText("ผลข้างเคียงทั่วไป");
                        r92.setText(Drug.drugs.get(i).getDrug_side_effect());
                        r101.setText("ผลข้างเคียงร้ายแรง");
                        r102.setText(Drug.drugs.get(i).getDrug_dang_effect());
                        r111.setText("การเก็บรักษา");
                        r112.setText(Drug.drugs.get(i).getDrug_keep());
                        break;
                    } else if((i+1)>=Drug.drugs.size()){
                        initiatePopupWindow("Can't find Drug Name Please return to Chatroom");
                    } else {
                        continue;
                    }
                }
            }
        });
    }

    private PopupWindow pw;
    private void initiatePopupWindow(CharSequence a) {
        try {
            //We need to get the instance of the LayoutInflater, use the context of this activity
            LayoutInflater inflater = (LayoutInflater) DrugDetail.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //Inflate the view from a predefined XML layout
            View layout = inflater.inflate(R.layout.popup_layout,
                    (ViewGroup) findViewById(R.id.popup_element));
            // create a 300px width and 470px height PopupWindow
            pw = new PopupWindow(layout, 1300, 600, true);
            pw.showAtLocation(layout, Gravity.CENTER, 0, 0);

            TextView mResultText = (TextView) layout.findViewById(R.id.server_status_text);
            mResultText.setText(a);
            Button cancelButton = (Button) layout.findViewById(R.id.end_data_send_button);
            cancelButton.setOnClickListener(cancel_button_click_listener);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener cancel_button_click_listener = new View.OnClickListener() {
        public void onClick(View v) {
            pw.dismiss();
        }
    };

    @Override
    public void onClick(View v) {

    }

    public static void setDrugName(String drugname) {
        DrugName = drugname;
    }

}
