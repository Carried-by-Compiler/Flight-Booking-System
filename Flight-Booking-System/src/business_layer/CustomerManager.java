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
    
    public void addCustomer(int id, String name) {
        // TODO: add information validation before passing to DAO
        dao.add(new Customer(id, name));
    }
    
    public String searchCustomer(int id) {
        Customer customer = dao.get(id);
        
        if(customer == null)
            return "Customer does not exist";
        else
            return customer.toString();
    }
}
