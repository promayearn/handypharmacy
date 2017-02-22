package cstu.handypharmacy.model;

import java.util.ArrayList;

public class Amphur {

    public static ArrayList<Amphur> amphurs = new ArrayList<Amphur>();
    public static Amphur instance;

    private String AmphurId;
    private String AmphurName;

    public static Amphur getInstance() {
        if (instance == null) {
            instance = new Amphur();
        }
        return instance;
    }

    public String getAmphurId() {
        return AmphurId;
    }

    public void setAmphurId(String amphurId) {
        AmphurId = amphurId;
    }

    public String getAmphurName() {
        return AmphurName;
    }

    public void setAmphurName(String amphurName) {
        AmphurName = amphurName;
    }
}
