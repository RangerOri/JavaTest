package EncryptionIO;

import Algorithms.EncryptionAlgorithm;

/**
 * Created by Ori on 14/10/2015.
 */
public class AsyncDirectoryProcessor extends DirectoryEncryptor implements IDirectoryProcessor {
    @Override
    public void encryptDir(String dir, EncryptionAlgorithm algorithm) {
        encryptDirAsync(dir, algorithm);
    }

    @Override
    public void decryptDir(String dir, EncryptionAlgorithm algorithm) {
        decryptDirAsync(dir, algorithm);
    }

}
