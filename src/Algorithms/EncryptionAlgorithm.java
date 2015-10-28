package Algorithms;

import Exceptions.InvalidEncryptionKeyException;

/**
 * Created by Ori on 20/09/2015.
 */
public interface EncryptionAlgorithm<T>{

    public int[] encrypt(int[] toEncrypt) throws InvalidEncryptionKeyException, InvalidEncryptionKeyException;
    public int[] decrypt(int[] toDecrypt, T key) throws InvalidEncryptionKeyException;
    public int getKey();
    public int getKeyStrength();
}
