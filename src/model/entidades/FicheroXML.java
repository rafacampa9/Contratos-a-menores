/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.entidades;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author rafacampa9
 */
public class FicheroXML {
    
    /**
     * Nos carga el fichero XML en un 
     * Document para poder manipularlo.
     * 
     * Le pasamos el path del fichero XML por
     * parámetro
     * 
     * 
     * @param path
     * @return 
     */
    public Document cargarFicheroXML(String path){
        try{
            /**
             * Proceso por defecto para cargar un fichero XML:
             * 
             * DocumentBuilderFactory>DocumentBuilder>Document
             */
            DocumentBuilderFactory factory =  DocumentBuilderFactory.newInstance();
            /**
             * Ignoramos los espacios en blanco contenidos entre elementos
             */
            factory.setIgnoringElementContentWhitespace(true);
            
            DocumentBuilder builder = factory.newDocumentBuilder();
            File file = new File(path);
        
            Document doc = builder.parse(file);
            return doc;
            /**
             * Excepciones 
             */
        } catch (ParserConfigurationException | SAXException | IOException ex){
            ex.printStackTrace();
            return null;
            
        }          
    }   
    
}
