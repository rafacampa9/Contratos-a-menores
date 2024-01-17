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
    
    
    //*********************************MÉTODOS****************************************

    /**
     * Obtenemos la lista de todos los registros
     * @return 
     */
    public LinkedHashSet<FilaDatos> listaRegistros(){
        fichero = new FicheroXML();
        nodeList = new XPathStore();
        //cargamos el fichero XML en nuestro Document
        documento = fichero.cargarFicheroXML("contratos-a-menores.xml");
        //obtenemos las filas del fichero
        listaNodos = nodeList.listaNodos(documento, "//Table/Row");
        LinkedHashSet<FilaDatos> listaFilas = new LinkedHashSet<>();
        
        /**
         * iteramos sobre las filas del fichero
         */
        for (int i = 1; i < listaNodos.getLength(); i++){
            //obtenemos la línea a la que hace referencia
            Element line = (Element) listaNodos.item(i);
            
            //instanciamos una nueva lista de nodos con la etiqueta "Data"
            // "//Table/Row/Data
            NodeList dataNodes = line.getElementsByTagName("Data");
            
            //Inicializamos las columnas que contienen cada fila
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
                /**
                 * Si el tamaño de la lista de nodos es 8,
                 * Índice 0: nif
                 * Índice 1: adjudicatario
                 * Índice 2: objetoGenerico
                 * Índice 3: objeto
                 * Índice 4: fechaStr (formato String), después obtenemos
                 *              la fechaAdjudicacion en el formato que nos
                 *              interese, dependiendo de la forma de devolverlo
                 *              (Tratados en las excepciones)
                 * Índice 5: importe
                 * Índice 6: proveedoresConsultados
                 * Índice 7: tipoContrato
                 * 
                 */  
                case 8 ->
                {
                       
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
                    /**
                     * El método parse de SimpleDateFormat hay
                     * que tratarlo de tres formas para la fecha:
                     *  - string de fecha "yyyy-MM-dd'T'HH:mm:ss.SSS"
                     *  - string de fecha "dd.MM.yyyy"
                     *  - texto de fecha vacío
                     */
                    try{
                            fechaAdjudicacion = new SimpleDateFormat(
                                            "yyyy-MM-dd'T'HH:mm:ss.SSS"
                                             ).parse(fechaStr);
                    } catch(ParseException e1){
                        try{
                            fechaAdjudicacion = new SimpleDateFormat(
                                            "dd.MM.yyyy"
                                            ).parse(fechaStr);
                        } catch (ParseException e2){
                            fechaAdjudicacion = null;
                        }
                    }
                    importe = line.getElementsByTagName("Data").
                                item(5)
                                .getTextContent();
                    proveedoresConsultados = line.getElementsByTagName("Data")
                                .item(6).
                                getTextContent();
                    tipoContrato = line.getElementsByTagName("Data")
                                .item(7).
                                getTextContent();
                     
                }
                //Registros del xml con 7 atributos en su fila
                case 7 ->
                {
                    /**
                    * Traen el nif y la Empresa en el índice 0
                    */
                    String nifAndEmpresa = dataNodes.item(0).getTextContent();
                    //Obtenemos el índice en el que se separan los dos valores
                    int separatorIndex = nifAndEmpresa.indexOf(' ');
                    
                        
                    nif = nifAndEmpresa.substring(0, 
                                separatorIndex);
                    adjudicatario = nifAndEmpresa.substring(separatorIndex + 1);

                    objetoGenerico = line.getElementsByTagName("Data")
                                .item(1).getTextContent();
                    objeto = line.getElementsByTagName("Data")
                                .item(2).getTextContent();
                    fechaStr = line.getElementsByTagName("Data")
                                .item(3).getTextContent();
                    /**
                     * Tratamos la fecha según sus características
                     */
                    try{
                            fechaAdjudicacion = new SimpleDateFormat(
                                            "yyyy-MM-dd'T'HH:mm:ss.SSS"
                                             ).parse(fechaStr);
                    } catch(ParseException e1){
                        try{
                            fechaAdjudicacion = new SimpleDateFormat(
                                            "dd.MM.yyyy"
                                            ).parse(fechaStr);
                        } catch (ParseException e2){
                            fechaAdjudicacion = null;
                        }
                    }
                    importe = line.getElementsByTagName("Data").item(4)
                                .getTextContent();
                    proveedoresConsultados = line.getElementsByTagName("Data")
                                .item(5).getTextContent();
                    tipoContrato = line.getElementsByTagName("Data")
                                .item(6).getTextContent();
                    
                    
                }
                //la fila cuenta con 9 columnas
                case 9 ->
                {
                    nif = line.getElementsByTagName("Data")
                            .item(0)
                            .getTextContent();
                    adjudicatario = line.getElementsByTagName("Data")
                            .item(1)
                            .getTextContent();
                    fechaAdjudicacion = null;
                    
                    /**
                     * Si el contenido de índice 2 de 
                     * la fila es "ACTIVIDAD/REUNIONES" y en índice 3
                     *  aparece "CONFERENCIAS Y CURSOS", el problema
                     * que se da aquí es que esta columna al completo es 
                     * el valor de objetoGenerico
                     */
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
                        /**
                         * Tratamos las dos excepciones de fecha
                         */
                        fechaStr = line.getElementsByTagName("Data")
                                .item(5).getTextContent();
                        try
                        {
                            fechaAdjudicacion = new SimpleDateFormat (
                                    "yyyy-MM-dd'T'HH:mm:ss.SSS").
                                    parse(fechaStr);
                        } catch (ParseException e1)
                        {
                            try{
                                fechaAdjudicacion = new SimpleDateFormat(
                                        "dd.MM.yyyy"
                                ).parse(fechaStr);
                            } catch (ParseException e2){
                                fechaAdjudicacion = null;
                            }
                        }
                        importe = line.getElementsByTagName("Data").item(6)
                                .getTextContent();
                        proveedoresConsultados = line.getElementsByTagName("Data")
                                .item(7).getTextContent();
                        tipoContrato = line.getElementsByTagName("Data")
                                .item(8).getTextContent();
                    } else{
                        /**
                         * En este caso, es el importe el que tiene dividido
                         * su valor en dos campos, uno para el número entero
                         * y otro para la parte decimal
                         */
                        objetoGenerico = line.getElementsByTagName("Data")
                                .item(2).getTextContent();
                        objeto = line.getElementsByTagName("Data")
                                .item(3).getTextContent();
                        fechaStr = line.getElementsByTagName("Data")
                                .item(4).getTextContent();
                        /**
                         * Excepciones de fecha
                         */
                        try{
                            fechaAdjudicacion = new SimpleDateFormat(
                                            "yyyy-MM-dd'T'HH:mm:ss.SSS"
                            ).parse(fechaStr);
                        } catch(ParseException e1){
                            try{
                                fechaAdjudicacion = new SimpleDateFormat(
                                            "dd.MM.yyyy"
                                ).parse(fechaStr);
                            } catch (ParseException e2){
                                fechaAdjudicacion = null;
                            }
                        }
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
                /**
                 * Si el tamaño de la fila que nos devuelve es de
                 * 10 columnas
                 */
                default ->
                {
                    nif = line.getElementsByTagName("Data")
                            .item(0).getTextContent();
                    fechaAdjudicacion = null;
                    /***
                     * Si el índice 2 que nos devuleve es igual
                     * a " SL" o " S.L."
                     */
                    if (line.getElementsByTagName("Data").
                            item(2).
                            getTextContent().equals(" S.L.")
                            || 
                            line.getElementsByTagName("Data").
                                    item(2).getTextContent().
                                    equals(" SL")){
                        
                        adjudicatario = line.getElementsByTagName("Data")
                                .item(1).getTextContent()+ 
                                line.getElementsByTagName("Data")
                                        .item(2).getTextContent();
                        objetoGenerico = line.getElementsByTagName("Data")
                                .item(3).getTextContent();
                        objeto = line.getElementsByTagName("Data")
                                .item(4).getTextContent();
                        fechaStr = line.getElementsByTagName("Data")
                                .item(5).getTextContent();
                        /**
                         * Excepciones de fecha
                         */
                        try{
                            fechaAdjudicacion = new SimpleDateFormat(
                                            "yyyy-MM-dd'T'HH:mm:ss.SSS"
                            ).parse(fechaStr);
                        } catch(ParseException e1){
                            try{
                                fechaAdjudicacion = new SimpleDateFormat(
                                            "dd.MM.yyyy"
                                ).parse(fechaStr);
                            } catch (ParseException e2){
                                fechaAdjudicacion = null;
                            }
                        }
                        importe = line.getElementsByTagName("Data").
                                item(6).getTextContent() + "," +
                                line.getElementsByTagName("Data")
                                        .item(7).getTextContent();
                        proveedoresConsultados = line.getElementsByTagName("Data")
                                .item(8).getTextContent();
                        tipoContrato = line.getElementsByTagName("Data")
                                .item(9).getTextContent();
                        
                    /**
                     * Si el primer carácter del String devuleto por el objeto
                     * genérico es un espacio
                     */    
                    } else if (line.getElementsByTagName("Data")
                            .item(3).getTextContent()
                            .charAt(0) == ' '){
                        
                        /**
                         * Si ocurre lo mismo con indice 5
                         */
                        if (line.getElementsByTagName("Data").
                                item(5).
                                getTextContent().
                                charAt(0) == ' '){
                            
                            adjudicatario = line.getElementsByTagName("Data")
                                    .item(1).getTextContent();
                            objetoGenerico = line.getElementsByTagName("Data")
                                    .item(2).getTextContent()+
                                    line.getElementsByTagName("Data")
                                            .item(3).getTextContent();
                            /**
                             *  Juntamos el cuarto y quinto índice 
                             *  del documento
                             */
                            objeto = line.getElementsByTagName("Data")
                                    .item(4).getTextContent() +
                                    line.getElementsByTagName("Data")
                                            .item(5).getTextContent();
                            fechaStr = line.getElementsByTagName("Data")
                                    .item(6).getTextContent();
                            /**
                             * Excepciones de fecha
                             */
                            try{
                                fechaAdjudicacion = new SimpleDateFormat(
                                                "yyyy-MM-dd'T'HH:mm:ss.SSS"
                                ).parse(fechaStr);
                            } catch(ParseException e1){
                                try{
                                    fechaAdjudicacion = new SimpleDateFormat(
                                                "dd.MM.yyyy"
                                    ).parse(fechaStr);
                                } catch (ParseException e2){
                                    fechaAdjudicacion = null;
                                }
                            }
                            importe = line.getElementsByTagName("Data")
                                    .item(7).getTextContent();
                            proveedoresConsultados = line.getElementsByTagName("Data")
                                    .item(8).getTextContent();
                            tipoContrato = line.getElementsByTagName("Data")
                                    .item(9).getTextContent();
                        } else{
                            /***
                             * En este caso, de nuevo el problema se encuentra
                             * en el importe (al igual que en otros registros
                             * de distinto tamaño de columnas, que también
                             * contenían este error)
                             */
                            adjudicatario = line.getElementsByTagName("Data")
                                    .item(1).getTextContent();
                            objetoGenerico = line.getElementsByTagName("Data")
                                    .item(2).getTextContent()+
                                    line.getElementsByTagName("Data")
                                            .item(3).getTextContent();
                            objeto = line.getElementsByTagName("Data")
                                    .item(4).getTextContent();
                            fechaStr = line.getElementsByTagName("Data")
                                    .item(5).getTextContent();
                            /**
                             * Excepciones de fecha
                             */
                            try{
                                fechaAdjudicacion = new SimpleDateFormat(
                                                "yyyy-MM-dd'T'HH:mm:ss.SSS"
                                ).parse(fechaStr);
                            } catch(ParseException e1){
                                try{
                                    fechaAdjudicacion = new SimpleDateFormat(
                                                "dd.MM.yyyy"
                                    ).parse(fechaStr);
                                } catch (ParseException e2){
                                    fechaAdjudicacion = null;
                                }
                            }
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
                        /**
                         * Si el primer carácter del String devuleto por el objeto
                         * genérico NO es un espacio, objetoGenerico será igual
                         * al valor del tercer y cuarto nodo juntos.
                         * Importe también nos lo devuelve dividido en entero
                         * y decimal, lo juntamos.
                         */
                        adjudicatario = line.getElementsByTagName("Data")
                                .item(1).getTextContent();
                        objetoGenerico = line.getElementsByTagName("Data")
                                .item(2).getTextContent()+ " " + 
                                line.getElementsByTagName("Data")
                                        .item(3).getTextContent();
                        objeto = line.getElementsByTagName("Data")
                                .item(4).getTextContent();
                        fechaStr = line.getElementsByTagName("Data")
                                .item(5).getTextContent();
                        /**
                         * Excepciones de fecha
                         */
                        try{
                            fechaAdjudicacion = new SimpleDateFormat(
                                            "yyyy-MM-dd'T'HH:mm:ss.SSS"
                            ).parse(fechaStr);
                        } catch(ParseException e1){
                            try{
                                fechaAdjudicacion = new SimpleDateFormat(
                                            "dd.MM.yyyy"
                                ).parse(fechaStr);
                            } catch (ParseException e2){
                                fechaAdjudicacion = null;
                            }
                        }
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
            
            //creamos rootElement como la raíz de nuestro XML
            Element rootElement = doc.createElement("contratos");
            doc.appendChild(rootElement);
            
            /**
             * Vamos creando las filas
             */
            for (FilaSinTipoContrato fila: listaDatos){
                // objeto que usaremos para manipular la fila
                Element filaElement = doc.createElement("contrato");
                
                /**
                 * le agregamos a la fila el elemento creado para el nif
                 * y así con todos los demás
                 */
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
                
                //finalmente, la fila completada la agregamos al nodo raíz
                rootElement.appendChild(filaElement);
            }
            /**
             * Transformamos el documento en el XML result
             * doc
             * TransformerFactory as TF
             * Transformer as T = TF.new
             * DOMSource (doc) as S
             * StreamResult as r = new(File)
             * T.transform(S, r)
             */
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("datos del gobierno sobre contratos a menores.xml"));
            transformer.transform(source, result);
            /**
             * Nos proporciona un mensaje de diálogo que nos confirma
             * que el archivo se ha generado correctamente.
             */
            JOptionPane.showMessageDialog(null, "Archivo generado correctamente");
        } catch (ParserConfigurationException | TransformerException e){
            e.printStackTrace();
        }
    }
}
