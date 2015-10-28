package EncryptionIO;

import Algorithms.*;
import Exceptions.*;
import LogsEnc.EncryptionLogger;

import java.io.File;

/**
 * Created by Ori on 27/09/2015.
 */
public class DirectoryEncryptor {

    protected final String DIR_NAME = "encrypted_files";

    public DirectoryEncryptor(){
    }

    /**
     * Encryption takes longer than decryption, because for every byte, you write 4 bytes,
     * so for 4mb, your write 16mb, and it takes more time for the hard disk to write it.*/


    void encryptDirSync(String dir, EncryptionAlgorithm algorithm){
        File[] files = FileIO.getTextFilesInDir(dir);

        //create new directory
        String dirPath = dir+"\\"+ DIR_NAME;
        if(!new File(dirPath).exists())
            new File(dirPath).mkdir();

        long currentTime = System.currentTimeMillis();

        for(final File f : files){
            final FileEncryptor fileEncryptor = new FileEncryptor(algorithm);
            final String filePath = f.getPath();
            try {
                fileEncryptor.encryptFile(filePath, dirPath);
            } catch (InvalidEncryptionKeyException e) {
                e.printStackTrace();
            } catch (InvalidPathException e) {
                e.printStackTrace();
            }
        }

        long finalTime = System.currentTimeMillis() - currentTime;
        EncryptionLogger.logInfo(0, "Final duration time for encryption: " + finalTime + "ms");
    }

    void decryptDirSync(String dir, EncryptionAlgorithm algorithm){
        File[] files = FileIO.getTextFilesInDir(dir);

        long currentTime = System.currentTimeMillis();

        for(final File f : files){
            final FileEncryptor fileEncryptor = new FileEncryptor(algorithm);
            final String filePath = f.getPath();
            final String keyPath = filePath.replace(FileIO.getFileName(filePath), "key.txt");
            try {
                if(!filePath.equals(keyPath)) //Skip key.txt decryption
                    fileEncryptor.decryptFile(filePath, keyPath);
            } catch (InvalidEncryptionKeyException e) {
                e.printStackTrace();
            } catch (InvalidPathException e) {
                e.printStackTrace();
            }
        }

        long finalTime = System.currentTimeMillis() - currentTime;
        EncryptionLogger.logInfo(0, "Final duration time for encryption: " + finalTime + "ms");
    }

    void encryptDirAsync(String dir, EncryptionAlgorithm algorithm){
        File[] files = FileIO.getTextFilesInDir(dir);

        //create new directory
        final String dirPath = dir+"\\"+ DIR_NAME;
        if(!new File(dirPath).exists())
            new File(dirPath).mkdir();

        for(final File f : files){
            final FileEncryptor fileEncryptor = new FileEncryptor(algorithm);
            final String filePath = f.getPath();
            new Thread(new Runnable(){

                @Override
                public void run() {
                    try {
                        fileEncryptor.encryptFile(filePath, dirPath);
                    } catch (InvalidEncryptionKeyException e) {
                        e.printStackTrace();
                    } catch (InvalidPathException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    void decryptDirAsync(String dir, EncryptionAlgorithm algorithm){
        File[] files = FileIO.getTextFilesInDir(dir);
        for(final File f : files){
            final FileEncryptor fileEncryptor = new FileEncryptor(algorithm);
            final String filePath = f.getPath();
            final String keyPath = filePath.replace(FileIO.getFileName(filePath), "key.txt");
            new Thread(new Runnable(){

                @Override
                public void run() {
                    try {
                        if(!filePath.equals(keyPath)) //Skip key.txt decryption
                            fileEncryptor.decryptFile(filePath, keyPath);
                    } catch (InvalidEncryptionKeyException e) {
                        e.printStackTrace();
                    } catch (InvalidPathException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
