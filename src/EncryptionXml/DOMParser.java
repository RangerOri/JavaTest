package EncryptionXml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.*;
import java.io.*;

/**
 * Created by Ori on 29/09/2015.
 */
public class DOMParser extends XmlParser implements XmlParsing {

    public String operation     = null;
    public String sourceDir     = null;
    public String destPath      = null;
    public String sourceFile    = null;
    public String algorithm     = null;
    public String keySourceFile = null;
    public String keyDestPath   = null;

    private Element eElement;

    public DOMParser(String path, String xsdPath){
        super(path, xsdPath);
        try {
            File inputFile = new File(path);
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element: "
                    + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("configurations");
            System.out.println("----------------------------");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("Current Element: "
                        + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    eElement = (Element) nNode;
                    operation = getElementData("operation");
                    System.out.println("Operation: " + operation);
                    algorithm = getElementData("algorithm");
                    System.out.println("Algorithm: " + algorithm);
                    sourceDir = getElementData("sourceDir");
                    System.out.println("SourceDir: " + sourceDir);
                    sourceFile = getElementData("sourceFile");
                    System.out.println("SourceFile: " + sourceFile);
                    keySourceFile = getElementData("keySourceFile");
                    System.out.println("KeySourceFile: " + keySourceFile);
                    destPath = getElementData("destPath");
                    System.out.println("DestPath: " + destPath);
                    keyDestPath = getElementData("keyDestPath");
                    System.out.println("KeyDestPath: " + keyDestPath);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getElementData(String tag) {
        return eElement.getElementsByTagName(tag)
                .item(0)
                .getTextContent();
    }

    @Override
    public void executeXmlFile() {
        if(!operation.isEmpty() && !sourceDir.isEmpty() && sourceFile.isEmpty() && keySourceFile.isEmpty())
            encrypt_decrypt_dir(operation, sourceDir, parseAlgorithm(algorithm));
        else if(!sourceFile.isEmpty() && !destPath.isEmpty() && keySourceFile.isEmpty() && sourceDir.isEmpty())
            encrypt_file(operation, sourceFile, destPath, parseAlgorithm(algorithm));
        else if(!sourceFile.isEmpty() && !keySourceFile.isEmpty())
            decrypt_file(operation, sourceFile, keySourceFile, parseAlgorithm(algorithm));
    }
}
