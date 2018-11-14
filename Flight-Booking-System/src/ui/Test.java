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
import java.time.Month;
import java.util.ArrayList;
import business_layer.CustomerManager;
import java.util.Scanner;

/**
 *
 * @author John Rey Juele
 */
public class Test {
    
    public static void main(String[] args) {
        AirlineManager a = new AirlineManager();
        FlightsManager f = new FlightsManager();
        CustomerManager c = new CustomerManager();
       
        new LoginPage().setVisible(true);
        //new FlightSearch().setVisible(true);
        //c.login()
        
        c.addCustomer(3, "OWEN", "CASSIDY", "e@yahoo.com", "e");
        
        /*
        c.addCustomer(1, "DAVE", "DOUGH", "MA-BOY");
        c.addCustomer(2, "JIM", "DOUGH", "ma-Boy2");
        */
        
        /*
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
        */
        
        // Test Flight Search
        
       /* Scanner in = new Scanner(System.in);
        System.out.print("Destination:\t");
        String departure = in.nextLine().toUpperCase();
        System.out.print("Arrival:\t");
        String arrival = in.nextLine().toUpperCase();
        System.out.print("Enter departure date(YYYY-MM-DD):");
        String dDate = in.nextLine();
        
        String splitDate[] = dDate.split("-");
        
        LocalDate depDate = LocalDate.of(Integer.parseInt(splitDate[0]), Integer.parseInt(splitDate[1]), Integer.parseInt(splitDate[2]));
                
                
        String[] criteria = {
            departure,
            arrival,
            dDate.toString()
        };
        ArrayList<Flight> flights = f.getFlightsOneWay(criteria);
        for(Flight flight : flights) {
            System.out.println(flight.toString());
*/
       // }
    }
    
    
}
