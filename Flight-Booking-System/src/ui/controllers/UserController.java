
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
            this.customerManager.addCustomer(random ,name, surname, email, password);
            JOptionPane.showMessageDialog(null, "You have been Registered, please login","Register successful", JOptionPane.INFORMATION_MESSAGE);
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