/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business_layer;

/**
 *
 * @author John Rey Juele
 */
public class Airline implements Comparable<Airline> {
    private int id;
    private String name;
    private String password;
    
    public Airline() {
        this.id = 0;
        this.name = "";
        this.password = "";
    }
    
    public Airline(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }
    
    @Override
    public int compareTo(Airline other) {
        
        if(this.id == other.getID()) {
            return 1;
        } else {
            return 0;
        }
    }
    
    public void setID(int id)               { this.id = id; }
    public int  getID()                     { return this.id; }
    
    public void setName(String n)           { this.name = n; }
    public String getName()                 { return this.name; }
    
    public void setPassword(String p)       { this.password = p; }
    public String getPassword()             { return this.password; }
    
    public String toString() {
        String message = "ID:\t\t" + this.id + "\nAirline:\t" + this.name + "\n";
        
        return message;
    }
}
