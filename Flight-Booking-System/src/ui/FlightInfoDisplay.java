/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import business_layer.BookingManager;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Aoife
 */
public class FlightInfoDisplay extends javax.swing.JFrame {

    /**
     * Creates new form FlightInfoDisplay
     */
    private boolean hasReturn;
    
    private ArrayList<String> outboundFlight;
    private ArrayList<String> returnFlight;
    
    private DefaultTableModel tbDepartModel;
    private DefaultTableModel tbReturnModel;
    
    public FlightInfoDisplay() {
        
        initComponents();
        
        this.tbDepartModel = (DefaultTableModel) this.outboundDetails.getModel();
        this.tbReturnModel = (DefaultTableModel) this.returnDetails.getModel();
        
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                
                tbDepartModel.setRowCount(0);
                tbReturnModel.setRowCount(0);
            }
        });
    }
    
    public void display(ArrayList<String> o, ArrayList<String> r) {
        this.hasReturn = true;
        this.outboundFlight = o;
        this.returnFlight = r;
        displayFlightInfo();
        this.setVisible(true);
        
        System.out.println(o);
        System.out.println(r);
    }
    
    public void display(ArrayList<String> o) {
        this.hasReturn = false;
        this.outboundFlight = o;
        displayFlightInfo();
        this.setVisible(true);
    }
    
   private void displayFlightInfo(){
       
       Object[] datarow = new Object[5];
       
       if(hasReturn) { /*attempting to adjust for direct flights and  return flights here, its a mess rn*/
           
           /*
            System.out.println("Display");
            String [] depFlight = this.stored[0].split(",");
            String [] retFlight = this.stored[1].split(",");
            Double totalCost = 0.0;
            totalCost +=Double.parseDouble(depFlight[7]);
            totalCost +=Double.parseDouble(retFlight[7]);
            String cost = totalCost +"";
            String airlines = depFlight[0] + "," +  retFlight[0];
            this.airlineNames.setText(airlines);
            this.departAirport.setText(depFlight[5]);
            this.arrAirport.setText(depFlight[6]);
            this.arrTime.setText(depFlight[3]);
            this.retDepAirport.setText(retFlight[5]);
            this.retArrAirport.setText(retFlight[6]);
            this.retdepTime.setText(retFlight[3]);
            this.totalCost.setText(cost);}
           */
           
           // Handle Outbound flights
           String[] airlines = this.outboundFlight.get(0).split(",");
           String[] times = this.outboundFlight.get(2).split(",");
           String[] cities = this.outboundFlight.get(3).split(",");
           
           for(int i = 0; i < airlines.length; i++) {
               datarow[0] = airlines[i];
               datarow[1] = cities[i];
               datarow[2] = cities[i + 1];
               
               String[] t = times[i].split("/");
               datarow[3] = t[0];
               datarow[4] = t[1];
               
               this.tbDepartModel.addRow(datarow);
           }
           
           // Hanlde Return flights
           airlines = this.returnFlight.get(0).split(",");
           times = this.returnFlight.get(2).split(",");
           cities = this.returnFlight.get(3).split(",");
           
           for(int i = 0; i < airlines.length; i++) {
               datarow[0] = airlines[i];
               datarow[1] = cities[i];
               datarow[2] = cities[i + 1];
               
               String[] t = times[i].split("/");
               datarow[3] = t[0];
               datarow[4] = t[1];
               
               this.tbReturnModel.addRow(datarow);
           }
           
           
       } else {
           String[] airlines = this.outboundFlight.get(0).split(",");
           String[] times = this.outboundFlight.get(2).split(",");
           String[] cities = this.outboundFlight.get(3).split(",");
           
           for(int i = 0; i < airlines.length; i++) {
               datarow[0] = airlines[i];
               datarow[1] = cities[i];
               datarow[2] = cities[i + 1];
               
               String[] t = times[i].split("/");
               datarow[3] = t[0];
               datarow[4] = t[1];
               
               this.tbDepartModel.addRow(datarow);
           }
           /*
            String [] depFlight = this.stored[0].split(",");
            this.airlineNames.setText(depFlight[0]);
            this.departAirport.setText(depFlight[5]);
            this.arrAirport.setText(depFlight[6]);
            this.arrTime.setText(depFlight[3]);
            this.totalCost.setText(depFlight[7]);
           */
       }
   }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        outboundDetails = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        returnDetails = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Flight Information");

        outboundDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Airline", "City 1", "City 2", "Departure Time", "Arrival Time"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(outboundDetails);

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel8.setText("Outbound");

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel1.setText("Return");

        returnDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Airline", "City 1", "City 2", "Departure Time", "Arrival Time"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(returnDetails);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel8)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGap(50, 50, 50)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 604, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 604, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FlightInfoDisplay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FlightInfoDisplay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FlightInfoDisplay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FlightInfoDisplay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new FlightInfoDisplay().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable outboundDetails;
    private javax.swing.JTable returnDetails;
    // End of variables declaration//GEN-END:variables
}
