package cstu.handypharmacy.model;

import java.util.ArrayList;

public class Province {

    public static ArrayList<Province> provinces = new ArrayList<Province>();
    public static Province instance;

    private String proId;
    private String proName;

    public static Province getInstance() {
        if (instance == null) {
            instance = new Province();
        }
        return instance;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }
}
