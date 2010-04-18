/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Hl7V2TreePanel.java
 *
 * Created on 28-Mar-2010, 10:56:12 AM
 */

package ca.uhn.hunit.swing.ui.msg;

import ca.uhn.hl7v2.parser.PipeParser;
import ca.uhn.hl7v2.validation.impl.ValidationContextImpl;
import ca.uhn.hl7v2.view.TreePanel;
import java.awt.BorderLayout;
import java.awt.Component;

/**
 * Basically a quick and dirty extension of HAPI's TreePanel, allowing it
 * to be used as a bean in Netbeans' IDE UI editor.
 */
public class Hl7V2TreePanel extends TreePanel {

    /** Creates new form Hl7V2TreePanel */
    public Hl7V2TreePanel() {
        super(createParser());
        initComponents();
    }

    private static PipeParser createParser() {
        PipeParser retVal = new PipeParser();
        retVal.setValidationContext(new ValidationContextImpl());
        return retVal;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public Component add(Component comp) {
        super.add(comp, BorderLayout.CENTER);
        return comp;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}