package JunitTests;

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
        assertEquals(1,cm.searchCustomer("DAVE@GMAIL.COM", "MA-BOY"));
        assertEquals(3, cm.searchCustomer("e@yahoo.com","e"));
        
    }
    
    @Test
    public void testInvaidInfoCustomerSearch(){
      assertEquals(-1,cm.searchCustomer("e@yahoo.com", "e@yahoo.com"));
      assertEquals(-1,cm.searchCustomer("e", "e"));
      assertEquals(-1,cm.searchCustomer("",""));
    }
    
    @Test 
    public void testValidIntCustomerSearch(){
      assertEquals("Customer{id=1, fName=DAVE, lName=DOUGH, email=DAVE@GMAIL.COM}",cm.searchCustomer(1));
      assertEquals("Customer{id=3, fName=OWEN, lName=CASSIDY, email=e@yahoo.com}",cm.searchCustomer(3));

    } 
    @Test
    public void testInvalidIntCustomerSearch(){      
       assertEquals("Customer does not exist", cm.searchCustomer(1000));
    }
}
