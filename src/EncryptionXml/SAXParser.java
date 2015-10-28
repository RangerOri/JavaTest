package EncryptionXml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

/**
 * Created by Ori on 29/09/2015.
 */
public class SAXParser extends XmlParser implements XmlParsing{ //Default Handler is extended in XmlParser

    public String operation = null;
    public String sourceDir = null;
    public String destPath = null;
    public String sourceFile = null;
    public String algorithm = null;
    public String keySourceFile = null;
    public String keyDestPath = null;

    public SAXParser(String path, String xsdPath){
        super(path, xsdPath);
        try {
            File inputFile = new File(path);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            javax.xml.parsers.SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(inputFile, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startElement(String uri,
                             String localName, String qName, Attributes attributes)
            throws SAXException {
        if (qName.equalsIgnoreCase("configurations")) {
            System.out.println("Start Element: " + qName);
        } else if (qName.equalsIgnoreCase("operation")) {
            operation = "V";
        } else if (qName.equalsIgnoreCase("algorithm")) {
            algorithm = "V";
        } else if (qName.equalsIgnoreCase("SourceDir")) {
            sourceDir = "V";
        } else if (qName.equalsIgnoreCase("SourceFile")) {
            sourceFile = "V";
        } else if (qName.equalsIgnoreCase("KeySourceFile")) {
            keySourceFile = "V";
        } else if (qName.equalsIgnoreCase("DestPath")) {
            destPath = "V";
        } else if (qName.equalsIgnoreCase("KeyDestPath")) {
            keyDestPath = "V";
        }
    }

    @Override
    public void endElement(String uri,
                           String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("configurations")) {
            System.out.println("End Element: " + qName);
        }
    }

    @Override
    public void characters(char ch[],
                           int start, int length) throws SAXException {
        if (algorithm != null && algorithm.equals("V")) {
            System.out.println("Algorithm: " + new String(ch, start, length));
            algorithm = new String(ch, start, length);
        } else if (operation != null && operation.equals("V")) {
            System.out.println("Operation: " + new String(ch, start, length));
            operation = new String(ch, start, length);
        } else if (sourceDir != null && sourceDir.equals("V")) {
            System.out.println("Source Dir: " + new String(ch, start, length));
            sourceDir = new String(ch, start, length);
        } else if (sourceFile != null && sourceFile.equals("V")) {
            System.out.println("Source File: " + new String(ch, start, length));
            sourceFile = new String(ch, start, length);
        } else if (keySourceFile != null && keySourceFile.equals("V")) {
            System.out.println("Key Source Path: " + new String(ch, start, length));
            keySourceFile = new String(ch, start, length);
        } else if (destPath != null && destPath.equals("V")) {
            System.out.println("Dest Path: " + new String(ch, start, length));
            destPath = new String(ch, start, length);
        } else if (keyDestPath != null && keyDestPath.equals("V")) {
            System.out.println("Key Dest Path: " + new String(ch, start, length));
            keyDestPath = new String(ch, start, length);
        }
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
