/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.entidades;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 *
 * @author rafacampa9
 */
public class XPathStore {
    
    /**
     * A este método le pasamos por parámetro un 
     * documento y la expresión XPATH y nos devuelve
     * la lista de nodos
     * 
     * @param doc
     * @param expresion
     * @return 
     */
    public NodeList listaNodos (Document doc, String expresion) {
        try{
            XPath xpath = XPathFactory.newInstance().newXPath();
            NodeList nodeList = (NodeList) xpath.compile(expresion).
                    evaluate(
                            doc, 
                            XPathConstants.NODESET
            );
            return nodeList;
        } catch(XPathExpressionException e){
            e.printStackTrace();
            return null;
        }    
            
    }
}
