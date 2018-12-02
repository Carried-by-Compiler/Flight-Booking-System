
package ui.controllers;
import business_layer.AirlineManager;
import business_layer.CustomerManager;
import business_layer.FlightsManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import ui.FlightGUI;
import ui.LoginPage;
import ui.Registration;

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
public class UserController {
    private CustomerManager customerManager;
    private AirlineManager airlineManager;
    private LoginPage loginPage;
    private Registration register;
    
    
    public UserController(CustomerManager customerManager, LoginPage loginPage, Registration register){
        this.customerManager = customerManager;
        this.loginPage = loginPage;
        this.register = register;
        
        this.loginPage.addSubmitListener(new SubmitListener());
        this.register.addSubmitListener(new SubmitListener());
    }
    
    
    public void start(){
        loginPage.display();
    }
    
    public void regPage(){
        register.display();
    }
    
    public void closelogin(){
        loginPage.closeLogin();
    }
    
    public void closeReg(){
        register.close();
    }
    
    public void checkLogin(){
            //CustomerManager cm = new CustomerManager();
            String email = loginPage.getEmail();
            String password = loginPage.getPassword();
            int response = customerManager.searchCustomer(email, password);
            if(response == -1){
              JOptionPane.showMessageDialog(null, "Incorrect Email or Password","Unable to login",JOptionPane.WARNING_MESSAGE);
            }
            else{
                FlightsManager fManager = new FlightsManager();
                FlightGUI flightGUI = new FlightGUI();
                FlightController fController = new FlightController(fManager, flightGUI,response);
                fController.startGUI();
                loginPage.closeLogin();
            }
    }
    
    public void checkRegister(){
        String name = register.getName();
        String surname = register.getSurname();
        String email = register.getEmail();
        String password = register.getPassword();
        String cpass = register.getConPassword();
        
        if(password.equals("") || cpass.equals("") || (!cpass.equalsIgnoreCase(password)))
        {
            JOptionPane.showMessageDialog(null, "Password words must match and cannot be empty","Unable to Register",JOptionPane.WARNING_MESSAGE);
        }
        else if(name.equals("") || surname.equals("") || email.equals("")){
            JOptionPane.showMessageDialog(null, "You cannot leave any field blank","Unable to Register",JOptionPane.WARNING_MESSAGE);
        }
        
        else{
            //Idealy we would use a database to to set an id automatically for the user but in this case we are just using a randome number simulator as proxy
            int random = (int)(Math.random() * 50 + 1);
            CustomerManager cm = new CustomerManager();
            cm.addCustomer(random ,name, surname, email, password);
            JOptionPane.showMessageDialog(null, "You have been Registered, please login","Register successful",JOptionPane.OK_OPTION);
            //start();
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
                case "Register":
                    checkRegister();
                    break;
                case "Register Account":
                    regPage();
                    closelogin();
                    break;
            }        
        }
        
    }
}