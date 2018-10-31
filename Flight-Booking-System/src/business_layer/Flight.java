/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business_layer;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author John Rey Juele
 */
public class Flight implements Comparable<LocalDate> {
    
    private int id;
    private int airLineID;
    private String departure;
    private String arrival;
    private LocalDateTime depTime;
    private LocalDateTime arrTime;
    private double        cost;
    
    public Flight() {
        
    }
    
    public Flight(int id, int aid, String d, String a,
            LocalDateTime dTime, LocalDateTime aTime, double c) {
        
        this.id = id;
        this.airLineID = aid;
        this.departure = d;
        this.arrival = a;
        this.depTime = dTime;
        this.arrTime = aTime;
        this.cost = c;
    }
    
    public double getCost() {
        return this.cost;
    }
    
    public void setCost(double cost) {
        this.cost = cost;
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

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
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
        return "Flight{" + "id=" + id + ", airLineID=" + airLineID + ", departure=" + departure + 
                ", arrival=" + arrival + ", depTime=" + depTime + ", arrTime=" + arrTime + "cost=" + cost + '}';
    }
    
    public String[] getDetailsAsArray() {
        String[] details = new String[6];
        
        details[0] = String.valueOf(this.id);
        details[1] = String.valueOf(this.airLineID);
        details[2] = this.departure;
        details[3] = this.arrival;
        details[4] = this.depTime.toString();
        details[5] = this.arrTime.toString();
        details[6] = String.valueOf(this.cost);
        
        return details;
    }

    @Override
    public int compareTo(LocalDate o) {
        if(this.depTime.toLocalDate().isEqual(o)) {
            return 0;
        } else if(this.depTime.toLocalDate().isBefore(o)) {
            return 1;
        } else {
            return -1;
        }
    
    } 
}
