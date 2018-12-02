/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB_Layer;

/**
 *
 * @author John Rey Juele
 */
public class DaoFactory {
    
    public static final int AIRLINE = 1;
    public static final int FLIGHT  = 2;
    public static final int CUSTOMER = 3;
    public static final int BOOK = 4;
    public static Dao getDao(int type) {
        Dao d = null;
        
        switch(type) {
            case AIRLINE:
                d = new AirlineDAO();
                break;
                
            case FLIGHT:
                d = new FlightsDAO();
                break;
                
            case CUSTOMER:
                d = new CustomerDAO();
                break;
                
            case BOOK: 
                d = new BookingDAO();
                break;
        }
       
        return d;
    }
}
