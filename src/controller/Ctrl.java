/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import model.entidades.FicheroXML;
import model.entidades.FilaDatos;
import model.entidades.FilaSinTipoContrato;
import model.entidades.XPathStore;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 *
 * @author rafacampa9
 */
public class Ctrl {
    
    private FicheroXML fichero;
    private Document documento;
    private XPathStore nodeList;
    private FilaDatos filaDatos;
    private NodeList listaNodos;

    
    public LinkedHashSet<FilaDatos> listaRegistros(){
        fichero = new FicheroXML();
        nodeList = new XPathStore();
        documento = fichero.cargarFicheroXML("contratos-a-menores.xml");
        listaNodos = nodeList.listaNodos(documento, "//Table/Row");
        LinkedHashSet<FilaDatos> listaFilas = new LinkedHashSet<>();
        
        for (int i = 1; i < listaNodos.getLength(); i++){
            
            Element line = (Element) listaNodos.item(i);
            
            NodeList dataNodes = line.getElementsByTagName("Data");
            
            String nif = null;
            String adjudicatario = null;
            String objetoGenerico = null;
            String objeto = null;
            String fechaStr = null;
            Date fechaAdjudicacion = null;
            String importe = null;
            String proveedoresConsultados = null;
            String tipoContrato = null;
            fechaAdjudicacion = new Date();
         
            switch (dataNodes.getLength())
            {               
                case 8 ->
                {
                    try{   
                        nif = line.getElementsByTagName("Data")
                                .item(0)
                                .getTextContent();
                        adjudicatario = line.getElementsByTagName("Data")
                                .item(1)
                                .getTextContent();
                        objetoGenerico = line.getElementsByTagName("Data")
                                .item(2).getTextContent();
                        objeto = line.getElementsByTagName("Data")
                                .item(3).getTextContent();
                        fechaStr = line.getElementsByTagName("Data")
                                .item(4).getTextContent();
                        
                        fechaAdjudicacion = new SimpleDateFormat ("yyyy-MM-dd'T'HH:mm:ss.SSS").
                                parse(fechaStr);
                        importe = line.getElementsByTagName("Data").item(5)
                                .getTextContent();
                        proveedoresConsultados = line.getElementsByTagName("Data")
                                .item(6).getTextContent();
                        tipoContrato = line.getElementsByTagName("Data")
                                .item(7).getTextContent();
                    } catch (ParseException e1){
                        try{
                            nif = line.getElementsByTagName("Data")
                                    .item(0)
                                    .getTextContent();
                            adjudicatario = line.getElementsByTagName("Data")
                                    .item(1)
                                    .getTextContent();
                            objetoGenerico = line.getElementsByTagName("Data")
                                    .item(2).getTextContent();
                            objeto = line.getElementsByTagName("Data")
                                    .item(3).getTextContent();
                            fechaStr = line.getElementsByTagName("Data")
                                    .item(4).getTextContent();
                            fechaAdjudicacion = new SimpleDateFormat ("dd.MM.yyyy")
                                    .parse(fechaStr);
                            importe = line.getElementsByTagName("Data").item(5)
                                    .getTextContent();
                            proveedoresConsultados = line.getElementsByTagName("Data")
                                    .item(6).getTextContent();
                            tipoContrato = line.getElementsByTagName("Data")
                                    .item(7).getTextContent();
                            
                        } catch (ParseException e2){
                            nif = line.getElementsByTagName("Data")
                                    .item(0)
                                    .getTextContent();
                            adjudicatario = line.getElementsByTagName("Data")
                                    .item(1)
                                    .getTextContent();
                            objetoGenerico = line.getElementsByTagName("Data")
                                    .item(2).getTextContent();
                            objeto = line.getElementsByTagName("Data")
                                    .item(3).getTextContent();
                            importe = line.getElementsByTagName("Data").item(5)
                                    .getTextContent();
                            proveedoresConsultados = line.getElementsByTagName("Data")
                                    .item(6).getTextContent();
                            tipoContrato = line.getElementsByTagName("Data")
                                    .item(7).getTextContent();
                            
                            fechaAdjudicacion = null;
                        }
                    }
                }
                case 7 ->
                {
                    String nifAndEmpresa = dataNodes.item(0).getTextContent();
                    int separatorIndex = nifAndEmpresa.indexOf(' ');
                    try{
                        nif = nifAndEmpresa.substring(0, separatorIndex);
                        adjudicatario = nifAndEmpresa.substring(separatorIndex + 1);

                        objetoGenerico = line.getElementsByTagName("Data")
                                .item(1).getTextContent();
                        objeto = line.getElementsByTagName("Data")
                                .item(2).getTextContent();
                        fechaStr = line.getElementsByTagName("Data")
                                .item(3).getTextContent();
                        fechaAdjudicacion = new SimpleDateFormat ("yyyy-MM-dd'T'HH:mm:ss.SSS").
                                parse(fechaStr);
                        importe = line.getElementsByTagName("Data").item(4)
                                .getTextContent();
                        proveedoresConsultados = line.getElementsByTagName("Data")
                                .item(5).getTextContent();
                        tipoContrato = line.getElementsByTagName("Data")
                                .item(6).getTextContent();
                        
                    } catch (ParseException e1){
                        try{
                            nif = nifAndEmpresa.substring(0, separatorIndex);
                            adjudicatario = nifAndEmpresa.substring(separatorIndex + 1);
                            
                            objetoGenerico = line.getElementsByTagName("Data")
                                    .item(1).getTextContent();
                            objeto = line.getElementsByTagName("Data")
                                    .item(2).getTextContent();
                            fechaStr = line.getElementsByTagName("Data")
                                    .item(3).getTextContent();
                            fechaAdjudicacion = new SimpleDateFormat ("dd.MM.yyyy")
                                    .parse(fechaStr);
                            importe = line.getElementsByTagName("Data").item(4)
                                    .getTextContent();
                            proveedoresConsultados = line.getElementsByTagName("Data")
                                    .item(5).getTextContent();
                            tipoContrato = line.getElementsByTagName("Data")
                                    .item(6).getTextContent();
                            
                        } catch (ParseException e2){
                            nif = nifAndEmpresa.substring(0, separatorIndex);
                            adjudicatario = nifAndEmpresa.substring(separatorIndex + 1);
                            
                            objetoGenerico = line.getElementsByTagName("Data")
                                    .item(1).getTextContent();
                            objeto = line.getElementsByTagName("Data")
                                    .item(2).getTextContent();
                            fechaAdjudicacion = null;
                            importe = line.getElementsByTagName("Data").item(4)
                                    .getTextContent();
                            proveedoresConsultados = line.getElementsByTagName("Data")
                                    .item(5).getTextContent();
                            tipoContrato = line.getElementsByTagName("Data")
                                    .item(6).getTextContent();
                        }
                    }
                }
                case 9 ->
                {
                    nif = line.getElementsByTagName("Data")
                            .item(0)
                            .getTextContent();
                    adjudicatario = line.getElementsByTagName("Data")
                            .item(1)
                            .getTextContent();
                    fechaAdjudicacion = null;
                    if ((line.getElementsByTagName("Data")
                            .item(2)
                            .getTextContent()
                            .equals("ACTIVIDAD/REUNIONES")
                            && line.getElementsByTagName("Data")
                                    .item(3).getTextContent()
                                    .equals(" CONFERENCIAS Y CURSOS"))
                            ||
                            line.getElementsByTagName("Data")
                            .item(2)
                            .getTextContent()
                            .equals("ACTIVIDAD/REUNIONES")
                            && line.getElementsByTagName("Data")
                            .item(3)
                            .getTextContent()
                            .equals("CONFERENCIAS Y CURSOS")){
                        
                        
                        objetoGenerico = line.getElementsByTagName("Data")
                                .item(2).getTextContent() +
                                line.getElementsByTagName("Data").item(3)
                                        .getTextContent();
                        objeto = line.getElementsByTagName("Data")
                                .item(4).getTextContent();
                        
                        importe = line.getElementsByTagName("Data").item(6)
                                .getTextContent();
                        proveedoresConsultados = line.getElementsByTagName("Data")
                                .item(7).getTextContent();
                        tipoContrato = line.getElementsByTagName("Data")
                                .item(8).getTextContent();
                    } else{
                        objetoGenerico = line.getElementsByTagName("Data")
                                .item(2).getTextContent();
                        objeto = line.getElementsByTagName("Data")
                                .item(3).getTextContent();
                        importe = line.getElementsByTagName("Data")
                                .item(5).getTextContent() + "," +
                                line.getElementsByTagName("Data")
                                        .item(6).getTextContent();
                        proveedoresConsultados = line.getElementsByTagName("Data")
                                .item(7).getTextContent();
                        tipoContrato = line.getElementsByTagName("Data")
                                .item(8).getTextContent();
                    }
                }
                default ->
                {
                    nif = line.getElementsByTagName("Data")
                            .item(0).getTextContent();
                    fechaAdjudicacion = null;
                    if (line.getElementsByTagName("Data").item(2).getTextContent().equals(" S.L.")
                            || line.getElementsByTagName("Data").item(2).getTextContent().equals(" SL")){
                        adjudicatario = line.getElementsByTagName("Data")
                                .item(1).getTextContent()+ 
                                line.getElementsByTagName("Data")
                                        .item(2).getTextContent();
                        objetoGenerico = line.getElementsByTagName("Data")
                                .item(3).getTextContent();
                        objeto = line.getElementsByTagName("Data")
                                .item(4).getTextContent();
                        importe = line.getElementsByTagName("Data").
                                item(6).getTextContent() + "," +
                                line.getElementsByTagName("Data")
                                        .item(7).getTextContent();
                        proveedoresConsultados = line.getElementsByTagName("Data")
                                .item(8).getTextContent();
                        tipoContrato = line.getElementsByTagName("Data")
                                .item(9).getTextContent();
                    } else if (line.getElementsByTagName("Data").item(3).getTextContent().charAt(0) == ' '){
                        if (line.getElementsByTagName("Data").item(5).getTextContent().charAt(0) == ' '){
                            adjudicatario = line.getElementsByTagName("Data")
                                    .item(1).getTextContent();
                            objetoGenerico = line.getElementsByTagName("Data")
                                    .item(2).getTextContent()+
                                    line.getElementsByTagName("Data")
                                            .item(3).getTextContent();
                            objeto = line.getElementsByTagName("Data")
                                    .item(4).getTextContent() +
                                    line.getElementsByTagName("Data")
                                            .item(5).getTextContent();
                            importe = line.getElementsByTagName("Data")
                                    .item(7).getTextContent();
                            proveedoresConsultados = line.getElementsByTagName("Data")
                                    .item(8).getTextContent();
                            tipoContrato = line.getElementsByTagName("Data")
                                    .item(9).getTextContent();
                        } else{
                            adjudicatario = line.getElementsByTagName("Data")
                                    .item(1).getTextContent();
                            objetoGenerico = line.getElementsByTagName("Data")
                                    .item(2).getTextContent()+
                                    line.getElementsByTagName("Data")
                                            .item(3).getTextContent();
                            objeto = line.getElementsByTagName("Data")
                                    .item(4).getTextContent();
                            importe = line.getElementsByTagName("Data").
                                    item(6).getTextContent() + "," +
                                    line.getElementsByTagName("Data")
                                            .item(7).getTextContent();
                            proveedoresConsultados = line.getElementsByTagName("Data")
                                    .item(8).getTextContent();
                            tipoContrato = line.getElementsByTagName("Data")
                                    .item(9).getTextContent();
                        }
                        
                    } else {
                        adjudicatario = line.getElementsByTagName("Data")
                                .item(1).getTextContent();
                        objetoGenerico = line.getElementsByTagName("Data")
                                .item(2).getTextContent()+ " " + 
                                line.getElementsByTagName("Data")
                                        .item(3).getTextContent();
                        objeto = line.getElementsByTagName("Data")
                                .item(4).getTextContent();
                        importe = line.getElementsByTagName("Data").
                                item(6).getTextContent() + "," +
                                line.getElementsByTagName("Data")
                                        .item(7).getTextContent();
                        proveedoresConsultados = line.getElementsByTagName("Data")
                                .item(8).getTextContent();
                        tipoContrato = line.getElementsByTagName("Data")
                                .item(9).getTextContent();
                    }
                }
            }
                
            if (nif != null )
                filaDatos = new FilaDatos (nif, adjudicatario, objetoGenerico,
                        objeto, fechaAdjudicacion, importe,
                        proveedoresConsultados, tipoContrato
                        );
                
            listaFilas.add(filaDatos);
        }

        return listaFilas;
    }
    
