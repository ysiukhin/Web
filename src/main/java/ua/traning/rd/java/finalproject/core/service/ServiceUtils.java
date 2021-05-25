package ua.traning.rd.java.finalproject.core.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ServiceUtils {

    public final static Logger LOGGER = LogManager.getLogger(ServiceUtils.class);

    public static String getMd5(String input) {
        MessageDigest messageDigest;
        byte[] bytesEncoded;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(input.getBytes(StandardCharsets.UTF_8));
            bytesEncoded = messageDigest.digest();
            return new BigInteger(1, bytesEncoded).toString(16);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("UtilsService error: {}", e.getMessage(), e);
        }
        return null;
    }
}
