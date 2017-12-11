/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Camilo
 */
public class JPColorChooser extends javax.swing.JPanel {
    private int color = -1;
    /**
     * Creates new form JPColorChooser
     */
    public JPColorChooser() {
        initComponents();
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public JLabel getjLText() {
        return jLText;
    }

    public void setjLText(JLabel jLText) {
        this.jLText = jLText;
    }

    public JPanel getjPBlue() {
        return jPBlue;
    }

    public void setjPBlue(JPanel jPBlue) {
        this.jPBlue = jPBlue;
    }

    public JPanel getjPGreen() {
        return jPGreen;
    }

    public void setjPGreen(JPanel jPGreen) {
        this.jPGreen = jPGreen;
    }

    public JPanel getjPRed() {
        return jPRed;
    }

    public void setjPRed(JPanel jPRed) {
        this.jPRed = jPRed;
    }

    public JPanel getjPYellow() {
        return jPYellow;
    }

    public void setjPYellow(JPanel jPYellow) {
        this.jPYellow = jPYellow;
    }
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLText = new javax.swing.JLabel();
        jPBlue = new javax.swing.JPanel();
        jPGreen = new javax.swing.JPanel();
        jPRed = new javax.swing.JPanel();
        jPYellow = new javax.swing.JPanel();

        setMaximumSize(new java.awt.Dimension(200, 90));
        setMinimumSize(new java.awt.Dimension(200, 90));
        setPreferredSize(new java.awt.Dimension(200, 90));
        setLayout(new java.awt.GridBagLayout());

        jLText.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLText.setText("test");
        jLText.setAlignmentX(0.5F);
        jLText.setPreferredSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        add(jLText, gridBagConstraints);

        jPBlue.setBackground(new java.awt.Color(0, 0, 255));
        jPBlue.setMinimumSize(new java.awt.Dimension(120, 30));
        jPBlue.setPreferredSize(new java.awt.Dimension(120, 30));

        javax.swing.GroupLayout jPBlueLayout = new javax.swing.GroupLayout(jPBlue);
        jPBlue.setLayout(jPBlueLayout);
        jPBlueLayout.setHorizontalGroup(
            jPBlueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 120, Short.MAX_VALUE)
        );
        jPBlueLayout.setVerticalGroup(
            jPBlueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE;
        add(jPBlue, gridBagConstraints);

        jPGreen.setBackground(new java.awt.Color(0, 255, 51));
        jPGreen.setMinimumSize(new java.awt.Dimension(120, 30));
        jPGreen.setPreferredSize(new java.awt.Dimension(120, 30));

        javax.swing.GroupLayout jPGreenLayout = new javax.swing.GroupLayout(jPGreen);
        jPGreen.setLayout(jPGreenLayout);
        jPGreenLayout.setHorizontalGroup(
            jPGreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 124, Short.MAX_VALUE)
        );
        jPGreenLayout.setVerticalGroup(
            jPGreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE;
        add(jPGreen, gridBagConstraints);

        jPRed.setBackground(new java.awt.Color(255, 51, 0));
        jPRed.setMinimumSize(new java.awt.Dimension(120, 30));
        jPRed.setPreferredSize(new java.awt.Dimension(120, 30));

        javax.swing.GroupLayout jPRedLayout = new javax.swing.GroupLayout(jPRed);
        jPRed.setLayout(jPRedLayout);
        jPRedLayout.setHorizontalGroup(
            jPRedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 120, Short.MAX_VALUE)
        );
        jPRedLayout.setVerticalGroup(
            jPRedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE;
        add(jPRed, gridBagConstraints);

        jPYellow.setBackground(new java.awt.Color(255, 255, 0));
        jPYellow.setMinimumSize(new java.awt.Dimension(120, 30));
        jPYellow.setPreferredSize(new java.awt.Dimension(120, 30));

        javax.swing.GroupLayout jPYellowLayout = new javax.swing.GroupLayout(jPYellow);
        jPYellow.setLayout(jPYellowLayout);
        jPYellowLayout.setHorizontalGroup(
            jPYellowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 124, Short.MAX_VALUE)
        );
        jPYellowLayout.setVerticalGroup(
            jPYellowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE;
        add(jPYellow, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLText;
    private javax.swing.JPanel jPBlue;
    private javax.swing.JPanel jPGreen;
    private javax.swing.JPanel jPRed;
    private javax.swing.JPanel jPYellow;
    // End of variables declaration//GEN-END:variables
}