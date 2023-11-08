/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.entidades;

import java.util.Date;

/**
 *
 * @author rafacampa9
 */
public class FilaDatos {
    
    // ATRIBUTOS
    private String nif, adjudicatario, objetoGenerico, objeto, importe, 
            proveedoresConsultados, tipoContrato;
    private Date fechaAdjudicacion;
    
    //CONSTRUCTORES

    public FilaDatos() {
    }

    public FilaDatos(String nif, String adjudicatario, String objetoGenerico, String objeto , Date fechaAdjudicacion,  String importe, String proveedoresConsultados, String tipoContrato) {
        this.nif = nif;
        this.adjudicatario = adjudicatario;
        this.objetoGenerico = objetoGenerico;
        this.objeto = objeto;
        this.fechaAdjudicacion = fechaAdjudicacion;
        this.importe = importe;
        this.proveedoresConsultados = proveedoresConsultados;
        this.tipoContrato = tipoContrato;
        
    }

    // GETTER Y SETTER
    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getAdjudicatario() {
        return adjudicatario;
    }

    public void setAdjudicatario(String adjudicatario) {
        this.adjudicatario = adjudicatario;
    }

    public String getObjetoGenerico() {
        return objetoGenerico;
    }

    public void setObjetoGenerico(String objetoGenerico) {
        this.objetoGenerico = objetoGenerico;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getProveedoresConsultados() {
        return proveedoresConsultados;
    }

    public void setProveedoresConsultados(String proveedoresConsultados) {
        this.proveedoresConsultados = proveedoresConsultados;
    }

    public String getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public Date getFechaAdjudicacion() {
        return fechaAdjudicacion;
    }

    public void setFechaAdjudicacion(Date fechaAdjudicacion) {
        this.fechaAdjudicacion = fechaAdjudicacion;
    }

    
    //toString

    @Override
    public String toString() {
        return "FilaDatos{" + "nif=" + nif + ", adjudicatario=" + adjudicatario + ", objetoGenerico=" + objetoGenerico + ", objeto=" + objeto + ", importe=" + importe + ", proveedoresConsultados=" + proveedoresConsultados + ", tipoContrato=" + tipoContrato + ", fechaAdjudicacion=" + fechaAdjudicacion + '}';
    }
    
    
}
