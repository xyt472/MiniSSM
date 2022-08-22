package com.lyt;

import com.lyt.io.Resource;
import org.junit.Test;

import javax.sound.midi.Soundbank;
import java.io.*;

public class readTest {

    @Test
    public void read()throws Exception{

        //必须在resource目录下才可以
        InputStream inputStream= Resource.getResourceAsStream("a.txt");

         //String bytes= inputStream.readAllBytes();
        if(inputStream!=null){
            System.out.println("文件找到了");
           // System.out.println(bytes);
        }

        OutputStream outputStream=new FileOutputStream("a.txt");

        outputStream.write("sdretewtew".getBytes());
        outputStream.write("sdretewtew2".getBytes());
        outputStream.write("sdretewtew3".getBytes());


    }
}
