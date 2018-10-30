/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import DB_Layer.Dao;
import business_layer.Airline;

/**
 *
 * @author John Rey Juele
 */
public class Main {
    public static void main(String... args) {
        Dao<Airline> dao = new AirlineDAO();
        dao.add(new Airline(1, "RYANAIR", "RYAN"));
    }
}
