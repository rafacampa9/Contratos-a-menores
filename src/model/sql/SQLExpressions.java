/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.sql;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.sql.ResultSet;
//import java.text.SimpleDateFormat;

import model.entidades.FilaDatos;
import model.entidades.FilaSinTipoContrato;


/**
 *
 * @author rafacampa9
 */
public class SQLExpressions {
    
    private final Conexion conn = new Conexion();
    
    // ***************************MÉTODOS***************************************
    /**
     * Para insertar la lista de registros
     * que tenemos en un LinkedHashSet y devuelve
     * true si se inserta o false, en caso de que
     * no sea posible (salte una Excepción)
     * 
     * @param lista
     * @return 
     */
    public boolean insertar(LinkedHashSet<FilaDatos> lista){
        /**
         * Conectamos con la base de datos
         * y preparamos el insert según
         * la cantidad de campos que queramos
         * rellenar (en este caso, 8)
         */
         
        Connection conexion = conn.conectarDB();
        PreparedStatement ps = null;
        String insert = """
                        INSERT INTO DATOS_GOB VALUES 
                        (?, ?, ?, ?, ?, ?, ?, ?)
                        """;
        
        //Aquí comienza el código a tratar con SQLExcepción
        try {
            
            ps = conexion.prepareStatement(insert);
            
            for (FilaDatos fila: lista){
                ps.setString(1, fila.getNif());
                ps.setString(2, fila.getAdjudicatario());
                ps.setString(3, fila.getObjetoGenerico());
                ps.setString(4, fila.getObjeto());
                if (fila.getFechaAdjudicacion()!=null){
                    ps.setDate(5, new java.sql.Date(
                                        fila.
                                                getFechaAdjudicacion().
                                                getTime()
                    ));
                }else{
                    ps.setDate(5, null);
                }
                ps.setString(6, fila.getImporte());
                ps.setString(7, fila.getProveedoresConsultados());
                ps.setString(8, fila.getTipoContrato());
                
                ps.executeUpdate();
            }
            return true;
        } catch (SQLException ex){
            ex.printStackTrace();
            return false;
        } finally{
            try{
                conexion.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    
    }
    
    /**
     * Leemos todos los datos
     * del archivo XML proporcionado
     * por el Gobierno
     * 
     * @return 
     */
    public LinkedHashSet<FilaDatos> leer(){
        //Conectamos
        Connection conexion = conn.conectarDB();
        //Instanciamos un LinkedHashSet para obtener los registros
        LinkedHashSet<FilaDatos> registros = new LinkedHashSet<>();
        FilaDatos fila;
        PreparedStatement ps = null;
        ResultSet rs = null;
        //Consulta SELECT de todo
        String select = """
                        SELECT * FROM DATOS_GOB
                        """;
        
        try{
            ps = conexion.prepareStatement(select);
            rs = ps.executeQuery();
            
            while (rs.next()){
                fila = new FilaDatos();
                //NIF
                fila.setNif(rs.getString("nif"));
                //Adjudicatario
                fila.setAdjudicatario(
                        rs.getString("adjudicatario"));
                //Objeto Genérico
                fila.setObjetoGenerico(
                        rs.getString("objeto_generico"));
                //Objeto
                fila.setObjeto(rs.getString("objeto"));
                //Fecha Adjudicación
                if (rs.getString("fecha_adjudicacion")!=null){
                    fila.setFechaAdjudicacion(
                            rs.getDate(
                                    "fecha_adjudicacion"
                            ));
                }else
                    fila.setFechaAdjudicacion(null);
                //Importe
                fila.setImporte(rs.getString("importe"));
                //Proveedores consultados
                fila.setProveedoresConsultados(
                        rs.getString(
                                "proveedores_consultados"
                        ));
                //Tipo Contrato
                fila.setTipoContrato(rs.getString("tipo_contrato"));

                //Agregamos el registo a la lista de registros
                registros.add(fila);
            }
            /**
             * Excepciones
             */
        } catch (SQLException e){
            e.printStackTrace();
        } finally{
            try{
                conexion.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        //retornamos los registros
        return registros;
    }
    
    
    /**
     * Leemos todos los campos de la tabla DATOS_GOB
     * salvo el campo tipoContrato
     * @return 
     */
    public LinkedHashSet<FilaSinTipoContrato> leerSinTipoContrato(){
        
        Connection conexion = conn.conectarDB();
        LinkedHashSet<FilaSinTipoContrato> registros = new LinkedHashSet<>();
        FilaSinTipoContrato fila;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String select = """
                        SELECT nif, adjudicatario, objeto_generico, objeto,
                        fecha_adjudicacion, importe, proveedores_consultados
                        from DATOS_GOB
                        """;
        
        try{
            ps = conexion.prepareStatement(select);
            rs = ps.executeQuery();
            
            while (rs.next()){
                fila = new FilaSinTipoContrato();
                fila.setNif(rs.getString("nif"));
                fila.setAdjudicatario(
                        rs.getString(
                                    "adjudicatario"));
                fila.setObjetoGenerico(
                        rs.getString("objeto_generico"));
                fila.setObjeto(rs.getString("objeto"));
                if (rs.getString("fecha_adjudicacion")!=null){
                    fila.setFechaAdjudicacion(
                            rs.getDate("fecha_adjudicacion"));
                }else{
                    fila.setFechaAdjudicacion(null);
                }
                fila.setImporte(rs.getString("importe"));
                fila.setProveedoresConsultados(
                        rs.getString(
                                "proveedores_consultados"));

                
                registros.add(fila);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally{
            try{
                conexion.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return registros;
    }
    
    
    /**
     * Vaciamos la tabla DATOS_GOB
     * con el comando TRUNCATE TABLE
     * 
     * @return 
     */
    public boolean borrarRegistros(){
        Connection conexion = conn.conectarDB();
        PreparedStatement ps = null;
        String delete = """
                        TRUNCATE TABLE DATOS_GOB
                        """;
        
        try
        {
            ps = conexion.prepareStatement(delete);
            ps.executeUpdate();
            
            return true; 
        } catch (SQLException ex)
        {
            ex.printStackTrace();
            return false;
        }
        finally
        {
            try
            {
                conexion.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        
    }
    }
}
