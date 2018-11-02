package es.unex.asee.proyectoasee.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

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

}
