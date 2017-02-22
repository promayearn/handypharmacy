package cstu.handypharmacy.model;

import java.util.ArrayList;

public class District {

    public static ArrayList<District> districts = new ArrayList<District>();
    public static District instance;

    private String disId;
    private String disName;

    public static District getInstance() {
        if (instance == null) {
            instance = new District();
        }
        return instance;
    }

    public String getDisId() {
        return disId;
    }

    public void setDisId(String disId) {
        this.disId = disId;
    }

    public String getDisName() {
        return disName;
    }

    public void setDisName(String disName) {
        this.disName = disName;
    }
}
