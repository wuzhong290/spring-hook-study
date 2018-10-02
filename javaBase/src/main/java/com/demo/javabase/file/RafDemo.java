package com.demo.javabase.file;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
public class RafDemo {
    public static void main(String[] args) throws IOException {
        File demo = new File("D:/demo");
        if (!demo.exists()) {
            demo.mkdir();
        }
        File file = new File(demo, "raf.dat");
        if (!file.exists()) {
            file.createNewFile();
        }
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        System.out.println(raf.getFilePointer());
        //write方法只写一个字节，一个char是两个字节，所以会把A的后八位写进去
        raf.write('A');
        System.out.println(raf.getFilePointer());

        int i = 0x7fffffff;
        raf.write(i >>> 24);
        raf.write(i >>> 16);
        raf.write(i >>> 8);
        //相当于raf.writeInt();
        raf.write(i);
        System.out.println(raf.getFilePointer());
        //也可以直接写一个数组
        String s = "中";
        byte[] gbk = s.getBytes();
        raf.write(gbk);
        System.out.println(raf.getFilePointer());
        System.out.println(raf.length());

        //读文件必须把指针移动到头部
        raf.seek(0);
        //一次性全部读取
        byte[] buf = new byte[(int) raf.length()];
        raf.read(buf);
        System.out.println(Arrays.toString(buf));

        //最终一定要关闭，close
        raf.close();

    }
}