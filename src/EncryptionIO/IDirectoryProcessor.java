package EncryptionIO;

import Algorithms.EncryptionAlgorithm;

/**
 * Created by Ori on 14/10/2015.
 */
public interface IDirectoryProcessor {

    public void encryptDir(String dir, EncryptionAlgorithm algorithm);
    public void decryptDir(String dir, EncryptionAlgorithm algorithm);
}
