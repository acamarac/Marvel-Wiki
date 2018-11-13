package es.unex.asee.proyectoasee.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

    public static final String apiKey = "8930b8251773dc6334474b306aaaa6b6";
    public static final String privateKey = "a6fd8f30a718e8f8f2e8f462ef36a46ee94f9309";

    /**
     * Método que permite calcular el md5 de un string.
     * Será utilizado para calcular el hash que debe pasarse por parámetro en la url
     * @param s String del que calcular el hash
     * @return String obtenido tras la realización del algoritmo md5
     */
    public static String MD5_Hash(String s) {
        MessageDigest m = null;

        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        m.update(s.getBytes(),0,s.length());
        String hash = new BigInteger(1, m.digest()).toString(16);
        return hash;
    }

    public static Integer getResourceId(String resource) {
        int index = resource.lastIndexOf('/');
        String result = resource.substring(index+1, resource.length());
        return Integer.parseInt(result);
    }

}
