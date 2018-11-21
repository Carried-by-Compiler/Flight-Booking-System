
package ui.controllers;
import business_layer.AirlineManager;
import business_layer.CustomerManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import ui.FlightGUI;
import ui.FlightSearch;
import ui.LoginPage;

/**
 *
 * @author Sophia
 */

/*
    Initialise login and customer manager and register
    in main but takes in those;
    "error handling"
    checks for if login is correct and register is correct (exisit or not)
    take it out of customer;
    Once the gui gets the info? action listener placed here - wil parser it? and then it will query the manager 
    and the manager will query the database;
    yeah ok
    Has reference to airline manager class? 
    Tab to switch to airline login GUI?
    customer, airline and flight managers in here?
    if airline button is pressed... airline Controller
    Registration is not an option for the airline - We'll assume all our airlines are registered. 
    Managers don't assume there's a GUI - takes in values of the manager 
*/
public class CustomerController {
    private CustomerManager customerManager;
    private AirlineManager airlineManager;
    private LoginPage loginPage;
    
    
    public CustomerController(CustomerManager customerManager, LoginPage loginPage){
        this.customerManager = customerManager;
        this.loginPage = loginPage;
        
        this.loginPage.addSubmitListener(new SubmitListener());
    }
    
    public void start(){
        loginPage.display();
    }
    
    public void checkLogin(){
            //CustomerManager cm = new CustomerManager();
            String email = loginPage.getEmail();
            String password = loginPage.getPassword();
            if(customerManager.searchCustomer(email, password).equals("")){
                System.out.print("Customer does not exist");
            }
            else{
                new FlightGUI().display();
            }
    }
    
    private class SubmitListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String source = e.getActionCommand();

            switch(source) {
                case "Login":
                    checkLogin();
                    break;
            }        
        }
        
    }
}