    public void generarXML(LinkedHashSet<FilaSinTipoContrato> listaDatos){
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            
            Element rootElement = doc.createElement("contratos");
            doc.appendChild(rootElement);
            
            for (FilaSinTipoContrato fila: listaDatos){
                Element filaElement = doc.createElement("contrato");
                
                Element nifElement = doc.createElement("nif");
                nifElement.appendChild(doc.createTextNode(fila.getNif()));
                filaElement.appendChild(nifElement);
                
                Element adjudicatarioElement = doc.createElement("adjudicatario");
                adjudicatarioElement.appendChild(doc.createTextNode(fila.getAdjudicatario()));
                filaElement.appendChild(adjudicatarioElement);
                
                Element objetoGenericoElement = doc.createElement("objeto_generico");
                objetoGenericoElement.appendChild(doc.createTextNode(fila.getObjetoGenerico()));
                filaElement.appendChild(objetoGenericoElement);
                
                Element objetoElement = doc.createElement("objeto");
                objetoElement.appendChild(doc.createTextNode(fila.getObjeto()));
                filaElement.appendChild(objetoElement);
                
                Element fechaAdjudicacionElement = doc.createElement("fecha_adjudicacion");
                if (fila.getFechaAdjudicacion()!=null)
                    fechaAdjudicacionElement.appendChild(doc.createTextNode(formato.format(fila.getFechaAdjudicacion())));
                else
                    fechaAdjudicacionElement.appendChild(doc.createTextNode("null"));
                filaElement.appendChild(fechaAdjudicacionElement);
                
                Element importeElement = doc.createElement("importe");
                importeElement.appendChild(doc.createTextNode(fila.getImporte()));
                filaElement.appendChild(importeElement);
                
                Element proveedoresConsultadosElement = doc.createElement("proveedores_consultados");
                proveedoresConsultadosElement.appendChild(doc.createTextNode(fila.getProveedoresConsultados()));
                filaElement.appendChild(proveedoresConsultadosElement);
                
                rootElement.appendChild(filaElement);
            }
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("datos del gobierno sobre contratos a menores.xml"));
            transformer.transform(source, result);
            
            JOptionPane.showMessageDialog(null, "Archivo generado correctamente");
        } catch (ParserConfigurationException | TransformerException e){
            e.printStackTrace();
        }
    }
}
