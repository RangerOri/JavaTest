package Exceptions;

/**
 * Created by Ori on 26/09/2015.
 */
public class InvalidPathException extends Exception {

    private String error;

    public InvalidPathException(String err){
        error = err;
    }

    public String getPathError(){
        return error;
    }
}
