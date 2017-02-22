package cstu.handypharmacy.model;

import java.io.File;
import java.util.ArrayList;

public class Pharmacy {

    public static ArrayList<Pharmacy> pharmacies = new ArrayList<Pharmacy>();

    private String store_name;
    private String store_address;
    private String store_tel;
    private String openTime;
    private String closeTime;
    private String day_open;
    private int province;
    private int amphur;
    private int district;
    private String postcode;
    private double lat;
    private double lng;
    private int status;
    private int quality;
    private File images;

    public Pharmacy() {

        store_name = "";
        store_address = "";
        store_tel = "";
        openTime = "";
        closeTime = "";
        day_open = "";
        province = 0;
        amphur = 0;
        district = 0;
        postcode = "";
        lat = 0;
        lng = 0;
        status = 0;
        quality = 0;

    }

    // ประกาศ singleton เพื่อใช้สำหรับการลงทะเบียนร้านยา
    private static Pharmacy instance;

    public static Pharmacy getInstance() {
        if (instance == null) {
            instance = new Pharmacy();
        }
        return instance;
    }

    public String getStore_name() {
        return store_name;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getStore_addr() {
        return store_address;
    }

    public void setStore_addr(String store_addr) {
        this.store_address = store_addr;
    }

    public String getStore_tel() {
        return store_tel;
    }

    public void setStore_tel(String store_tel) {
        this.store_tel = store_tel;
    }

    public String getDay_open() {
        return day_open;
    }

    public void setDay_open(String day_open) {
        this.day_open = day_open;
    }

    public int getProvince() {
        return province;
    }

    public void setProvince(int province) {
        this.province = province;
    }

    public int getAmphur() {
        return amphur;
    }

    public void setAmphur(int amphur) {
        this.amphur = amphur;
    }

    public int getDistrict() {
        return district;
    }

    public void setDistrict(int district) {
        this.district = district;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getStatus() {
        return status;
    }

    public void setStatus(int Status) {
        this.status = Status;
    }

    public double getQuality() {
        return quality;
    }

    public void setQuality(int Quality) {
        this.quality = Quality;
    }

    public File getImages() {
        return images;
    }

    public void setImages(File images) {
        this.images = images;
    }
}
