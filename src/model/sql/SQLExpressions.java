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
import java.text.SimpleDateFormat;

import model.entidades.FilaDatos;
import model.entidades.FilaSinTipoContrato;


/**
 *
 * @author rafacampa9
 */
public class SQLExpressions {
    
    private final Conexion conn = new Conexion();
    
    // Métodos
    public boolean insertar(LinkedHashSet<FilaDatos> lista){
        
        Connection conexion = conn.conectarDB();
        PreparedStatement ps = null;
        String insert = """
                        INSERT INTO DATOS_GOB VALUES 
                        (?, ?, ?, ?, ?, ?, ?, ?)
                        """;
        
        try {
            
            ps = conexion.prepareStatement(insert);
            
            for (FilaDatos fila: lista){
                ps.setString(1, fila.getNif());
                ps.setString(2, fila.getAdjudicatario());
                ps.setString(3, fila.getObjetoGenerico());
                ps.setString(4, fila.getObjeto());
                if (fila.getFechaAdjudicacion()!=null)
                    ps.setDate(5, new java.sql.Date(fila.getFechaAdjudicacion().getTime()));
                else
                    ps.setDate(5, null);
                ps.setString(6, fila.getImporte());
                ps.setString(7, fila.getProveedoresConsultados());
                ps.setString(8, fila.getTipoContrato());
                
                ps.executeUpdate();
            }
            System.out.println("Registros insertados correctamente");
            return true;
        } catch (SQLException ex){
            ex.printStackTrace();
            return false;
        }
    
    }
    
    public LinkedHashSet<FilaDatos> leer(){
        
        Connection conexion = conn.conectarDB();
        LinkedHashSet<FilaDatos> registros = new LinkedHashSet<>();
        FilaDatos fila;
        PreparedStatement ps = null;
        ResultSet rs = null;
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String select = """
                        SELECT * FROM DATOS_GOB
                        """;
        
        try{
            ps = conexion.prepareStatement(select);
            rs = ps.executeQuery();
            
            while (rs.next()){
                fila = new FilaDatos();
                fila.setNif(rs.getString("nif"));
                fila.setAdjudicatario(rs.getString("adjudicatario"));
                fila.setObjetoGenerico(rs.getString("objeto_generico"));
                fila.setObjeto(rs.getString("objeto"));
                if (rs.getString("fecha_adjudicacion")!=null)
                    fila.setFechaAdjudicacion(rs.getDate("fecha_adjudicacion"));
                else
                    fila.setFechaAdjudicacion(null);
                fila.setImporte(rs.getString("importe"));
                fila.setProveedoresConsultados(rs.getString("proveedores_consultados"));
                fila.setTipoContrato(rs.getString("tipo_contrato"));

                
                registros.add(fila);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return registros;
    }
    
    public LinkedHashSet<FilaSinTipoContrato> leerSinTipoContrato(){
        
        Connection conexion = conn.conectarDB();
        LinkedHashSet<FilaSinTipoContrato> registros = new LinkedHashSet<>();
        FilaSinTipoContrato fila;
        PreparedStatement ps = null;
        ResultSet rs = null;
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
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
                fila.setAdjudicatario(rs.getString("adjudicatario"));
                fila.setObjetoGenerico(rs.getString("objeto_generico"));
                fila.setObjeto(rs.getString("objeto"));
                if (rs.getString("fecha_adjudicacion")!=null)
                    fila.setFechaAdjudicacion(rs.getDate("fecha_adjudicacion"));
                else
                    fila.setFechaAdjudicacion(null);
                fila.setImporte(rs.getString("importe"));
                fila.setProveedoresConsultados(rs.getString("proveedores_consultados"));

                
                registros.add(fila);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return registros;
    }
    
    public boolean borrarRegistros(){
        Connection conexion = conn.conectarDB();
        PreparedStatement ps = null;
        String delete = """
                        DELETE FROM DATOS_GOB
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
    }
}
