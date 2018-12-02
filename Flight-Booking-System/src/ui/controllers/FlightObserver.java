/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import java.util.ArrayList;

/**
 *
 * @author John Rey Juele
 */
public interface FlightObserver {
    
    // TODO: change signature so that flight objects are not used
    public void update(ArrayList<String> data, int type);
}
