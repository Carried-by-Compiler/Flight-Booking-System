/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import business_layer.AirlineManager;
import business_layer.CustomerManager;

/**
 *
 * @author John Rey Juele
 */
public class Test {
    
    public static void main(String[] args) {
        AirlineManager a = new AirlineManager();
        a.addAirline(3, "LUFTHANSA", "lufthansa");
        
        
        CustomerManager c = new CustomerManager();
        c.addCustomer(2, "Dave");
        
        System.out.println(a.searchAirline(2));
        System.out.println(c.searchCustomer(1));
    }
    
    
}
