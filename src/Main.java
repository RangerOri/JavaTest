import Algorithms.DoubleEncryption;
import Algorithms.EncryptionAlgorithm;
import Algorithms.ShiftUpEncryption;
import EncryptionIO.FileEncryptor;
import EncryptionIO.SyncDirectoryProcessor;
import Exceptions.*;
import EncryptionIO.FileIO;
import EncryptionXml.DOMParser;
import EncryptionXml.XmlParsing;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.util.Scanner;


/**
 * Created by Ori on 20/09/2015.
 */
public class Main {

    private static final String XML_PATH_ENC = "encryption\\config_enc.xml";
    private static final String XML_PATH_DEC = "encryption\\config_dec.xml";
    private static final String XML_SINGLE_PATH_ENC = "encryption\\config_file_enc.xml";
    private static final String XML_SINGLE_PATH_DEC = "encryption\\config_file_dec.xml";
    private static final String XSD_PATH = "encryption\\config.xsd";


    public static void main(String[] args){
        testing();
        XmlParsing parser = new DOMParser(XML_PATH_DEC, XSD_PATH);
        parser.executeXmlFile();

        //getUserInput(0);

        //pause();
    }

    static void validateXSDFilePath(String[] args){
        if(args.length !=2){
            System.out.println("Usage : XSDValidator <file-name.xsd> <file-name.xml>");
        }else{

        }
    }

    static void testing(){
        Result result = JUnitCore.runClasses(UnitTesting.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println("Tests results successful: "+result.wasSuccessful());
    }

    static void pause(){
        System.out.println("Press Enter To Continue...");
        new java.util.Scanner(System.in).nextLine();
    }

    static void getUserInput(int type) { //0 - encrypt file, 1 - encrypt dir
        Scanner in = new Scanner(System.in);
        String info = "In order to use Encryption enter 'enc', " +
                "for Decryption enter 'dec', to exit enter 'e':";
        System.out.println(info);
        String input = in.nextLine();
        EncryptionAlgorithm algorithm = new ShiftUpEncryption();
        DoubleEncryption doubleAlgo = new DoubleEncryption(new ShiftUpEncryption(), new ShiftUpEncryption());

        while (!input.equals("e")) {
            String result = info;
        try {
            if (input.equals("enc")) {
                input = validatePath(type);
                if (type == 0)
                    new FileEncryptor(doubleAlgo).doubleProgramEncrypt(input);
                else
                    new SyncDirectoryProcessor().encryptDir(input, algorithm);
            } else if (input.equals("dec")) {
                input = validatePath(type);
                if (type == 0) {
                    String keyDestPath = input.replace(FileIO.getFileName(input), "");
                    new FileEncryptor(doubleAlgo).doubleProgramDecrypt(input,
                            keyDestPath + "keya.txt", keyDestPath + "keyb.txt");
                }
                else
                    new SyncDirectoryProcessor().decryptDir(input, algorithm);
            } else
                result = "Wrong input, " + result;
        } catch(InvalidPathException e){
            System.out.println(e.getPathError());

        } catch (InvalidEncryptionKeyException e){
            System.out.println(e.getKeyError());
        }
            System.out.println(result);
            input = in.nextLine();
        }
    }

    static String validatePath(int type){
        Scanner in = new Scanner(System.in);
        String info = type == 0 ? "file" : "directory";
        System.out.println("Enter "+info+" path:");
        String input = in.nextLine();

        try {
            if(type == 0)
                FileIO.validateFilePath(input);
            else
                FileIO.validateDirectoryPath(input);
        } catch (InvalidPathException exc){
            System.out.println(exc.getPathError());
            input = validatePath(type);
        }
        return input;
    }
}
