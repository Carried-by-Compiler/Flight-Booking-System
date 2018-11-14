/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business_layer;

/**
 *
 * @author eoghan
 */
public class Customer {
    private int id;
    private String fName;
    private String lName;
    private String email;
    private String password;
    
    
    public Customer(){
        this.id = 0;
        this.fName = "";
        this.lName = "";
        this.email = "";
        this.password = "";
    }
    
    public Customer(int uid, String uname, String sname, String email, String password){
        this.id = uid;
        this.fName = uname;
        this.lName = sname;
        this.email = email;
        this.password = password;
    }

    public Customer(String name, String lName, String e, String password) {
        this.fName = name;
        this.lName = lName;
        this.email = e;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }
    
    public void setEmail(String newEmail) {
        this.email = newEmail;
    }
    
    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", fName=" + fName + ", lName=" + lName + ", email=" + email + '}';
    }
    
}

