package Algorithms;

import Exceptions.InvalidEncryptionKeyException;

import java.util.Comparator;
import java.util.Random;

/**
 * Created by Ori on 20/09/2015.
 */
public class BaseAlgorithm implements Comparator<BaseAlgorithm> {

    private int key;
    private int random_max;
    protected enum OP_TYPE {ADD, SUBTRACT, MULTIPLY, DIVIDE, XOR}

    public int[] encrypt(int[] array, int key, OP_TYPE type) throws InvalidEncryptionKeyException {
        //setChanged();
        //notifyObservers(ENC_STATE.ENC_STARTED);
        int[] result = encrypt_decrypt(array, key, type);
        //setChanged();
        //notifyObservers(ENC_STATE.ENC_ENDED);
        return result;
    }

    public int[] decrypt(int[] array, int key, OP_TYPE type) throws InvalidEncryptionKeyException {
        //setChanged();
        //notifyObservers(ENC_STATE.DEC_STARTED);
        int[] result = encrypt_decrypt(array, key, type);
        //setChanged();
        //notifyObservers(ENC_STATE.DEC_ENDED);
        return result;
    }

    private int[] encrypt_decrypt(int[] array, int key, OP_TYPE type) throws InvalidEncryptionKeyException {

        if(key == 0)
            throw new InvalidEncryptionKeyException("Key can't be 0!");

        int[] toArray = new int[array.length];


        for(int i = 0; i<array.length; i++) {
            if(array[i] != 0)
                switch(type){
                    case ADD:
                        toArray[i] = array[i] + key;
                        break;
                    case MULTIPLY:
                        toArray[i] = array[i] * key;
                        break;
                    case SUBTRACT:
                        toArray[i] = array[i] - key;
                        break;
                    case DIVIDE:
                        toArray[i] = array[i] / key;
                        break;
                    case XOR:
                        toArray[i] = array[i] ^ key;
                        break;
                }
        }

        return toArray;
    }

    private int[] encrypt_decrypt_unused(int[] array, int key, OP_TYPE type) throws InvalidEncryptionKeyException {

        if(key == 0)
            throw new InvalidEncryptionKeyException("Key can't be 0!");

        int[] toArray = new int[array.length];


        for(int i = 0; i<array.length; i++) {
            if(array[i] != 0)
                switch(type){
                    case ADD:
                        toArray[i] = AddModulus(array[i], key, random_max);
                        break;
                    case MULTIPLY:
                        toArray[i] = MultModulus(array[i], key, random_max);
                        break;
                    case SUBTRACT:
                        toArray[i] = ReverseAddModulus(random_max, key, array[i]);
                        break;
                    case DIVIDE:
                        toArray[i] = ReverseMultModulus(random_max, key, array[i]);
                        break;
                    case XOR:
                        toArray[i] = array[i] ^ key;
                        break;
                }
        }

        return toArray;
    }

    private int AddModulus(int num, int key, int mod) throws InvalidEncryptionKeyException
    {
        int result = (num + key) % mod;
        return result;
    }

    private int ReverseAddModulus(int mod, int key, int remainder) throws InvalidEncryptionKeyException
    {
        if(remainder >= mod)
            throw new InvalidEncryptionKeyException("Remainder cannot be greater than or equal to divisor");
        if(key < remainder)
            return remainder - key;
        return mod + remainder - key;
    }

    private int MultModulus(int num, int key, int mod) throws InvalidEncryptionKeyException
    {
        return (num * key) % mod;
    }

    private int ReverseMultModulus(int mod, int key, int result) throws InvalidEncryptionKeyException
    {
        for (int i=0;i<=mod;i++){
            if (MultModulus(i, key, mod) == result){
                return i;
            }
        }

        throw new InvalidEncryptionKeyException("Remainder cannot be greater than or equal to divisor");
    }

    public int getKey(){
        return key;
    }
    public void generateKey(int maxRandom){
        random_max = maxRandom;
        key = new Random().nextInt(random_max); //small number so it won't affect Integer size
        if(key < 0)
            key *= -1;
        else if(key == 0)
            key = 2;
    }
    public int getKeyStrength(){
        return String.valueOf(random_max).length();
    }

    @Override
    public int compare(BaseAlgorithm o1, BaseAlgorithm o2) {
        return o1.getKeyStrength() > o2.getKeyStrength() ? 1 :
                o1.getKeyStrength() < o2.getKeyStrength() ? -1 : 0;
    }

}
