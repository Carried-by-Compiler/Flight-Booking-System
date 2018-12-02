/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import business_layer.BookingManager;
import business_layer.FlightsManager;
import business_layer.NoFlightsFoundException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import ui.BookingGUI;
import ui.FlightGUI;
import ui.FlightInfoDisplay;
import ui.FlightObserver;

/**
 *
 * @author John Rey Juele
 */
public class FlightController implements FlightObserver {
    
    private FlightsManager flightManager;
    private FlightGUI flightGUI;
    private FlightInfoDisplay flightInfo;
    private int loggedInUser;
    // Lists that will store the details of each flights found in the search.
    private List<ArrayList<String>> departPathDetails;
    private List<ArrayList<String>> returnPathDetails;
    
    
    /**
     * Initialize the controller class connecting the flights gui and its appropriate manager.
     * @param manager The manager handling the GUI.
     * @param gui The Graphical User Interface class that will display flight details.
     */
    public FlightController(FlightsManager manager, FlightGUI gui, int user) {
        this.flightManager = manager;
        this.flightGUI = gui;
        this.loggedInUser = user;
        this.flightInfo = new FlightInfoDisplay();
        
        this.flightGUI.addButtonListener(new ButtonListener());
        this.flightManager.register(this);
       
        this.departPathDetails = new ArrayList<ArrayList<String>>();
        this.returnPathDetails = new ArrayList<ArrayList<String>>();
        
        // Set the years in the combo box in the gui
        LocalDate now = LocalDate.now();
        LocalDate nextYear = now.plusYears(1);
        LocalDate in2Years = now.plusYears(2);
        
        int[] years = {now.getYear(), nextYear.getYear(), in2Years.getYear()};
        this.flightGUI.setYearComboBox(years);
    }
    
    public void startGUI() {
        this.flightGUI.display();
    }
    
    public void startFlightInfoGUI(){
        System.out.println("INFO DISPLAY");
        int [] selectRow =getRows();
        String [] info = getFlightinfo(selectRow);
        this.flightInfo.display(info);
    }
    
    public void startBookingInfoGUI(String [] flightInfo){
                BookingManager bmanager = new BookingManager();
                BookingGUI bookGUI = new BookingGUI();
                BookingController bcontroller = new BookingController(bmanager, bookGUI,flightInfo,loggedInUser);
                bcontroller.startGUI();
    }
    public void checkRows(){
        System.out.println("Checking Rows");
       int [] positions = this.flightGUI.getSelectedRows();
       String [] flightInfo = getFlightinfo(positions);
       startBookingInfoGUI(flightInfo);
    
    }
    public int [] getRows(){
          int [] positions = this.flightGUI.getSelectedRows();
          return positions;
    }
    public String [] getFlightinfo(int [] positions){
        System.out.println("Flight Info");
        String[] info = new String[2];
        System.out.println("depature");
            String dep = "";
            for(int a =0; a < positions[1]; a++){
               dep += this.departPathDetails.get(positions[0]).get(a)+ ",";
               
            }
          System.out.println(dep);
          info [0]= dep;
          System.out.println("arrival");
          String ar = "";
          for(int a =0; a < positions[3]; a++){
             ar+= this.returnPathDetails.get(positions [2]).get(a) + ",";       
          
            }
          System.out.println(ar);
          info [1]= ar;
        return info;
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
            } else if(flightMethod.equals("DIRECT"))  {
                try {
                    if(oneWayOrReturn.equals("ONEWAY")) {
                        this.flightManager.getDirectFlights(departure, arrival, depDate);
                    } else if(oneWayOrReturn.equals("RETURN")) {
                       this.flightManager.getDirectFlights(departure, arrival, depDate, retDate);
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
    
    /**
     * Updates the GUI with the passed in data.
     * 
     * data[0] = Airline names
     * data[1] = Number of stops
     * data[2] = Scheduled times of each flight
     * data[3] = The cities within the flight path.
     * data[4] = The total cost of the flight path.
     * 
     * @param data A list containing the data to display
     */
    @Override
    public void update(ArrayList<String> data, int type) {
        String airlines = "", depDate = "", arrDate = "";
        Object[] dataRow = new Object[5];
        String[] parsedInfo;
        
        parsedInfo = data.get(0).split(",");
        
        airlines += parseAirlines(parsedInfo);
        
        parsedInfo = data.get(2).split(",");
        depDate = getStartingTime(parsedInfo);
        arrDate = getEndingTime(parsedInfo);
        
        dataRow[0] = airlines; //flight.getAirLineID();
        dataRow[1] = depDate; //flight.getDepTime().format(formatter);
        dataRow[2] = arrDate;//flight.getArrTime().format(formatter);
        dataRow[3] = data.get(1);//1;
        dataRow[4] = "€" + data.get(4); //flight.getCost();
        
        switch(type) {
            case FlightsManager.DEPART:
                this.departPathDetails.add(data);
                this.flightGUI.fillDepartTable(dataRow);
                break;
            
            case FlightsManager.RETURN:
                this.returnPathDetails.add(data);
                this.flightGUI.fillReturnTable(dataRow);
                break;
        }
    }
    
    private String parseAirlines(String[] airlines) {
        String output = "";
        for(int i = 0; i < airlines.length;  i++) {
            output += airlines[i] + ", ";
        }
        
        return output;
    }
    
    private String getStartingTime(String[] schedule) {
        String firstFlight = schedule[0];
        String[] parsedTime = firstFlight.split("/");
        
        return parsedTime[0];
    }
    
    private String getEndingTime(String[] schedule) {
        String lastFlight = schedule[schedule.length - 1];
        String[] parsedTime = lastFlight.split("/");
        
        return parsedTime[1];
    }
    
    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String source = e.getActionCommand();
           
            switch(source) {
                case "Search Flights":
                    
                    departPathDetails.clear();
                    returnPathDetails.clear();
                    
                    flightGUI.clearTable();
                    getInput();
                    break;
                    
                case "One way":
                    flightGUI.setRBEnable(false);
                    break;
                    
                case "Return":
                    flightGUI.setRBEnable(true);
                    break;
                    
                case "Purchase":
                   checkRows();
                   // System.out.println("Submit");
                    break;
                case "More Info":
                    startFlightInfoGUI();
                    break;
                
            }        
        }
        
    }
}