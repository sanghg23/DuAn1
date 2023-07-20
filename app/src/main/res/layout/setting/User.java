package khanhnqph30151.fptpoly.duan1.setting;

public class User {

    String user_name,user_pass,user_role;


    public User() {
    }

    public User(String user_name) {
        this.user_name = user_name;
    }

    public User(String user_name, String user_pass, String user_role) {
        this.user_name = user_name;
        this.user_pass = user_pass;
        this.user_role = user_role;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_pass() {
        return user_pass;
    }

    public void setUser_pass(String user_pass) {
        this.user_pass = user_pass;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }
}
