package module;

public class loginDetail {
    String userName;
    String password;
    String Type;

    public loginDetail() {
    }

    public loginDetail(String userName, String password, String type) {
        this.userName = userName;
        this.password = password;
        Type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    @Override
    public String toString() {
        return "loginDetail{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", Type='" + Type + '\'' +
                '}';
    }

}
