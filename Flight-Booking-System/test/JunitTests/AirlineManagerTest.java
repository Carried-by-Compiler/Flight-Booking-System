package JunitTests;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import business_layer.AirlineManager;
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
public class AirlineManagerTest {
    
    public AirlineManagerTest() {
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
    private AirlineManager ar = new AirlineManager();
    
    @Test
    public void testSearchAirlineByID(){
      
       assertEquals("AERLINGUS",ar.searchAirline(1));
       assertEquals("RYANAIR",ar.searchAirline(2));
       assertEquals("KML",ar.searchAirline(10));
    }
}
