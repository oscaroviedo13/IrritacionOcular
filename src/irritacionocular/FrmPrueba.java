/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package irritacionocular;

import AcopleAccess.ConexionBD;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author osarmiento
 */
public class FrmPrueba extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    
    private String[][] a_sDatosObjetoEvaluacion;
    private String sIdObjetoEvaluacionSeleccionado = "";
    
    private String[][] a_sDatosGrupoEvaluacion;
    private String sIdGrupoEvaluacionSeleccionado = "";
    
    private String[][] a_sDatosDetalleEvaluacion;
    private String sIdGrupoDetalleSeleccionado = "";
    
    ConexionBD obj = null;
    
    public FrmPrueba() {
        initComponents();
        obj = new ConexionBD();
        cargarObjetoEvaluacion();
    }

    
    private void cargarObjetoEvaluacion(){
        a_sDatosObjetoEvaluacion = obj.consultaGenerica("Select * from IrritacionOcular.ObjetoEvaluacion");
        if(a_sDatosObjetoEvaluacion != null){
            for(int i=0; i< a_sDatosObjetoEvaluacion.length; i++){
                 cmbObjetoEvaluacion.addItem(a_sDatosObjetoEvaluacion[i][1]);
            }
        }
    }
    
    private void ubicarIdObjetoEvaluacion(String sDescripcionObjetoSel){
        for (int i = 0; i < a_sDatosObjetoEvaluacion.length; i++) {
            if(a_sDatosObjetoEvaluacion[i][1].equals(sDescripcionObjetoSel)){
                sIdObjetoEvaluacionSeleccionado = a_sDatosObjetoEvaluacion[i][0];
            }
        }
    }
    
    private void ubicarIdGrupoEvaluacion(String sDescripcionGrupoSel){
        
        if(cmbGrupoEvaluacion != null){
            for (int i = 0; i < a_sDatosGrupoEvaluacion.length; i++) {
                if(a_sDatosGrupoEvaluacion[i][1].equals(sDescripcionGrupoSel)){
                    sIdGrupoEvaluacionSeleccionado = a_sDatosGrupoEvaluacion[i][0];
                }
            }
        }
    }
    
    private void cargarGrupoEvaluacion(){
        a_sDatosGrupoEvaluacion = obj.consultaGenerica("Select * FROM IrritacionOcular.GrupoEvaluacion WHERE ID_ObjetoEvaluacion = " + sIdObjetoEvaluacionSeleccionado);
        cmbGrupoEvaluacion.removeAllItems();
        cmbGrupoEvaluacion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "[Seleccione Grupo Evaluacion]" }));
        
        if(a_sDatosGrupoEvaluacion != null){
            for(int i=0; i< a_sDatosGrupoEvaluacion.length; i++){
                 cmbGrupoEvaluacion.addItem(a_sDatosGrupoEvaluacion[i][2]);
            }
        }
    }
    
    private void cargarDetalleEvaluacion(){
        //a_sDatosDetalleEvaluacion = obj.consultaGenerica("Select * FROM IrritacionOcular.DetalleEvaluacion,  WHERE DescripcionGrupoEvaluacion = " + sIdGrupoEvaluacionSeleccionado);
        a_sDatosDetalleEvaluacion = obj.consultaGenerica("Select * FROM IrritacionOcular.DetalleEvaluacion, IrritacionOcular.GrupoEvaluacion "
                + "WHERE "
                + "IrritacionOcular.DetalleEvaluacion.ID_GrupoEvaluacion = IrritacionOcular.GrupoEvaluacion.ID_GrupoEvaluacion "
                + "AND "
                + "IrritacionOcular.GrupoEvaluacion.DescripcionGrupoEvaluacion = '" + cmbGrupoEvaluacion.getSelectedItem().toString() + "'");
        
        cmbDetalleEvaluacion.removeAllItems();
        cmbDetalleEvaluacion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "[Seleccione Detalle Evaluacion]" }));
        
        if(a_sDatosDetalleEvaluacion != null){
            for (int i = 0; i < a_sDatosDetalleEvaluacion.length; i++) {
                try {
                    String sComentario = new String(a_sDatosDetalleEvaluacion[i][1].getBytes("ISO-8859-1"), "UTF-8");
                    cmbDetalleEvaluacion.addItem(sComentario);
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(FrmPrueba.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
            }
        }
    
    }
    
    private void corregirFirmaObservador(String oldObser, String newObser){
        String[][] aa_sData = obj.consultaGenerica("SELECT "
                + "SensibilizacionDermica.JornadaEvaluacionSensibiDermi.ID_JorSensibilizacionDermica,  "
                + "SensibilizacionDermica.JornadaEvaluacionSensibiDermi.FirmaObservador "
                + "FROM  SensibilizacionDermica.JornadaEvaluacionSensibiDermi");
        
        for (String[] strings : aa_sData) {
            if(strings[1].equals(oldObser)){
                String sSQL = "UPDATE SensibilizacionDermica.JornadaEvaluacionSensibiDermi "
                        + "SET "
                        + "SensibilizacionDermica.JornadaEvaluacionSensibiDermi.FirmaObservador='" + newObser + "' "
                        + "WHERE "
                        + "SensibilizacionDermica.JornadaEvaluacionSensibiDermi.ID_JorSensibilizacionDermica='" + strings[0] + "'";
                obj.updateGenerico(sSQL);
            }
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        cmbGrupoEvaluacion = new javax.swing.JComboBox();
        cmbObjetoEvaluacion = new javax.swing.JComboBox();
        txtNew = new javax.swing.JTextField();
        txtOld = new javax.swing.JTextField();
        cmbDetalleEvaluacion = new javax.swing.JComboBox();
        btnCargarDetalle = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        cmbGrupoEvaluacion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "[Seleccione Grupo Evaluacion]" }));
        cmbGrupoEvaluacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmbGrupoEvaluacionMouseClicked(evt);
            }
        });
        cmbGrupoEvaluacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbGrupoEvaluacionActionPerformed(evt);
            }
        });

        cmbObjetoEvaluacion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "[Seleccione Objeto Evaluacion]" }));
        cmbObjetoEvaluacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbObjetoEvaluacionActionPerformed(evt);
            }
        });

        cmbDetalleEvaluacion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "[Seleccione Detalle Evaluacion]" }));

        btnCargarDetalle.setText("Cargar");
        btnCargarDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarDetalleActionPerformed(evt);
            }
        });

        jButton2.setText("jButton2");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setText("Corregir FirmaObservador");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(txtOld, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNew, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cmbObjetoEvaluacion, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cmbGrupoEvaluacion, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cmbDetalleEvaluacion, javax.swing.GroupLayout.PREFERRED_SIZE, 498, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCargarDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(215, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbObjetoEvaluacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbGrupoEvaluacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnCargarDetalle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmbDetalleEvaluacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtOld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(39, 39, 39)
                .addComponent(jButton2)
                .addContainerGap(205, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab1", jPanel1);

        jButton3.setText("jButton3");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addContainerGap(647, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jButton3)
                .addContainerGap(420, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab2", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 745, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 503, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbObjetoEvaluacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbObjetoEvaluacionActionPerformed
        if(cmbObjetoEvaluacion.getSelectedIndex() == 0){
            sIdObjetoEvaluacionSeleccionado = "";
            cmbGrupoEvaluacion.removeAllItems();
            cmbGrupoEvaluacion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "[Seleccione Grupo Evaluacion]" }));
        
        }else{
            ubicarIdObjetoEvaluacion(cmbObjetoEvaluacion.getSelectedItem().toString());
            cargarGrupoEvaluacion();
        }
    }//GEN-LAST:event_cmbObjetoEvaluacionActionPerformed

    private void cmbGrupoEvaluacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbGrupoEvaluacionActionPerformed
        if(cmbGrupoEvaluacion.getSelectedIndex() == 0){
            sIdGrupoEvaluacionSeleccionado = "";
        }        
    }//GEN-LAST:event_cmbGrupoEvaluacionActionPerformed

    private void cmbGrupoEvaluacionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbGrupoEvaluacionMouseClicked
         
    }//GEN-LAST:event_cmbGrupoEvaluacionMouseClicked

    private void btnCargarDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarDetalleActionPerformed
        ubicarIdGrupoEvaluacion(cmbGrupoEvaluacion.getSelectedItem().toString());  
        cargarDetalleEvaluacion();
        
    }//GEN-LAST:event_btnCargarDetalleActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        corregirFirmaObservador(txtOld.getText(), txtNew.getText());
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String[] a_sDatos = obj.consultaGenerico("Select PesoCorporal FROM GeneralSensibilizacionDermica;");
        
        for (String strings : a_sDatos) {
            if(strings.contains(".")){
                System.out.println(strings);
            }            
        }
        
        System.out.println("PROCESO TERMINADO!!");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String[][] a_sDatosCostra = obj.consultaGenerica("Select * FROM "
                + "SensibilizacionDermica.JornadaEvaluacionSensibiDermi WHERE ID_Convencion = 4");
        
        for (int i = 0; i < a_sDatosCostra.length; i++) {
            String[] strings = a_sDatosCostra[i];
            
            String sSQL = "INSERT into SensibilizacionDermica.EvaluacionSignosClinicosSensiDermi values (" + (i+1) + ", " 
                    + strings[0] + ", " +
                     strings[1] + ", " +
                     strings[2] + ", " +
                     strings[3] + ", " +
                     strings[4] + ", " +
                     strings[5] + ", " +
                    "58, 1);";
            
            System.out.println(sSQL);
        }
        
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmPrueba.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmPrueba.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmPrueba.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmPrueba.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new FrmPrueba().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCargarDetalle;
    private javax.swing.JComboBox cmbDetalleEvaluacion;
    private javax.swing.JComboBox cmbGrupoEvaluacion;
    private javax.swing.JComboBox cmbObjetoEvaluacion;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField txtNew;
    private javax.swing.JTextField txtOld;
    // End of variables declaration//GEN-END:variables
}
