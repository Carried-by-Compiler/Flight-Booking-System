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
    private String name;
    
    
    public Customer(){
        this.id = 0;
        this.name = "";
    }
    
    public Customer(int uid, String uname){
        this.id = uid;
        this.name = uname;
    }
    
    public int getID(){
        return this.id;
    }
    
    public void setID(int uid){
       this.id = uid;
    }
   
    
    public String getName(){
        return name;
    }
    
    public void setName(String uName){
        this.name = uName;
    }
    
    public String toString() {
        String message = "ID:\t\t" + this.id + "\nCustomer Name:\t" + this.name + "\n";
        
        return message;
    }
}

