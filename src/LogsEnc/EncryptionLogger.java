package LogsEnc;

import java.util.Observable;
import java.util.Observer;

import org.apache.log4j.Logger;

/**
 * Created by Ori on 27/09/2015.
 */
public class EncryptionLogger implements Observer {


    private static Logger log = Logger.getLogger(EncryptionLogger.class);
    private Long timeInitiated;
    private Long timeEnded;

    @Override
    public void update(Observable o, Object arg) {
        try{
            LogStruct logObj = (LogStruct) arg;
            String message = "";
            switch(logObj.state){
                case ENC_STARTED:
                    timeInitiated = System.currentTimeMillis();
                    message = "Algorithm "+logObj.algorithm +" Encryption started for file: "+logObj.file;
                    break;
                case ENC_ENDED:
                    timeEnded = System.currentTimeMillis();
                    message = "Algorithm "+logObj.algorithm +" Encryption ended for file: "+ logObj.file+", Time: "+(timeEnded-timeInitiated)+"ms";
                    break;
                case DEC_STARTED:
                    timeInitiated = System.currentTimeMillis();
                    message = "Algorithm "+logObj.algorithm +" Decryption started for file: "+logObj.file;
                    break;
                case DEC_ENDED:
                    timeEnded = System.currentTimeMillis();
                    message = "Algorithm "+logObj.algorithm +" Decryption ended for file: "+ logObj.file+", Time: "+(timeEnded-timeInitiated)+"ms";
                    break;
            }

            //System.out.println(message);
            logInfo(0, message);

        } catch (Exception e){
            logInfo(1, e.getMessage());
        }
    }

    public static synchronized void logInfo(int type, String msg){
        switch(type){
            case 0: //info
                log.info(msg);
                break;
            case 1: //fatal
                log.fatal(msg);
                break;
        }
    }
}
