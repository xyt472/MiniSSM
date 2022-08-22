package com.lyt.servlet;

import java.io.CharConversionException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.ResourceBundle;

public abstract class ServletOutputStream extends OutputStream {
    private static final String LSTRING_FILE = "jakarta.servlet.LocalStrings";
    private static final ResourceBundle lStrings = ResourceBundle.getBundle(LSTRING_FILE);

    protected ServletOutputStream() {
        // NOOP
    }

    //抄源码的
    public void print(String s) throws IOException {
        if (s == null) {
            s = "null";
        }
        int len = s.length();
        byte[] buffer = new byte[len];

        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);

            //
            // XXX NOTE: This is clearly incorrect for many strings,
            // but is the only consistent approach within the current
            // servlet framework. It must suffice until servlet output
            // streams properly encode their output.
            //
            if ((c & 0xff00) != 0) { // high order byte must be zero
                String errMsg = lStrings.getString("err.not_iso8859_1");
                Object[] errArgs = new Object[1];
                errArgs[0] = Character.valueOf(c);
                errMsg = MessageFormat.format(errMsg, errArgs);
                throw new CharConversionException(errMsg);
            }
            buffer[i] = (byte) (c & 0xFF);
        }
        write(buffer);
    }

    //这也是抄源码的
    public void println(String s) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(s);
        sb.append("\r\n");
        print(sb.toString());
    }
}
