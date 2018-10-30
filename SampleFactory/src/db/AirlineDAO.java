package db;


import business_layer.Airline;
import DB_Layer.Dao;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author John Rey Juele
 * 
 */
public class AirlineDAO implements Dao<Airline> {
    
    private BufferedReader br;
    private BufferedWriter bw;
    
    private String filename;
    
    public AirlineDAO() {
        filename = "./DatabaseFiles/Airlines.csv";
    }

    @Override
    public Airline getDetails(int id) {
        String[] lineSplit = new
        try {
            br = new BufferedReader(new FileReader(filename));
            
        } catch(IOException e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public void add(Airline n) {
        String line = n.getID() + "," + n.getName() + "," + n.getPassword();
        try {
            bw = new BufferedWriter(new FileWriter(filename, true));
            bw.append(line);
            bw.close();
            
        } catch(IOException e) {
            System.out.println(e.toString());
        }
    }
    
}
