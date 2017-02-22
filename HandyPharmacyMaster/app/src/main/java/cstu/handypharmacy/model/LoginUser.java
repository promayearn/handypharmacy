package cstu.handypharmacy.model;

public class LoginUser {

    private int usrID;
    private String usrEmail;
    private String usrName;
    private String usrPwd;
    private String mobileId;
    private String Name;
    private String LastName;
    private String pinn;
    private int status;
    private int statLogin; // -1: ลงทะเบียนไม่ผ่าน  0: ยังไม่ทำการ login 1: login แล้ว 2: รอลงทะเบียน pinn
    private int userType; // 0: user 1: pharmacy


    private static LoginUser instance;

    public static LoginUser getInstance() {
        if (instance == null) {
            instance = new LoginUser();
        }
        return instance;
    }

    public LoginUser() {

        usrID = 0;
        usrName = "";
        usrPwd = "";

        Name = "";
        LastName = "";
        mobileId = "";
        pinn = "";
        status = 0;
        statLogin = -1;
        userType = 0; // กำหนดค่าเริ่มต้นเป็น user

    }

    public static boolean chkInstance() {
        if (instance == null) {
            return false;
        } else {
            return true;
        }
    }

    public String getUsrEmail() {
        return usrEmail;
    }

    public void setUsrEmail(String usrEmail) {
        this.usrEmail = usrEmail;
    }

    public int getUsrID() {
        return usrID;
    }

    public void setUsrID(int usrID) {
        this.usrID = usrID;
    }

    public String getUsrName() {
        return usrName;
    }

    public void setUsrName(String usrName) {
        this.usrName = usrName;
    }

    public String getUsrPwd() {
        return usrPwd;
    }

    public void setUsrPwd(String usrPwd) {
        this.usrPwd = usrPwd;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public int getStatLogin() {
        return statLogin;
    }

    public void setStatLogin(int statLogin) {
        this.statLogin = statLogin;
    }

    public int getStatLog() {
        return statLogin;
    }

    public void setStatLog(int statLog) {
        this.statLogin = statLog;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int Status) {
        this.status = Status;
    }

    public void setPinn(String pinn) {
        this.pinn = pinn;
    }

    public String getPinn() {
        return pinn;
    }

    public void setMobileId(String mobileId) {
        this.mobileId = mobileId;
    }

    public String getMobileId() {
        return mobileId;
    }
}
