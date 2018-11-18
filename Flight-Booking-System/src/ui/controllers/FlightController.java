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
    }
    
    private void getInput() {
        
        // TODO: validate input
        String departure = this.flightGUI.getDeparture().toUpperCase();
        String arrival = this.flightGUI.getArrival().toUpperCase();
        String dDate = this.flightGUI.getDepartDate();
        String flightMethod = this.flightGUI.getFlightMethodChoice();
        
        String[] parsedDate = dDate.split("/");
        try {
            int day = Integer.parseInt(parsedDate[0]);
            int month = Integer.parseInt(parsedDate[1]);
            int year = Integer.parseInt(parsedDate[2]);
            
            LocalDate date = LocalDate.of(year, month, day);
            
            if(flightMethod == "MULTIPLE") {
                this.flightManager.getFlightsMultipleStop(departure, arrival, date);
            }
        } catch (Exception e) {
            this.flightGUI.error();
        }
        
        //this.flightManager.submitInput(departure, arrival, );
    }
    
    private class SubmitListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String source = e.getActionCommand();
            
            switch(source) {
                case "Search Flight":
                    getInput();
                    break;
            }        
        }
        
    }
}