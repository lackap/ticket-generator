package com.forum.ticketgenerator.utils;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.io.FileUtils;
import org.mozilla.universalchardet.UniversalDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class EncodingUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(EncodingUtils.class);

    public static String getEncodingToUse(String file) throws IOException {
        UniversalDetector ud = new UniversalDetector(null);
        byte[] bytes = FileUtils.readFileToByteArray(new File(file));
        ud.handleData(bytes, 0, bytes.length); ud.dataEnd();
        String charset = CharEncoding.UTF_8;
        if (ud.getDetectedCharset().equals("WINDOWS-1252")) {
            charset = "Cp1252";
        }
        LOGGER.info(file + " charset : " + ud.getDetectedCharset());
        return charset;
    }
}
