/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package contratomenores;

import controller.Ctrl;
import controller.CtrlVista;
import model.sql.SQLExpressions;
import view.Ventana;

/**
 *
 * @author rafacampa9
 */
public class ContratoMenores {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Ventana ventana = new Ventana();
        SQLExpressions sql = new SQLExpressions();
        Ctrl c = new Ctrl();
        CtrlVista ctrl = new CtrlVista(ventana, sql, c);
        ctrl.iniciar();  
    }
    
}
