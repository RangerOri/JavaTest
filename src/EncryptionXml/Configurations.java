package EncryptionXml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Ori on 29/09/2015.
 */

@XmlRootElement(name="configurations")
public class Configurations {

    private String operation     = null;
    private String sourceDir     = null;
    private String destPath      = null;
    private String sourceFile    = null;
    private String algorithm     = null;
    private String keySourceFile = null;
    private String keyDestPath   = null;

    @XmlElement
    public void setOperation(String operation) {
        this.operation = operation;
    }

    @XmlElement
    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    @XmlElement
    public void setSourceDir(String sourceDir) {
        this.sourceDir = sourceDir;
    }

    @XmlElement
    public void setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    @XmlElement
    public void setKeySourceFile(String keySourcePath) {
        this.keySourceFile = keySourcePath;
    }

    @XmlElement
    public void setDestPath(String destPath) {
        this.destPath = destPath;
    }

    @XmlElement
    public void setKeyDestPath(String keyDestPath) {
        this.keyDestPath = keyDestPath;
    }

    public String getOperation() {
        return operation;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public String getSourceDir() {
        return sourceDir;
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public String getKeyDestPath() {
        return keyDestPath;
    }

    public String getKeySourceFile() {
        return keySourceFile;
    }

    public String getDestPath() {
        return destPath;
    }

}