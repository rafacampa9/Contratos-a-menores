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
    
    private Ventana ventana;
    private SQLExpressions sql;
    private Ctrl ctrl;
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
    
    public void iniciar(){
        ventana.setTitle("Contratos a menores");
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
        ventana.setResizable(false);
    }
    
    public void limpiar(){
        DefaultTableModel table = (DefaultTableModel) ventana.table.getModel();
        table.setRowCount(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ventana.btnInsert){
            limpiar();
            filas = ctrl.listaRegistros();
            boolean insertado = sql.insertar(filas);
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
        
        if (e.getSource() == ventana.btnRead){
            filas = sql.leer();
            
            DefaultTableModel table = (DefaultTableModel) ventana.table.getModel();
            
            for (FilaDatos fila: filas){
                Object [] f = {
                        fila.getNif(), fila.getAdjudicatario(), fila.getObjetoGenerico(),
                        fila.getObjeto(), fila.getFechaAdjudicacion(), fila.getImporte(),
                        fila.getProveedoresConsultados(), fila.getTipoContrato()
                };
                table.addRow(f);
            }
            
        }
        
        if (e.getSource() == ventana.btnXML){
            limpiar();
            filasSN = sql.leerSinTipoContrato(); 
            ctrl.generarXML(filasSN);
        }
        
        if (e.getSource() == ventana.btnDelete){
            limpiar();
            boolean borrado = sql.borrarRegistros();
            
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
