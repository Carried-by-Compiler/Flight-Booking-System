/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business_layer;

import java.time.LocalDateTime;

/**
 *
 * @author John Rey Juele
 */
public class Flight {
    
    private int id;
    private int airLineID;
    private String departure;
    private String arrivale;
    private LocalDateTime depTime;
    private LocalDateTime arrTime;
    
    public Flight(int id, int aid, String d, String a,
            LocalDateTime dTime, LocalDateTime aTime) {
        
        this.id = id;
        this.airLineID = aid;
        this.departure = d;
        this.arrivale = a;
        this.depTime = dTime;
        this.arrTime = aTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public int getAirLineID() {
        return airLineID;
    }

    public void setAirLineID(int airLineID) {
        this.airLineID = airLineID;
    }

    public String getArrivale() {
        return arrivale;
    }

    public void setArrivale(String arrivale) {
        this.arrivale = arrivale;
    }

    public LocalDateTime getDepTime() {
        return depTime;
    }

    public void setDepTime(LocalDateTime depTime) {
        this.depTime = depTime;
    }

    public LocalDateTime getArrTime() {
        return arrTime;
    }

    public void setArrTime(LocalDateTime arrTime) {
        this.arrTime = arrTime;
    }

    @Override
    public String toString() {
        return "Flight{" + "id=" + id + ", departure=" + departure + ", arrivale=" + arrivale + ", depTime=" + depTime + ", arrTime=" + arrTime + '}';
    }
    
    
}
