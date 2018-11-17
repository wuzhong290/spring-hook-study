package com.demo.javabase.security;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.*;
import java.nio.charset.Charset;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RsaSample {
    public static final String RSA_ALGORITHM = "RSA";
    public static final Charset UTF8 = Charset.forName("UTF-8");
    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    public static void main(String [] args) throws Exception {
        // generate public and private keys
        KeyPair keyPair = buildKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // encrypt the message
        byte [] encrypted = encrypt(privateKey, "This is a secret messageThis is a secret messageThis is a secret messageThis is a secret messageThis is a secret messageThis is a secret messageThis is a secret messageThis is a secret messageThis is a secret messageThis is a secret messageThis is a secret messageThis is a secret messageThis is a secret messageThis is a secret messageThis is a secret messageThis is a secret message");
        System.out.println(base64Encode(encrypted));

        // decrypt the message
        byte[] secret = decrypt(publicKey, encrypted);
        System.out.println(new String(secret, UTF8));
    }

    public static KeyPair buildKeyPair() throws NoSuchAlgorithmException {
        final int keySize = 2048;
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        keyPairGenerator.initialize(keySize);
        return keyPairGenerator.genKeyPair();
    }

    public static byte[] encrypt(PrivateKey privateKey, String message) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return cipher.doFinal(message.getBytes(UTF8));
    }

    public static byte[] decrypt(PublicKey publicKey, byte [] encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        InputStream ins = new ByteArrayInputStream(encrypted);
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        //rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
        byte[] buf = new byte[128];
        int bufl;

        while ((bufl = ins.read(buf)) != -1) {
            byte[] block = null;

            if (buf.length == bufl) {
                block = buf;
            } else {
                block = new byte[bufl];
                for (int i = 0; i < bufl; i++) {
                    block[i] = buf[i];
                }
            }

            writer.write(cipher.doFinal(block));
        }
        return writer.toByteArray();
    }

    /**
     * 从字符串中加载公钥
     *
     */
    public static RSAPublicKey loadPublicKey(String publicKeyStr) throws Exception {
        try {
            byte[] buffer = base64Decode(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public static RSAPrivateKey loadPrivateKey(String privateKeyStr) throws Exception {
        try {
            byte[] buffer = base64Decode(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public void savePublicKey(PublicKey publicKey) throws IOException {
        // 得到公钥字符串
        String publicKeyString = base64Encode(publicKey.getEncoded());
        System.out.println("publicKeyString="+publicKeyString);
        FileWriter fw = new FileWriter("publicKey.keystore");
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(publicKeyString);
        bw.close();
    }

    public void savePrivateKey(PrivateKey privateKey) throws IOException {
        // 得到私钥字符串
        String privateKeyString = base64Encode(privateKey.getEncoded());
        System.out.println("privateKeyString="+privateKeyString);

        BufferedWriter bw = new BufferedWriter(new FileWriter("privateKey.keystore"));
        bw.write(privateKeyString);
        bw.close();
    }

    public static String base64Encode(byte[] data) {
        return new BASE64Encoder().encode(data);
    }
    public static byte[] base64Decode(String data) throws IOException {
        return new BASE64Decoder().decodeBuffer(data);
    }

    /**
     * RSA签名
     *
     * @param content       待签名数据
     * @param privateKey    商户私钥
     * @param input_charset 编码格式
     * @return 签名值
     */
    public static String sign(String content, String privateKey, String input_charset) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature
                    .getInstance(SIGN_ALGORITHMS);

            signature.initSign(priKey);
            signature.update(content.getBytes(input_charset));

            byte[] signed = signature.sign();

            return Base64.encodeBase64String(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * RSA验签名检查
     *
     * @param content        待签名数据
     * @param sign           签名值
     * @param ali_public_key 支付宝公钥
     * @param input_charset  编码格式
     * @return 布尔值
     */
    public static boolean verify(String content, String sign, String ali_public_key, String input_charset) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = Base64.decodeBase64(ali_public_key);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));


            java.security.Signature signature = java.security.Signature
                    .getInstance(SIGN_ALGORITHMS);

            signature.initVerify(pubKey);
            signature.update(content.getBytes(input_charset));

            boolean bverify = signature.verify(Base64.decodeBase64(sign));
            return bverify;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
