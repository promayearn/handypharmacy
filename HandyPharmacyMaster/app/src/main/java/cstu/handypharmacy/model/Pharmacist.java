package cstu.handypharmacy.model;

import java.io.File;
import java.util.ArrayList;

public class Pharmacist {

    public static ArrayList<Pharmacist> pharmacists = new ArrayList<Pharmacist>();

    private String device_id;
    private String user_id;
    private String pin;
    private String f_name;
    private String l_name;
    private String gender;
    private String citizen_id;
    private String pharmacist_id;
    private String address;
    private int province_id;
    private int amphur_id;
    private int district_id;
    private String tel;
    private String email;
    private int status;
    private String postcode;
    private String password;
    private File images;
    private String img_name;

    public Pharmacist() {
        province_id = 0;
        amphur_id = 0;
        district_id = 0;
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

    public int getAmphur() {
        return amphur_id;
    }

    public void setAmphur(int amphur) {
        amphur_id = amphur;
    }

    public int getDistrict() {
        return district_id;
    }

    public void setDistrict(int district) {
        district_id = district;
    }

    public File getImages() {
        return images;
    }

    public void setImages(File images) {
        this.images = images;
    }

    public String getDevileID() {
        return device_id;
    }

    public void setDevileID(String deviceID) {
        this.device_id = deviceID;
    }

    public String getName() {
        return f_name;
    }

    public void setName(String firstName) {
        f_name = firstName;
    }

    public String getLastName() {
        return l_name;
    }

    public void setLastName(String lastName) {
        l_name = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String Gender) {
        gender = Gender;
    }

    public String getCitizenID() {
        return citizen_id;
    }

    public void setCitizenID(String citizenID) {
        citizen_id = citizenID;
    }

    public String getPharmacyID() {
        return pharmacist_id;
    }

    public void setPharmacyID(String pharmacistID) {
        pharmacist_id = pharmacistID;
    }

    public String getAddr() {
        return address;
    }

    public void setAddr(String addr) {
        address = addr;
    }

    public int getProvince() {
        return province_id;
    }

    public void setProvince(int province) {
        province_id = province;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String Tel) {
        tel = Tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String Email) {
        email = Email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String Password) {
        password = Password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int Status) {
        status = Status;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

}
