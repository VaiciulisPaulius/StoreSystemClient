package lt.viko.eif.pvaiciulis.StoreSystemClient.utils;

import java.io.*;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

public class JaxbUtilGeneric {
    public static <T> void convertToXML(T obj, String path) {
        try {
            JAXBContext context = JAXBContext.newInstance(new Class[]{obj.getClass()});
            Marshaller marshaller = null;
            marshaller = context.createMarshaller();
            marshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
            OutputStream os = new FileOutputStream(path);
            marshaller.marshal(obj, os);
        } catch (JAXBException | FileNotFoundException var4) {
            System.out.println(var4.getMessage());
        }
    }

    public static <T> T convertToPOJO(Class<T> obj, String path) {
        try {
            JAXBContext context = JAXBContext.newInstance(obj);
            Unmarshaller unmarshaller = null;
            File file = new File(path);

            if(!file.exists()) file.createNewFile();

            unmarshaller = context.createUnmarshaller();
            T instance = (T) unmarshaller.unmarshal(file);
            return instance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
