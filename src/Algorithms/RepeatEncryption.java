package Algorithms;

import Exceptions.InvalidEncryptionKeyException;

/**
 * Created by Ori on 24/09/2015.
 */
public class RepeatEncryption implements EncryptionAlgorithm<Integer> {

    private int times;
    private EncryptionAlgorithm algorithm;

    /**
     * When assigning a times, keep in mind that the times shouldn't be too high, 2^32 is the maxmimum encryption value
     * @param times
     * @param algorithm
     */
    public RepeatEncryption(int times, EncryptionAlgorithm algorithm) {
        this.times = times;
        this.algorithm = algorithm;
    }

    @Override
    public int[] encrypt(int[] array) throws InvalidEncryptionKeyException {
        for(int i = 0; i<times; i++)
            array = algorithm.encrypt(array);

        return array;
    }

    @Override
    public int[] decrypt(int[] array, Integer key) throws InvalidEncryptionKeyException {
        for(int i = 0; i<times; i++)
            array = algorithm.decrypt(array, key);

        return array;
    }

    public int getKey(){
       return algorithm.getKey();
    }
    public int getKeyStrength() {
        return algorithm.getKeyStrength();
    }
}
