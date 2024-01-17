/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashSet;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.entidades.FilaDatos;
import model.entidades.FilaSinTipoContrato;
import model.sql.SQLExpressions;
import view.Ventana;

/**
 *
 * @author rafacampa9
 */
public class CtrlVista implements ActionListener{
    
    //***********************************ATRIBUTOS*******************************************
    private final Ventana ventana;
    private final SQLExpressions sql;
    private final Ctrl ctrl;
    private LinkedHashSet<FilaDatos> filas;
    private LinkedHashSet<FilaSinTipoContrato> filasSN;
    private ButtonGroup grupo = new ButtonGroup();
    
    

    public CtrlVista(Ventana ventana, SQLExpressions sql, Ctrl ctrl) {
        this.ventana = ventana;
        this.sql = sql;
        this.ctrl = ctrl;
        this.ventana.btnInsert.addActionListener(this);
        this.ventana.btnRead.addActionListener(this);
        this.ventana.btnXML.addActionListener(this);
        this.ventana.btnDelete.addActionListener(this);
        
        this.grupo.add(ventana.btnInsert);
        this.grupo.add(ventana.btnRead);
        this.grupo.add(ventana.btnXML);
        this.grupo.add(ventana.btnDelete);
               
    }
    
    //************************************MÉTODOS********************************************
    
    /**
     * Método para iniciar la ventana
     */
    public void iniciar(){
        ventana.setTitle("Contratos a menores");
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
        ventana.setResizable(false);
    }
    
    
    
    /**
     * Vaciar el contenido de la tabla
     */
    public void limpiar(){   
        DefaultTableModel table = (DefaultTableModel) ventana.table.getModel();
        table.setRowCount(0);
    }

    
    
    /**
     * Método vinculado a la escucha
     * del ActionEvent
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        /**
         * Si pulsamos insertar
         */
        if (e.getSource() == ventana.btnInsert){
            //limpiamos la tabla
            limpiar();
            //le pasamos a las filas el método listaRegistros()
            filas = ctrl.listaRegistros();
            //insertamos el registro
            boolean insertado = sql.insertar(filas);
            /**
             * Si se ha realizado la inserción nos muestra
             * un mensaje de diálogo que nos dice que se 
             * han insertado correctamente.
             * 
             * Si no se puede realizar la inserción, nos devuelve
             * un mensaje de diálogo que nos informa que no se han
             * podido insertar 
             */
            if (insertado)
                JOptionPane.showMessageDialog(
                        null,
                        "Registros insertados correctamente"
                        );
            else
                JOptionPane.showMessageDialog(
                        null, 
                        "No se han podido insertar los registros"
                );
        }
        
        
        
        
        
        /**
         * Si pulsamos el botón de Leer
         */
        
        if (e.getSource() == ventana.btnRead){
            //tenemos el método leer de la clase SQLExpressions
            filas = sql.leer();
            
            DefaultTableModel table = (DefaultTableModel) ventana.table.getModel();
            /**
             * Añadimos los registros a la tabla
             */
            for (FilaDatos fila: filas){
                Object [] f = {
                        fila.getNif(), fila.getAdjudicatario(), fila.getObjetoGenerico(),
                        fila.getObjeto(), fila.getFechaAdjudicacion(), fila.getImporte(),
                        fila.getProveedoresConsultados(), fila.getTipoContrato()
                };
                table.addRow(f);
            }
            
        }
        
        
        
        
        
        /**
         * Si pulsamos GENERAR archivo XML
         */
        if (e.getSource() == ventana.btnXML){
            //Limpiamos la tabla
            limpiar();
            //Introducimos en la variable filasSN las filas sin tipo contrato
            filasSN = sql.leerSinTipoContrato(); 
            //Generamos el archivo con el método generarXML de la clase Ctrl
            ctrl.generarXML(filasSN);
        }
        
        
        
        
        /**
         * Si pulsamos ELIMINAR archivo
         */
        if (e.getSource() == ventana.btnDelete){
            //limpiamos la tabla
            limpiar();
            
            //borramos el registro y devolvemos un booleano
            boolean borrado = sql.borrarRegistros();
            
            /**
             * Si se ha realizado el borrado correctamente,
             * mostramos un mensaje de diálogo indicándolo.
             * 
             * Si no, también se indicará mediante un mensaje
             * de diálogo
             */
            if (borrado)
                JOptionPane.showMessageDialog(
                        null, 
                        "Registros de la tabla eliminados"
                );
            else
                JOptionPane.showMessageDialog(
                        null, 
                        "No se han podido eliminar los registros"
                );
        }
    }
    
    
    
}
