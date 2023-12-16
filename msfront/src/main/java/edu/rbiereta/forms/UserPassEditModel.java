package edu.rbiereta.forms;

public class UserPassEditModel {

    private String name,oldname, pass, oldpass;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    
    public String getOldName() {
        return oldname;
    }

    public void setOldName(String oldname) {
        this.oldname = oldname;
    }

    public String getOldPass() {
        return oldpass;
    }

    public void setOldPass(String oldpass) {
        this.oldpass = oldpass;
    }
}