package EncryptionXml;

import Algorithms.*;
import Exceptions.*;
import EncryptionIO.FileEncryptor;
import EncryptionIO.IDirectoryProcessor;
import EncryptionIO.SyncDirectoryProcessor;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

/**
 * Created by Ori on 29/09/2015.
 */
class XmlParser extends DefaultHandler {

    protected boolean isValid;
    protected IDirectoryProcessor idp;


    protected XmlParser(String path, String xsdPath){
        isValid = validateXMLSchema(xsdPath,path);
        if(isValid){
            System.out.println(path + " is valid against " + xsdPath);
        } else {
            System.out.println(path + " is not valid against " + xsdPath);
        }

        idp = new SyncDirectoryProcessor();
    }

    protected EncryptionAlgorithm parseAlgorithm(String algorithm){

        EncryptionAlgorithm algorithmEnc = null;
        try {
            String pieces[] = null;
            if (algorithm.contains("-"))
                pieces = algorithm.split("-");

            if (pieces != null && pieces[0].equalsIgnoreCase("double"))
                algorithm = pieces[1];

            int times = 0;
            if (pieces != null && pieces[0].equalsIgnoreCase("repeat")) {
                times = Integer.parseInt(pieces[2]);
                algorithm = pieces[1];
                if (algorithm.equalsIgnoreCase("ShiftUp"))
                    algorithmEnc = new RepeatEncryption(times, new ShiftUpEncryption());
                else if (algorithm.equalsIgnoreCase("ShiftMultiply"))
                    algorithmEnc = new RepeatEncryption(times, new ShiftMultiplyEncryption());
                else if (algorithm.equalsIgnoreCase("Xor"))
                    algorithmEnc = new RepeatEncryption(times, new XorEncryption());
            }

            if (algorithm.equalsIgnoreCase("ShiftUp"))
                algorithmEnc = new ShiftUpEncryption();
            else if (algorithm.equalsIgnoreCase("ShiftMultiply"))
                algorithmEnc = new ShiftMultiplyEncryption();
            else if (algorithm.equalsIgnoreCase("Xor"))
                algorithmEnc = new XorEncryption();

        } catch (Exception e){
            e.printStackTrace();
        }

        return algorithmEnc;
    }

    protected void encrypt_decrypt_dir(String operation, String sourceDir, EncryptionAlgorithm algorithm){
        if (isValid && !operation.isEmpty() && !sourceDir.isEmpty() && algorithm != null) //Directory encryption-decryption
        if(operation.equals("enc-dir"))
            idp.encryptDir(sourceDir, algorithm);
        else if (operation.equals("dec-dir"))
            idp.decryptDir(sourceDir, algorithm);
        else
            System.out.println("Cant perform action on directory, something is wrong");
    }

    protected void encrypt_file(String operation, String sourceFile, String destPath, EncryptionAlgorithm algorithm){
        if (isValid && !operation.isEmpty() && !sourceFile.isEmpty() && !destPath.isEmpty() && algorithm != null) //File encryption
            try {
                new FileEncryptor(algorithm).encryptFile(sourceFile, destPath);
            } catch (InvalidEncryptionKeyException e) {
                e.printStackTrace();
            } catch (InvalidPathException e) {
                e.printStackTrace();
            }
        else
            System.out.println("Cant perform action on file, something is wrong");
    }

    protected void decrypt_file(String operation, String sourceFile, String keySourceFile, EncryptionAlgorithm algorithm){
        if (isValid && !operation.isEmpty() && !keySourceFile.isEmpty() && algorithm != null) //File decryption
            try{
                new FileEncryptor(algorithm).decryptFile(sourceFile, keySourceFile);
            } catch (InvalidEncryptionKeyException e) {
                e.printStackTrace();
            } catch (InvalidPathException e) {
                e.printStackTrace();
            }
        else
            System.out.println("Cant perform action on file, something is wrong");
    }

    private boolean validateXMLSchema(String xsdPath, String xmlPath){
        try {
            SchemaFactory factory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
        } catch (IOException e){
            System.out.println("Exception: "+e.getMessage());
            return false;
        }catch(SAXException e1){
            System.out.println("SAX Exception: "+e1.getMessage());
            return false;
        }
        return true;
    }
}
