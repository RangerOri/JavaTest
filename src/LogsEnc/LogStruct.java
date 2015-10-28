package LogsEnc;

/**
 * Created by Ori on 27/09/2015.
 */

public class LogStruct {
    public static enum ENC_STATE {ENC_STARTED, DEC_STARTED, ENC_ENDED, DEC_ENDED}
    public ENC_STATE state;
    public String algorithm;
    public String file;

    public LogStruct(ENC_STATE state, String algorithm, String file) {
        this.state = state;
        this.algorithm = algorithm;
        this.file = file;
    }
}