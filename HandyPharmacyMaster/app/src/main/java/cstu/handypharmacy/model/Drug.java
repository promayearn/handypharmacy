package cstu.handypharmacy.model;

import java.util.ArrayList;

public class Drug {

    public static ArrayList<Drug> drugs = new ArrayList<>();

    private String drug_id;
    private String drug_normal_name;
    private String drug_trade_name;
    private String drug_dose;
    private String drug_type;
    private String drug_properties;
    private String drug_how_use;
    private String drug_pharm_know;
    private String drug_forget;
    private String drug_side_effect;
    private String drug_dang_effect;
    private String drug_keep;

    public String getDrug_id() {
        return drug_id;
    }

    public void setDrug_id(String drug_id) {
        this.drug_id = drug_id;
    }

    public String getDrug_normal_name() {
        return drug_normal_name;
    }

    public void setDrug_normal_name(String drug_normal_name) {
        this.drug_normal_name = drug_normal_name;
    }

    public String getDrug_trade_name() {
        return drug_trade_name;
    }

    public void setDrug_trade_name(String drug_trade_name) {
        this.drug_trade_name = drug_trade_name;
    }

    public String getDrug_dose() {
        return drug_dose;
    }

    public void setDrug_dose(String drug_dose) {
        this.drug_dose = drug_dose;
    }

    public String getDrug_type() {
        return drug_type;
    }

    public void setDrug_type(String drug_type) {
        this.drug_type = drug_type;
    }

    public String getDrug_properties() {
        return drug_properties;
    }

    public void setDrug_properties(String drug_properties) {
        this.drug_properties = drug_properties;
    }

    public String getDrug_how_use() {
        return drug_how_use;
    }

    public void setDrug_how_use(String drug_how_use) {
        this.drug_how_use = drug_how_use;
    }

    public String getDrug_pharm_know() {
        return drug_pharm_know;
    }

    public void setDrug_pharm_know(String drug_pharm_know) {
        this.drug_pharm_know = drug_pharm_know;
    }

    public String getDrug_forget() {
        return drug_forget;
    }

    public void setDrug_forget(String drug_forget) {
        this.drug_forget = drug_forget;
    }

    public String getDrug_side_effect() {
        return drug_side_effect;
    }

    public void setDrug_side_effect(String drug_side_effect) {
        this.drug_side_effect = drug_side_effect;
    }

    public String getDrug_dang_effect() {
        return drug_dang_effect;
    }

    public void setDrug_dang_effect(String drug_dang_effect) {
        this.drug_dang_effect = drug_dang_effect;
    }

    public String getDrug_keep() {
        return drug_keep;
    }

    public void setDrug_keep(String drug_keep) {
        this.drug_keep = drug_keep;
    }
}
