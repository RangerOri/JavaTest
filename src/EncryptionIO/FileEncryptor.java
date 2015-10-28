package EncryptionIO;

import Algorithms.*;
import Exceptions.*;
import LogsEnc.*;

import java.util.Observable;

/**
 * Created by Ori on 20/09/2015.
 */
public class FileEncryptor extends Observable {

    private EncryptionAlgorithm algorithm;
    private DoubleEncryption doubleAlgorithm;
    private final String ENCRYPT_EXTENSION = "_encrypted.";
    private final String DECRYPT_EXTENSION = "_decrypted.";

    public FileEncryptor(EncryptionAlgorithm algo) {
        this.addObserver(new EncryptionLogger());
        algorithm = algo;
    }

    public FileEncryptor(DoubleEncryption doubleAlgo){
        this.addObserver(new EncryptionLogger());
        doubleAlgorithm = doubleAlgo;
    }

    private void notifyObserversInfo(LogStruct.ENC_STATE state, String fileName){
        setChanged();
        notifyObservers(new LogStruct(state, algorithm.getClass().getSimpleName(), fileName));
    }

    public void doubleProgramEncrypt(String path) throws InvalidEncryptionKeyException, InvalidPathException {
        String filePath, destPath, keyDestPath, fileName, originFileName;
        filePath = path;//validateFilePath(input);
        originFileName = FileIO.getFileName(filePath);
        fileName = FileIO.getFileName(filePath);

        if (!originFileName.contains(ENCRYPT_EXTENSION)) //for multiple uses on same file
            fileName = originFileName.replace(".", ENCRYPT_EXTENSION);

        destPath = filePath.replace(originFileName, "") + fileName;
        keyDestPath = filePath.replace(FileIO.getFileName(filePath), "");

        int[] result = doubleAlgorithm.encrypt(FileIO.readFromFile(filePath));
        FileIO.writeKeyFile(doubleAlgorithm.getKeyA(), keyDestPath + "keyA.txt");
        FileIO.writeKeyFile(doubleAlgorithm.getKeyB(), keyDestPath + "keyB.txt");
        FileIO.writeToEncryptedFile(result, destPath);

    }

    public void doubleProgramDecrypt(String filePath, String keyFilePath, String keyFilePathB) throws InvalidEncryptionKeyException, InvalidPathException {
        String destPath, fileName, originFileName;

        originFileName = FileIO.getFileName(filePath);
        fileName = FileIO.getFileName(filePath);

        if (!originFileName.contains(DECRYPT_EXTENSION)) //for multiple uses on same file
            fileName = originFileName.replace(".", DECRYPT_EXTENSION);

        destPath = filePath.replace(originFileName, "") + fileName;

        int[] result = doubleAlgorithm.decrypt(FileIO.readFromEncryptedFile(filePath),
                FileIO.readFileKey(keyFilePath), FileIO.readFileKey(keyFilePathB));
        FileIO.writeToFile(result, destPath);
    }

    public void encryptFile(String filePath, String dirName)  throws InvalidEncryptionKeyException, InvalidPathException {
        String keyDestPath, fileName, originFileName, destPath;

        FileIO.validateFilePath(filePath);

        originFileName = FileIO.getFileName(filePath);
        fileName = FileIO.getFileName(filePath);

        notifyObserversInfo(LogStruct.ENC_STATE.ENC_STARTED, fileName);

        if (!originFileName.contains(ENCRYPT_EXTENSION)) //for multiple uses on same file
            fileName = originFileName.replace(".", ENCRYPT_EXTENSION);

        destPath = filePath.replace(filePath, dirName +"\\"+fileName);
        keyDestPath = filePath.replace(filePath, dirName +"\\key.txt");

        int[] result = FileIO.readFromFile(filePath);
        result = algorithm.encrypt(result);

        FileIO.writeToEncryptedFile(result, destPath);
        //FileIO.writeToFile(result, destPath);
        FileIO.writeKeyFile(algorithm.getKey(), keyDestPath);

        notifyObserversInfo(LogStruct.ENC_STATE.ENC_ENDED, fileName);
    }

    public void decryptFile(String filePath, String keyFilePath)  throws InvalidEncryptionKeyException, InvalidPathException {
        String fileName, originFileName, destPath;

        FileIO.validateFilePath(filePath);
        FileIO.validateFilePath(keyFilePath);

        originFileName = FileIO.getFileName(filePath);
        fileName = FileIO.getFileName(filePath);

        notifyObserversInfo(LogStruct.ENC_STATE.DEC_STARTED, fileName);

        if (!originFileName.contains(DECRYPT_EXTENSION)) //for multiple uses on same file
            fileName = originFileName.replace(".", DECRYPT_EXTENSION);

        destPath = filePath.replace(originFileName, fileName);

        int[] result = algorithm.decrypt(FileIO.readFromEncryptedFile(filePath), FileIO.readFileKey(keyFilePath));
        //int[] result = algorithm.decrypt(FileIO.readFromFile(filePath), FileIO.readFileKey(keyFilePath));
        FileIO.writeToFile(result, destPath);

        notifyObserversInfo(LogStruct.ENC_STATE.DEC_ENDED, fileName);
    }
}
