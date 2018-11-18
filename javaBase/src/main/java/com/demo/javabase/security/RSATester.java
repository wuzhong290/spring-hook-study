package com.demo.javabase.security;

import org.apache.commons.codec.binary.Base64;

import java.util.Map;

public class RSATester {

    static String publicKey;
    static String privateKey;

    static {
        try {
            Map<String, Object> keyMap = RSAUtils.genKeyPair();
            publicKey = RSAUtils.getPublicKey(keyMap);
            privateKey = RSAUtils.getPrivateKey(keyMap);
            System.out.println("公钥: \n\r" + publicKey);
            System.out.println("私钥： \n\r" + privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        //test();
        testepub2dpri();
        testepri2dpub();
        testSign();
        //test1();
    }

    static void test1() throws Exception{
        byte [] encrypted = RSAUtils.encryptByPrivateKey("This is a secret messageThis is a secret messageThis is a secret messageThis is a secret messageThis is a secret messageThis is a secret messageThis is a secret messageThis is a secret messageThis is a secret messageThis is a secret messageThis is a secret messageThis is a secret messageThis is a secret messageThis is a secret messageThis is a secret messageThis is a secret message".getBytes("utf-8"), privateKey);
        System.out.println(Base64.encodeBase64String(encrypted));
        // decrypt the message
        byte[] secret = RSAUtils.decryptByPublicKey(encrypted, publicKey);
        System.out.println(new String(secret, "utf-8"));
    }

    static void testepub2dpri() throws Exception {
        System.out.println("公钥加密——私钥解密");
        String source = "这是一行没有任何意义的文字，你看完了等于没看，不是吗？";
        System.out.println("\r加密前文字：\r\n" + source);
        byte[] data = source.getBytes();
        byte[] encodedData = RSAUtils.encryptByPublicKey(data, publicKey);
        System.out.println("加密后文字：\r\n" + new String(encodedData));
        byte[] decodedData = RSAUtils.decryptByPrivateKey(encodedData, privateKey);
        String target = new String(decodedData);
        System.out.println("解密后文字: \r\n" + target);
    }

    static void testepri2dpub() throws Exception {
        System.out.println("私钥加密——公钥解密");
        String source = "这是一行测试RSA数字签名的无意义文字";
        System.out.println("原文字：\r\n" + source);
        byte[] data = source.getBytes();
        byte[] encodedData = RSAUtils.encryptByPrivateKey(data, privateKey);
        System.out.println("加密后：\r\n" + new String(encodedData));
        byte[] decodedData = RSAUtils.decryptByPublicKey(encodedData, publicKey);
        String target = new String(decodedData);
        System.out.println("解密后: \r\n" + target);
    }

    static void testSign() throws Exception {
        System.out.println("私钥签名——公钥验证签名");
        String source = "这是一行测试RSA数字签名的无意义文字";
        System.out.println("原文字：\r\n" + source);
        byte[] data = source.getBytes();
        String sign = RSAUtils.sign(data, privateKey);
        System.out.println("签名:\n\r" + sign);
        boolean status = RSAUtils.verify(data, publicKey, sign);
        System.out.println("验证结果:\n\r" + status);
    }
}