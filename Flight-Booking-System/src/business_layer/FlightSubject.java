/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business_layer;

import java.util.ArrayList;
import ui.FlightObserver;

/**
 *
 * @author John Rey Juele
 */
public interface FlightSubject {
    public void register(FlightObserver o);
    public void unregister(FlightObserver o);
    public void notifyObservers(ArrayList<String> data, int type);
}
