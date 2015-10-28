package Algorithms;

import Exceptions.InvalidEncryptionKeyException;

/**
 * Created by Ori on 24/09/2015.
 */
public class XorEncryption extends BaseAlgorithm implements EncryptionAlgorithm<Integer> {

    public XorEncryption(){
        generateKey(Integer.MAX_VALUE);
    }

    @Override
    public int[] encrypt(int[] toEncrypt) throws InvalidEncryptionKeyException {
        return encrypt(toEncrypt, getKey(), OP_TYPE.XOR);
    }

    @Override
    public int[] decrypt(int[] toDecrypt, Integer key) throws InvalidEncryptionKeyException {
        return decrypt(toDecrypt, key, OP_TYPE.XOR);
    }
}
