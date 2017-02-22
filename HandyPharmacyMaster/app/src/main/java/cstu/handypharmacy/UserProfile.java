package cstu.handypharmacy;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import cstu.handypharmacy.controller.GetGeneralUsers;
import cstu.handypharmacy.controller.GetPharmacist;
import cstu.handypharmacy.model.Member;
import cstu.handypharmacy.model.Pharmacist;
import cstu.handypharmacy.model.User;

/**
 * Created by Seeuintupro on 14/6/2559.
 */
public class UserProfile extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "UserDetail";

    private static String emailName;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        User.useres.clear();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_detail);
        Log.e(TAG, "username " + emailName);

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


        final ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Loading system data...");
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();

        GetGeneralUsers.getGeneralUsersData(new GetGeneralUsers.DataCallback() {
            @Override
            public void onFinish() {
                for (int j = 0; j < Member.members.size(); j++) {
                    if (Member.members.get(j).getEtEmail().equals(emailName)) {
                        Log.e(TAG, "Yes " + Member.members.get(j).getEtEmail());
                        header.setText("General User");
                        r11.setText("Name");
                        if (Member.members.get(j).geteGender().equals("M")) {
                            r12.setText("นาย " + Member.members.get(j).getEtName());
                        } else {
                            r12.setText("นาง/นางสาว " + Member.members.get(j).getEtName());
                        }
                        r21.setText("Lastname");
                        r22.setText(Member.members.get(j).geteLastName());
                        r31.setText("Email");
                        r32.setText(Member.members.get(j).getEtEmail());
                        r41.setText("Age");
                        r42.setText(Member.members.get(j).getEtBirthday());
                        r51.setText("Blood Type");
                        r52.setText(Member.members.get(j).geteBlood());
                        r61.setText("Weight");
                        r62.setText(Member.members.get(j).getEtWeight() + " KG.");
                        r71.setText("Height");
                        r72.setText(Member.members.get(j).getEtHeight() + " CM.");
                        r81.setText("Drug Allergy");
                        r82.setText(Member.members.get(j).getEtDrugAllergy());
                        r91.setText("Congenital Disease");
                        r92.setText(Member.members.get(j).getEtDisease());
                        progress.dismiss();
                        break;
                    }
                }
            }
        });
        GetPharmacist.getPharmacistData(new GetPharmacist.DataCallback() {
            @Override
            public void onFinish() {
                for (int k = 0; k < Pharmacist.pharmacists.size(); k++) {
                    if (Pharmacist.pharmacists.get(k).getEmail().equals(emailName)) {
                        Log.e(TAG, "Yes " + Pharmacist.pharmacists.get(k).getEmail());
                        header.setText("Pharmacist");
                        r11.setText("Name");
                        if (Pharmacist.pharmacists.get(k).getGender().equals("M")) {
                            r12.setText("ภก." + Pharmacist.pharmacists.get(k).getName());
                        } else {
                            r12.setText("ภกญ." + Pharmacist.pharmacists.get(k).getName());
                        }
                        r12.setText(Pharmacist.pharmacists.get(k).getName());
                        r21.setText("Lastname");
                        r22.setText(Pharmacist.pharmacists.get(k).getLastName());
                        r31.setText("Email");
                        r32.setText(Pharmacist.pharmacists.get(k).getEmail());
                        r41.setText("Pharmacist ID");
                        r42.setText(Pharmacist.pharmacists.get(k).getPharmacyID());
                        r51.setText("Citizen ID");
                        r52.setText(Pharmacist.pharmacists.get(k).getCitizenID());
                        r61.setText("Tel");
                        r62.setText(Pharmacist.pharmacists.get(k).getTel());
                        progress.dismiss();
                        break;
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    public static void setEmailName(String username) {
        emailName = username;
    }

}
