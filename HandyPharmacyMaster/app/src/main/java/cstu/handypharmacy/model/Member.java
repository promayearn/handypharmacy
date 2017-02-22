package cstu.handypharmacy.model;

import java.io.File;
import java.util.ArrayList;

public class Member {

    public static ArrayList<Member> members = new ArrayList<Member>();

    private String user_id;
    private String pin;
    private String mobileId;                    // รหัสเครื่องมือถือ
    private String etName;                      // ชื่อผู้ใช้
    private String eLastName;                   // นามสกุล
    private String etBirthday;                  // วันเกิด
    private String etWeight;                    // น้ำหนัก
    private String etHeight;                    // ความสูง
    private String etDrugAllergy;     // รายการยาที่แพ้
    private String etDisease;                   // โรคประจำตัว
    private String eBlood;                      // หมู่เลือด
    private String eGender;                     //เพศ
    private String etEmail;
    private String etPwd;
    private String img_name;
    private File images;

    public String getEtPwd() {
        return etPwd;
    }

    public void setEtPwd(String etPwd) {
        this.etPwd = etPwd;
    }

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String userid) {
        user_id= userid;
    }

    public String getImg_name() {
        return img_name;
    }

    public void setImg_name(String imgname) {
        img_name= imgname;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pinn) {
        pin= pinn;
    }

    public String getEtEmail() {
        return etEmail;
    }

    public void setEtEmail(String etEmail) {
        this.etEmail = etEmail;
    }

    public String geteLastName() {
        return eLastName;
    }

    public void seteLastName(String eLastName) {
        this.eLastName = eLastName;
    }

    public String geteBlood() {
        return eBlood;
    }

    public void seteBlood(String eBlood) {
        this.eBlood = eBlood;
    }

    public String geteGender() {
        return eGender;
    }

    public void seteGender(String eGender) {
        this.eGender = eGender;
    }

    public String getMobileId() {
        return mobileId;
    }

    public void setMobileId(String mobileId) {
        this.mobileId = mobileId;
    }

    public String getEtName() {
        return etName;
    }

    public void setEtName(String etName) {
        this.etName = etName;
    }

    public String getEtBirthday() {
        return etBirthday;
    }

    public void setEtBirthday(String etBirthday) {
        this.etBirthday = etBirthday;
    }

    public String getEtWeight() {
        return etWeight;
    }

    public void setEtWeight(String etWeight) {
        this.etWeight = etWeight;
    }

    public String getEtHeight() {
        return etHeight;
    }

    public void setEtHeight(String etHeight) {
        this.etHeight = etHeight;
    }

    public String getEtDisease() {
        return etDisease;
    }

    public void setEtDisease(String etDisease) {
        this.etDisease = etDisease;
    }

    public String getEtDrugAllergy() {
        return etDrugAllergy;
    }

    public void setEtDrugAllergy(String etDrugAllergy) {
        this.etDrugAllergy = etDrugAllergy;
    }

    public File getImages() {
        return images;
    }

    public void setImages(File images) {
        this.images = images;
    }
}
