package hnu.fooma.yunlin.entity;

/**
 * Created by Fooma on 2016/4/22.
 */
public class Member {
    public int memberId;
    public int head;
    public String account;
    public String phone;
    public String password;
    public String mlongitude;
    public String mlatitude;
    public String gender;
    public String registTime;
    public int enable;

    public Member() {
    }

    public Member(String account, String password) {
        this.account = account;
        this.password = password;
    }
}
