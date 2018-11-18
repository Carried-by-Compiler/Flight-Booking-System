/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import business_layer.FlightsManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import ui.FlightGUI;

/**
 *
 * @author John Rey Juele
 */
public class FlightController {
    
    private FlightsManager flightManager;
    private FlightGUI flightGUI;
    
    public FlightController(FlightsManager manager, FlightGUI gui) {
        this.flightManager = manager;
        this.flightGUI = gui;
        
        this.flightGUI.addSubmitListener(new SubmitListener());
        this.flightManager.register(this.flightGUI);
        
        // Set the years in the combo box in the gui
        LocalDate now = LocalDate.now();
        LocalDate nextYear = now.plusYears(1);
        LocalDate in2Years = now.plusYears(2);
        
        int[] years = {now.getYear(), nextYear.getYear(), in2Years.getYear()};
        this.flightGUI.setYearComboBox(years);
    }
    
    private void getInput() {
        
        // TODO: validate input
        String departure = this.flightGUI.getDeparture().replaceAll("\\s+", "").toUpperCase();
        String arrival = this.flightGUI.getArrival().replaceAll("\\s+", "").toUpperCase();
        String[] dDate = this.flightGUI.getDepartDate();
        String flightMethod = this.flightGUI.getFlightMethodChoice();
        
        System.out.println("Departure:\t" + departure);
        System.out.println("Arrival:\t" + arrival);
        
        try {
            int day = Integer.parseInt(dDate[0]);
            int month = getMonthInt(dDate[1]);
            int year = Integer.parseInt(dDate[2]);
            
            LocalDate date = LocalDate.of(year, month, day);
                
            if(flightMethod == "MULTIPLE") {
                this.flightManager.getFlightsMultipleStop(departure, arrival, date);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            this.flightGUI.error("PARSING ERROR");
        }
        
        //this.flightManager.submitInput(departure, arrival, );
    }
    
    private int getMonthInt(String month) {
        int monthInt = 0;
        switch(month) {
            case "Jan.":
                monthInt = 1;
                break;
            case "Feb.":
                monthInt = 2;
                break;
            case "Mar.":
                monthInt = 3;
                break;
            case "Apr.":
                monthInt = 4;
                break;
            case "May":
                monthInt = 5;
                break;
            case "Jun.":
                monthInt = 6;
                break;
            case "Jul.":
                monthInt = 7;
                break;
            case "Aug.":
                monthInt = 8;
                break;
            case "Sep.":
                monthInt = 9;
                break;
            case "Oct.":
                monthInt = 10;
                break;                           
            case "Nov.":
                monthInt = 11;
                break;
            case "Dec.":
                monthInt = 12;
                break;
        }
        return monthInt;
    }
    
    private class SubmitListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String source = e.getActionCommand();
            
            switch(source) {
                case "Search Flight":
                    flightGUI.clearTable();
                    getInput();
                    break;
            }        
        }
        
    }
}