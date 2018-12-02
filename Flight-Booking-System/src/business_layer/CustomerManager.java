/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business_layer;

import DB_Layer.Dao;
import DB_Layer.DaoFactory;

/**
 *
 * @author eoghan
 */
public class CustomerManager {
        private Dao<Customer> dao;
    
    public CustomerManager() {
        this.dao = DaoFactory.getDao(DaoFactory.CUSTOMER);
    }
    
    public void addCustomer(String name, String lName, String e, String password){
        dao.add(new Customer(name, lName, e, password));
    }
    
    public void addCustomer(int id, String name, String lName, String e, String password) {
        // TODO: add information validation before passing to DAO
        dao.add(new Customer(id, name, lName, e, password));
    }
    
    public String searchCustomer(int id) {
        Customer customer = dao.get(id);
        
        if(customer == null)
            return "Customer does not exist";
        else
            return customer.toString();
    }
    
    public int searchCustomer(String email, String password){
        Customer customer = dao.get(email, password);
        
        if(customer == null){
            return -1;
        }
        else
        {
            System.out.println(customer.getEmail());
            return customer.getId();
        }
    }
    
}
