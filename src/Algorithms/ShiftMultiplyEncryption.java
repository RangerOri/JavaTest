package Algorithms;

import Exceptions.InvalidEncryptionKeyException;

/**
 * Created by Ori on 20/09/2015.
 */
public class ShiftMultiplyEncryption extends BaseAlgorithm implements EncryptionAlgorithm<Integer> {

    public ShiftMultiplyEncryption(){
        generateKey(Byte.MAX_VALUE);
    }

    @Override
    public int[] encrypt(int[] toEncrypt) throws InvalidEncryptionKeyException {
        return super.encrypt(toEncrypt, getKey(), OP_TYPE.MULTIPLY);
    }

    @Override
    public int[] decrypt(int[] toDecrypt, Integer key) throws InvalidEncryptionKeyException {
        return super.decrypt(toDecrypt, key, OP_TYPE.DIVIDE);
    }

}
