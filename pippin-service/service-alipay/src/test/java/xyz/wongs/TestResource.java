package xyz.wongs;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class TestResource {

    public static void main(String[] args) throws Exception{
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("jiohriwebar");
        String content = "wongs";
        String encrypt =encryptor.encrypt(content);
        System.out.println("[加密]"+encrypt);

        String decrypt = encryptor.decrypt(encrypt);
        System.out.println("[解密]"+decrypt);

    }
}
