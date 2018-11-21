/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business_layer;
import java.util.*;
/**
 *
 * @author eogha
 */
public class Booking {
    private int id;
     private String names;
     private String baggage;

    public Booking(int id, String names, String baggage){
        this.id = id;
        this.names = names;
        this.baggage = baggage;
    }
}


