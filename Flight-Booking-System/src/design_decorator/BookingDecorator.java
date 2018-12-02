/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package design_decorator;

import business_layer.Booking;

/**
 *
 * @author Aoife
 */

public abstract class BookingDecorator implements Book {
      protected Book decoratedBooking;
      public BookingDecorator(Book decoratedBooking) {
            super();
            this.decoratedBooking = decoratedBooking;
      }
}