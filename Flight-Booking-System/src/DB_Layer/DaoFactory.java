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
    
    public static Dao getDao(int type) {
        Dao d = null;
        
        switch(type) {
            case AIRLINE:
                d = new AirlineDAO();
                break;
        }
       
        return d;
    }
}
