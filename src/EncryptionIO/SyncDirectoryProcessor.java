package EncryptionIO;

import Algorithms.EncryptionAlgorithm;

/**
 * Created by Ori on 14/10/2015.
 */
public class SyncDirectoryProcessor extends DirectoryEncryptor implements IDirectoryProcessor {
    @Override
    public void encryptDir(String dir, EncryptionAlgorithm algorithm) {
        encryptDirSync(dir, algorithm);
    }

    @Override
    public void decryptDir(String dir, EncryptionAlgorithm algorithm) {
        decryptDirSync(dir, algorithm);
    }

}
