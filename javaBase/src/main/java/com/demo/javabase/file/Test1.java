package com.demo.javabase.file;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 测试利用多线程进行文件的写操作
 */
public class Test1 {

    public static void main(String[] args) throws Exception {
        // 预分配文件所占的磁盘空间，磁盘中会创建一个指定大小的文件
        RandomAccessFile raf = new RandomAccessFile(new File("D://abc.txt"), "rw");
        // 所要写入的文件内容
        String s1 = "1111111111第一个字符串222222222222222222222";

        try {
            byte b[] = new byte[(int)raf.length()];
            raf.readFully(b);
            System.out.println(new String(b));
//            raf.seek(raf.length());
//            raf.write(s1.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                raf.close();
            } catch (Exception e) {
            }
        }
    }

}
