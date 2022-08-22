package com.lyt.BabyBatisFramework.Utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

public class DocumentUtils {
    public  static Document getDocument(InputStream inputStream){


        try {
            SAXReader reader =new SAXReader();
            Document document=reader.read(inputStream);
            return  document;
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return null;
    }


}
