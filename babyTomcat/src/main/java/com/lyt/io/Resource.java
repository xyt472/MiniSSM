package com.lyt.io;

import java.io.InputStream;
import java.io.Reader;

public class Resource {

        public static InputStream getResourceAsStream(String location){
            return Resource.class.getClassLoader().getResourceAsStream(location);
        }



        public static Reader getResourceReader(String location){
            return  null;
        }

}
