package com.demo.javabase.id;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Random;
import java.util.UUID;

/**
 * Will generate time-based UUID (version 1 UUID).
 * Requires JDK 1.6+
 *
 * @author Oleg Zhurakousky
 */
public class TimeBasedUUIDGenerator {

    private static final Logger log = LoggerFactory.getLogger(TimeBasedUUIDGenerator.class);

    public static final Object lock = new Object();

    private static long lastTime;
    private static long clockSequence = 0;
    private static final long hostIdentifier = getHostId();

    /**
     * Will generate unique time based UUID where the next UUID is
     * always greater then the previous.
     */
    public final synchronized static String generateId(String prefix) {
        return prefix + generateIdFromTimestamp(System.currentTimeMillis());
    }

    public final  static String generateIdFromTimestamp(long currentTimeMillis){
        long time;
        if (currentTimeMillis > lastTime) {
            lastTime = currentTimeMillis;
            clockSequence = 0;
        } else  {
            ++clockSequence;
        }

        // low Time
        time = currentTimeMillis << 32;

        // mid Time
        time |= ((currentTimeMillis & 0xFFFF00000000L) >> 16);

        // hi Time
        time |= 0x1000 | ((currentTimeMillis >> 48) & 0x0FFF);

        long clockSequenceHi = clockSequence;

        clockSequenceHi <<=48;

        long lsb = clockSequenceHi | hostIdentifier;
        String uuid = new UUID(time, lsb).toString().replace("-", "");
        log.info("currentTimeMillis={},lastTime={},clockSequence={},lsb={},uuid={}",currentTimeMillis,lastTime,clockSequence,lsb,uuid);
        return uuid;
    }
    private static final long getHostId(){
        long  macAddressAsLong = 0;
        try {
            Random random = new Random();
            InetAddress address = InetAddress.getLocalHost();
            NetworkInterface ni = NetworkInterface.getByInetAddress(address);
            if (ni != null) {
                byte[] mac = ni.getHardwareAddress();
                random.nextBytes(mac); // we don't really want to reveal the actual MAC address
                //Converts array of unsigned bytes to an long
                if (mac != null) {
                    for (int i = 0; i < mac.length; i++) {
                        macAddressAsLong <<= 8;
                        macAddressAsLong ^= (long)mac[i] & 0xFF;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return macAddressAsLong;
    }
    /*
    0000000000000000000000010101101110001010010111101010000111100100    currentTimeMillis
    1000101001011110101000011110010000000000000000000000000000000000    currentTimeMillis << 32 (为currentTimeMillis最后32位)
    0000000000000000111111111111111100000000000000000000000000000000    0xFFFF00000000L
    0000000000000000000000010101101100000000000000000000000000000000    currentTimeMillis & 0xFFFF00000000L
    0000000000000000000000000000000000000001010110110000000000000000    (currentTimeMillis & 0xFFFF00000000L) >> 16
    1000101001011110101000011110010000000001010110110000000000000000    time |= ((currentTimeMillis & 0xFFFF00000000L) >> 16);//time = currentTimeMillis << 32
    0000000000000000000000000000000000000000000000000000000000000000    currentTimeMillis >> 48
    0000000000000000000000000000000000000000000000000000000000000000    (currentTimeMillis >> 48) & 0x0FFF
    0000000000000000000000000000000000000000000000000001000000000000    0x1000 | ((currentTimeMillis >> 48) & 0x0FFF);
    1000101001011110101000011110010000000001010110110001000000000000    time |= 0x1000 | ((currentTimeMillis >> 48) & 0x0FFF);
    * */

    /*public static String getUUID(String prefix) {
        return prefix + TimeBasedUUIDGenerator.generateId().toString().replace("-", "");
    }*/


    public static void main(String[] args) {
        //System.out.println(TimeBasedUUIDGenerator.generateIdFromTimestamp(System.currentTimeMillis()));
        Thread thread1 = new Thread(){
            @Override
            public void run() {
                for (int i =0; i < 500; i++){
                    TimeBasedUUIDGenerator.generateId("thread1_");
                }
            }
        };
        Thread thread2 = new Thread(){
            @Override
            public void run() {
                for (int i =0; i < 500; i++){
                    TimeBasedUUIDGenerator.generateId("thread2_");
                }
            }
        };
        Thread thread3 = new Thread(){
            @Override
            public void run() {
                for (int i =0; i < 500; i++){
                    TimeBasedUUIDGenerator.generateId("thread3_");
                }
            }
        };
        thread1.start();
        thread2.start();
        thread3.start();
        //System.out.println(TimeBasedUUIDGenerator.getHostId());
    }
}
