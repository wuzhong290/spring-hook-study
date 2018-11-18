package com.demo.javabase.security;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

public class AES {
    /**
     * 注意key和加密用到的字符串是不一样的 加密还要指定填充的加密模式和填充模式 AES密钥可以是128或者256，加密模式包括ECB, CBC等
     * ECB模式是分组的模式，CBC是分块加密后，每块与前一块的加密结果异或后再加密 第一块加密的明文是与IV变量进行异或
     */
    public static final String KEY_ALGORITHM = "AES";
    public static final String CBC_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

    /**
     * IV(Initialization Value)是一个初始值，对于CBC模式来说，它必须是随机选取并且需要保密的
     * 而且它的长度和密码分组相同(比如：对于AES 128为128位，即长度为16的byte类型数组)
     *
     */
    public static final byte[] ivBytes = new byte[] { 0x02, 0x02, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01 };

    /**
     * ECB模式进行加密,用key对data进行加密
     * @param key    AES密钥,默认UTF-8编码,必须为 128bit或256bit,即 16字节或32字节, 比如 "1234567890123456"
     * @param data   被加密的数据
     * @return  加密结果, 已经转换为Base64字符串
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws IOException
     */
    public static String encrypt(String key, String data)
            throws NoSuchAlgorithmException,
            NoSuchPaddingException,
            InvalidKeyException,
            InvalidAlgorithmParameterException,
            IllegalBlockSizeException,
            BadPaddingException, IOException {

        byte[] dataBytes = data.getBytes();
        byte[] keyBytes = key.getBytes();

        //必须为 128bit或256bit,即 16字节或32字节
        if( keyBytes.length != 16 && keyBytes.length != 32 ){
            return "" ;
        }

        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        SecretKeySpec k =  new SecretKeySpec(keyBytes, KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, k);
        return  Base64.encodeBase64String(cipher.doFinal(dataBytes));

    }

    /**
     * ECB模式进行解密,用key对data进行解密
     * @param key   AES密钥, 默认UTF-8编码,必须为 128bit或256bit,即 16字节或32字节, 比如 "1234567890123456"
     * @param data  被解密的数据, (加密结果对Base64字符串)
     * @return  解密结果字符串
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static String decrypt(String key, String data)
            throws NoSuchAlgorithmException,
            NoSuchPaddingException,
            InvalidKeyException,
            InvalidAlgorithmParameterException,
            IllegalBlockSizeException,
            BadPaddingException, IOException, ClassNotFoundException {

        byte[] keyBytes = key.getBytes();
        byte[] dataBytes = Base64.decodeBase64(data);

        //必须为 128bit或256bit,即 16字节或32字节
        if( keyBytes.length != 16 && keyBytes.length != 32 ){
            return "" ;
        }

        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec k =  new SecretKeySpec(keyBytes, "AES");
        cipher.init(Cipher.DECRYPT_MODE, k);

        return  new String(cipher.doFinal(dataBytes));

    }


    /**
     * CBC模式进行加密,用key对data进行加密,并使用
     * @param keyBytes
     * @param dataBytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws IOException
     */
    public static byte[] encryptCBC(byte[] keyBytes, byte[] dataBytes)
            throws NoSuchAlgorithmException,
            NoSuchPaddingException,
            InvalidKeyException,
            InvalidAlgorithmParameterException,
            IllegalBlockSizeException,
            BadPaddingException, IOException {

        AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        SecretKeySpec newKey = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance(CBC_CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);
        return  cipher.doFinal(dataBytes);

    }

    /**
     *
     * @param keyBytes
     * @param dataBytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static byte[] decryptCBC(byte[] keyBytes, byte[] dataBytes)
            throws NoSuchAlgorithmException,
            NoSuchPaddingException,
            InvalidKeyException,
            InvalidAlgorithmParameterException,
            IllegalBlockSizeException,
            BadPaddingException, IOException, ClassNotFoundException {

        AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        SecretKeySpec newKey = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance(CBC_CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);
        return  cipher.doFinal(dataBytes);

    }

    public static void main(String[] args) {
        try {
            String en = encrypt("1234567890123456","This is a secret messageThis is a secret messageThis is a secret messageThis is a secret messageThis is a secret messageThis is a secret messageThis is a secret messageThis is a secret messageThis is a secret messageThis is a secret messageThis is a secret messageThis is a secret messageThis is a secret messageThis is a secret messageThis is a secret messageThis is a secret message");
            System.out.println(decrypt("1234567890123456",en));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}