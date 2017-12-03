/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;
import model.SerialComm;
import model.SerialReader;
import model.Trama;
import model.Utils;

/**
 *
 * @author Camilo
 */
public class TestTrama extends javax.swing.JFrame {

    /**
     * Creates new form TestTrama
     */
    public TestTrama() {
        initComponents();
    }

    public JTextField getjTFCard() {
        return jTFCard;
    }

    public void setjTFCard(JTextField jTFCard) {
        this.jTFCard = jTFCard;
    }

    public JTextField getjTFColor() {
        return jTFColor;
    }

    public void setjTFColor(JTextField jTFColor) {
        this.jTFColor = jTFColor;
    }

    public JTextField getjTFControl() {
        return jTFControl;
    }

    public void setjTFControl(JTextField jTFControl) {
        this.jTFControl = jTFControl;
    }

    public JTextField getjTFDestino() {
        return jTFDestino;
    }

    public void setjTFDestino(JTextField jTFDestino) {
        this.jTFDestino = jTFDestino;
    }

    public JTextField getjTFOrigen() {
        return jTFOrigen;
    }

    public void setjTFOrigen(JTextField jTFOrigen) {
        this.jTFOrigen = jTFOrigen;
    }

    public JTextField getjTFSentido() {
        return jTFSentido;
    }

    public void setjTFSentido(JTextField jTFSentido) {
        this.jTFSentido = jTFSentido;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPPrincipal = new javax.swing.JPanel();
        jPInfo = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jTFOrigen = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jTFDestino = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jTFControl = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jSPSeparador = new javax.swing.JSeparator();
        jPanel6 = new javax.swing.JPanel();
        jTFSentido = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jTFColor = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jTFCard = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jBSend = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTAConsola = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(200, 53));
        setPreferredSize(new java.awt.Dimension(400, 200));

        jPPrincipal.setBackground(new java.awt.Color(0, 102, 102));
        jPPrincipal.setPreferredSize(new java.awt.Dimension(400, 156));
        jPPrincipal.setLayout(new java.awt.BorderLayout());

        jPInfo.setMaximumSize(new java.awt.Dimension(412, 60));
        jPInfo.setPreferredSize(new java.awt.Dimension(412, 80));
        jPInfo.setLayout(new java.awt.BorderLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(350, 50));

        jPanel3.setLayout(new java.awt.GridLayout(2, 0));

        jTFOrigen.setPreferredSize(new java.awt.Dimension(30, 20));
        jPanel3.add(jTFOrigen);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Origen");
        jPanel3.add(jLabel1);

        jPanel1.add(jPanel3);

        jPanel4.setLayout(new java.awt.GridLayout(2, 0));

        jTFDestino.setPreferredSize(new java.awt.Dimension(30, 20));
        jPanel4.add(jTFDestino);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Destino");
        jPanel4.add(jLabel2);

        jPanel1.add(jPanel4);

        jPanel5.setLayout(new java.awt.GridLayout(2, 0));

        jTFControl.setPreferredSize(new java.awt.Dimension(45, 20));
        jPanel5.add(jTFControl);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Control");
        jPanel5.add(jLabel3);

        jPanel1.add(jPanel5);

        jSPSeparador.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jSPSeparador.setPreferredSize(new java.awt.Dimension(5, 0));
        jPanel1.add(jSPSeparador);

        jPanel6.setLayout(new java.awt.GridLayout(2, 0));

        jTFSentido.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanel6.add(jTFSentido);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Sentido");
        jPanel6.add(jLabel4);

        jPanel1.add(jPanel6);

        jPanel7.setLayout(new java.awt.GridLayout(2, 0));

        jTFColor.setPreferredSize(new java.awt.Dimension(30, 20));
        jPanel7.add(jTFColor);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Color");
        jPanel7.add(jLabel5);

        jPanel1.add(jPanel7);

        jPanel8.setLayout(new java.awt.GridLayout(2, 0));

        jTFCard.setPreferredSize(new java.awt.Dimension(50, 20));
        jPanel8.add(jTFCard);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Carta");
        jPanel8.add(jLabel6);

        jPanel1.add(jPanel8);

        jPInfo.add(jPanel1, java.awt.BorderLayout.NORTH);

        jBSend.setText("Send");
        jBSend.setMaximumSize(new java.awt.Dimension(50, 20));
        jBSend.setMinimumSize(new java.awt.Dimension(50, 20));
        jBSend.setPreferredSize(new java.awt.Dimension(80, 20));
        jBSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSendActionPerformed(evt);
            }
        });
        jPanel2.add(jBSend);

        jPInfo.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPPrincipal.add(jPInfo, java.awt.BorderLayout.SOUTH);

        jScrollPane1.setPreferredSize(new java.awt.Dimension(155, 96));

        jTAConsola.setColumns(20);
        jTAConsola.setRows(5);
        jTAConsola.setPreferredSize(new java.awt.Dimension(150, 94));
        jScrollPane1.setViewportView(jTAConsola);

        jPPrincipal.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPPrincipal, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSendActionPerformed
        String tControl = this.getjTFOrigen().getText()+this.getjTFDestino().getText()+
                this.getjTFControl().getText();
        String tInfo = this.getjTFSentido().getText()+this.getjTFColor().getText()+
                this.getjTFCard().getText();
        
        System.out.println(tControl+"."+tInfo+".");
        try
        {
            if(tInfo.length()==8&&tControl.length()==8){
                int control = Utils.binaryToInt(tControl);
                int info = Utils.binaryToInt(tInfo);
                Trama trama = new Trama(control, info);
                SerialComm.sendTrama(trama);
            }
        
        }
        catch ( Exception e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }//GEN-LAST:event_jBSendActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]){
        /* Set the Nimbus look and feel */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("joxy.JoxyLookAndFeel".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
                if ("GTK+".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(TestTrama.class.getName()).log(Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TestTrama().setVisible(true);
            }
        });
        

        
        //Iniciando SerialComm
        try {
            SerialComm.connect("COM2");
        } catch (Exception ex) {
            Logger.getLogger(SerialComm.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Iniciando Hilo de Entrada por Serial
        SerialReader reader = new SerialReader(SerialComm.getIn());
        new Thread(reader).start();
        
        System.out.println("Serial Comm Iniciado.");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBSend;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPInfo;
    private javax.swing.JPanel jPPrincipal;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JSeparator jSPSeparador;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTAConsola;
    private javax.swing.JTextField jTFCard;
    private javax.swing.JTextField jTFColor;
    private javax.swing.JTextField jTFControl;
    private javax.swing.JTextField jTFDestino;
    private javax.swing.JTextField jTFOrigen;
    private javax.swing.JTextField jTFSentido;
    // End of variables declaration//GEN-END:variables
}
