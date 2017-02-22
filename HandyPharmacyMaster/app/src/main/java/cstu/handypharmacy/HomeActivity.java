package cstu.handypharmacy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class HomeActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "HomeActivity";

    private ImageButton btnChat;
    private ImageButton btnMap;
    private ImageButton btnArticle;
    private ImageButton btnProfile;

    private static String email;



    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        btnChat = (ImageButton) findViewById(R.id.chatbtn);
        btnChat.setOnClickListener(this);

        btnMap = (ImageButton) findViewById(R.id.mapbtn);
        btnMap.setOnClickListener(this);

        btnArticle = (ImageButton) findViewById(R.id.articlebtn);
        btnArticle.setOnClickListener(this);

        btnProfile = (ImageButton) findViewById(R.id.profilebtn);
        btnProfile.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chatbtn:
                startActivity(new Intent(v.getContext(), LoginChatActivity.class));
                break;
            case R.id.mapbtn:
                startActivity(new Intent(v.getContext(), StoreForUserMap.class));
                break;
            case R.id.profilebtn:
                startActivity(new Intent(v.getContext(), UserProfile.class));
                UserProfile.setEmailName(email);
                break;
        }
    }

    public static void getemail(String usrEmail) {
        email = usrEmail;
    }
}
