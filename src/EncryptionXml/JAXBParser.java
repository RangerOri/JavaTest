package EncryptionXml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by Ori on 29/09/2015.
 */
public class JAXBParser extends XmlParser implements XmlParsing {

    public Configurations cfg;

    public JAXBParser(String path, String xsdPath){
        super(path, xsdPath);
        try {

            File file = new File(path);
            JAXBContext jaxbContext = JAXBContext.newInstance(Configurations.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            cfg = (Configurations) jaxbUnmarshaller.unmarshal(file);
            //System.out.println(cfg.getAlgorithm());

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void executeXmlFile() {
        if(!cfg.getOperation().isEmpty() && !cfg.getSourceDir().isEmpty() && cfg.getSourceFile().isEmpty() && cfg.getKeySourceFile().isEmpty())
            encrypt_decrypt_dir(cfg.getOperation(), cfg.getSourceDir(), parseAlgorithm(cfg.getAlgorithm()));
        else if(!cfg.getSourceFile().isEmpty() && !cfg.getDestPath().isEmpty() && cfg.getKeySourceFile().isEmpty() && cfg.getSourceDir().isEmpty())
            encrypt_file(cfg.getOperation(), cfg.getSourceFile(), cfg.getDestPath(), parseAlgorithm(cfg.getAlgorithm()));
        else if(!cfg.getSourceFile().isEmpty() && !cfg.getKeySourceFile().isEmpty())
            decrypt_file(cfg.getOperation(), cfg.getSourceFile(), cfg.getKeySourceFile(), parseAlgorithm(cfg.getAlgorithm()));
    }
}
