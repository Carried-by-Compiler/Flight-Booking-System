/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import business_layer.BookingManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import ui.BookingGUI;

/**
 *
 * @author Aoife
 */
public class BookingController {
    private BookingManager bookManager;
    private BookingGUI bookGUI;
    private String [] flightInfo;
    private int loggedInUser;
   public BookingController(BookingManager bmanager, BookingGUI bookGUI, String [] fInfo, int user) {
        this.bookManager = bmanager;
        this.bookGUI = bookGUI;
        this.flightInfo = fInfo;
        this.loggedInUser = user;
        this.bookGUI.addButtonListener(new ButtonListener());
    } 

    void startGUI() {
        this.bookGUI.display();
    }
    public void getPolicies(){
     String seatType = this.bookGUI.getDropDown();
     boolean insurance = this.bookGUI.getInsuranceClicked();
     boolean baggage  = this.bookGUI.getBaggaeChecked();
     bookManager.addBooking(loggedInUser,flightInfo,seatType,baggage,insurance);
      
    }
    public void finishBooking(){
       this.bookGUI.setVisible(false);   
    }
 private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String source = e.getActionCommand();
           
            switch(source) {
                case "Buy":
                    getPolicies();
                    finishBooking();
                    break;
                    
                
            }        
        }
        
    }
}
