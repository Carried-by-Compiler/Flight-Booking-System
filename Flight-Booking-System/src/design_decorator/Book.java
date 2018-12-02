/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package design_decorator;

/**
 *
 * @author Aoife
 */
public interface Book {
    double getTotalCost();
    double getBaseCost();
    double calcTotalCost();
    String toString();
    public int getBookingId();
}
