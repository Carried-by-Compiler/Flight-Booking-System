/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import business_layer.AirlineManager;

/**
 *
 * @author John Rey Juele
 */
public class Test {
    
    public static void main(String[] args) {
        AirlineManager a = new AirlineManager();
        a.addAirline(3, "LUFTHANSA", "lufthansa");
        
        System.out.println(a.searchAirline(2));
    }
    
    
}
