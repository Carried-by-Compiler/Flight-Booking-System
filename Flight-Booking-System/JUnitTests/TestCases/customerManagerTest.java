package TestCases;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import business_layer.CustomerManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Aoife
 */
public class customerManagerTest {
    
    public customerManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    public CustomerManager cm = new CustomerManager();
    @Test
    public void testValidInfoCustomerSearch(){
        int customerID = cm.searchCustomer("e@yahoo.com","e");
        assertEquals(3, customerID);
        
    }
    
    @Test
    public void testInvaidInfoCustomerSearch(){
      int customerID = cm.searchCustomer("e", "e");
      assertEquals(-1,customerID);
    }
    
    @Test 
    public void testValidIntCustomerSearch(){
      String info = cm.searchCustomer(3);
      String expectedOutCome = "Customer{id=3, fName=OWEN, lName=CASSIDY, email=e@yahoo.com}";
      assertEquals(expectedOutCome,info);

    } 
    @Test
    public void testInvalidIntCustomerSearch(){
       String info = cm.searchCustomer(1000);
       String expectedOutCome = "Customer does not exist";
       assertEquals(expectedOutCome, info);
    }
}
