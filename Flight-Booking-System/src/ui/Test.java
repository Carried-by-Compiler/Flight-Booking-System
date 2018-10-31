/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import business_layer.AirlineManager;
import business_layer.Flight;
import business_layer.FlightsManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import business_layer.CustomerManager;

/**
 *
 * @author John Rey Juele
 */
public class Test {
    
    public static void main(String[] args) {
        AirlineManager a = new AirlineManager();
        FlightsManager f = new FlightsManager();
        
        CustomerManager c = new CustomerManager();
        c.addCustomer(2, "Dave");
        
        System.out.println(a.searchAirline(2));
        System.out.println(c.searchCustomer(1));
        LocalDateTime from = LocalDateTime.of(2019, Month.FEBRUARY, 15, 15, 30);
        LocalDateTime to   = LocalDateTime.of(2019, Month.FEBRUARY, 15, 22, 50);
        f.addFlight(0, 1, "DUBLIN", "BOSTON", from, to);
        
        from = LocalDateTime.of(2019, Month.JANUARY, 1, 10, 05);
        to   = LocalDateTime.of(2019, Month.JANUARY, 1, 11, 05);
        f.addFlight(1, 2, "SHANNON", "STANSTED", from, to);
        
        from = LocalDateTime.of(2019, Month.MARCH, 22, 8, 25);
        to   = LocalDateTime.of(2019, Month.MARCH, 22, 21, 45);
        f.addFlight(2, 4, "HEATHROW", "HONGKONG", to, from);
        
        a.addAirline(4, "CATHAYPACIFIC", "hongkong");
        a.addAirline(5, "UNITED", "usa");
        a.addAirline(6, "EMIRATES", "emirates");
        a.addAirline(7, "QANTAS", "australia");
        
        LocalDate dDate = LocalDate.of(2019, Month.MARCH, 22);
                
                
        String[] criteria = {
            "HEATHROW",
            "HONGKONG",
            dDate.toString()
        };
        ArrayList<Flight> flights = f.getFlightsOneWay(criteria);
        for(Flight flight : flights) {
            System.out.println(flight.toString());
        }
    }
    
    
}
