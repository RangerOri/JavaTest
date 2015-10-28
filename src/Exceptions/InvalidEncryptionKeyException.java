package Exceptions;

/**
 * Created by Ori on 26/09/2015.
 */
public class InvalidEncryptionKeyException extends Exception {

    private String error;

    public InvalidEncryptionKeyException(String err){
        error = err;
    }

    public String getKeyError(){
        return error;
    }
}
