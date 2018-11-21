/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import business_layer.FlightsManager;
import business_layer.NoFlightsFoundException;
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
    
    /**
     * Initialize the controller class connecting the flights gui and its appropriate manager.
     * @param manager The manager handling the GUI.
     * @param gui The Graphical User Interface class that will display flight details.
     */
    public FlightController(FlightsManager manager, FlightGUI gui) {
        this.flightManager = manager;
        this.flightGUI = gui;
        
        this.flightGUI.addButtonListener(new ButtonListener());
        this.flightManager.register(this.flightGUI);
        
        // Set the years in the combo box in the gui
        LocalDate now = LocalDate.now();
        LocalDate nextYear = now.plusYears(1);
        LocalDate in2Years = now.plusYears(2);
        
        int[] years = {now.getYear(), nextYear.getYear(), in2Years.getYear()};
        this.flightGUI.setYearComboBox(years);
    }
    
    private void getInput() {
        
        boolean correct = true;
        LocalDate date = null;
        LocalDate depDate = null, retDate = null;
        
        // TODO: validate input
        String departure = this.flightGUI.getDeparture().replaceAll("\\s+", "").toUpperCase();
        String arrival = this.flightGUI.getArrival().replaceAll("\\s+", "").toUpperCase();
        String[] dDate = this.flightGUI.getDepartDate();
        String[] rDate = this.flightGUI.getReturnDate();
        String flightMethod = this.flightGUI.getFlightMethodChoice();
        String oneWayOrReturn = this.flightGUI.getFlightGetReturnMethod();
        
        if(departure.isEmpty()) {
            this.flightGUI.error("DEPARTURE");
            correct = false;
        }
        if(arrival.isEmpty()) {
            this.flightGUI.error("ARRIVAL");
            correct = false;
        }
        
        try {            
            depDate = convertStringToDate(dDate, 0);
            if(!rDate[0].isEmpty())
                retDate = convertStringToDate(rDate, 1);
        } catch (NumberFormatException e) {
            correct = false;
        }
        
        if(correct) {
            if(flightMethod.equals("MULTIPLE")) {
                try {
                    if(oneWayOrReturn.equals("ONEWAY")) {
                        this.flightManager.getFlightsMultipleStop(departure, arrival, depDate);
                    } else if(oneWayOrReturn.equals("RETURN")) {
                        this.flightManager.getFlightsMultipleStop(departure, arrival, depDate, retDate);
                    }
                } catch(NoFlightsFoundException e) {
                    this.flightGUI.error(e.getErrorID());
                }
            }
        }
        
    }
    
    private LocalDate convertStringToDate(String[] date, int dateType) {
        int day = Integer.parseInt(date[0]);
        int month = getMonthInt(date[1]);
        if(month < 1) {
            if(dateType == 0)
                this.flightGUI.error("DATE");
            else 
                this.flightGUI.error("RDATE");
            
            throw new NumberFormatException();
        }
        int year = Integer.parseInt(date[2]);
        
        LocalDate ld = LocalDate.of(year, month, day);
        return ld;
    }
    
    private int getMonthInt(String month) {
        int monthInt;
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
            default:
                monthInt = -1;
        }
        return monthInt;
    }
    
    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String source = e.getActionCommand();
            
            switch(source) {
                case "Search Flights":
                    flightGUI.clearTable();
                    getInput();
                    break;
                    
                case "One way":
                    flightGUI.setRBEnable(false);
                    break;
                    
                case "Return":
                    flightGUI.setRBEnable(true);
                    break;
            }        
        }
        
    }
}