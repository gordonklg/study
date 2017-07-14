package gordon.study.cache.ehcache3.basic;

public class UserModel {

    private String id;

    private String phone;

    private String email;

    private String openid;

    private String info;

    public UserModel(String id, String info) {
        this.id = id;
        this.info = info;
    }

    public UserModel(String id, String phone, String email, String openid, String info) {
        this.id = id;
        this.phone = phone;
        this.email = email;
        this.openid = openid;
        this.info = info;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean isNull() {
        return false;
    }

    public static class NullUser extends UserModel {

        public NullUser() {
            super(null, null);
        }

        public boolean isNull() {
            return true;
        }
    }
}
