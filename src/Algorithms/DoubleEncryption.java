package Algorithms;

import Exceptions.InvalidEncryptionKeyException;

/**
 * Created by Ori on 20/09/2015.
 */
public class DoubleEncryption {

    private EncryptionAlgorithm algorithmA;
    private EncryptionAlgorithm algorithmB;

    public DoubleEncryption(EncryptionAlgorithm a, EncryptionAlgorithm b){
        algorithmA = a;
        algorithmB = b;
    }

    public int[] encrypt(int[] array) throws InvalidEncryptionKeyException {
        int[] result = algorithmA.encrypt(array);
        result = algorithmB.encrypt(result);

        return result;
    }
    public int[] decrypt(int[] array, int keyFileA, int keyFileB) throws InvalidEncryptionKeyException {
        int[] result = algorithmB.decrypt(array, keyFileB);
        result = algorithmA.decrypt(result, keyFileA);

        return result;
    }

    public int getKeyA() { return algorithmA.getKey(); }
    public int getKeyB() { return algorithmB.getKey(); }
}
