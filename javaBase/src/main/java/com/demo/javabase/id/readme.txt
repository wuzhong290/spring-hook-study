currentTimeMillis是一个long类型的变量，64位，
1、currentTimeMillis:10110010111000010110010100010101111000001
2、low Time
time = currentTimeMillis << 32;
1-32位保留，共32位，移动后变成33-64位
currentTimeMillis << 32：1100001011001010001010111100000100000000000000000000000000000000
3、mid Time
time |= ((currentTimeMillis & 0xFFFF00000000L) >> 16);
33-48位保留，共16位，移动后变成17-32位
((currentTimeMillis & 0xFFFF00000000L) >> 16):1011001010000000000000000
4、 hi Time
time |= 0x1000 | ((currentTimeMillis >> 48) & 0x0FFF);
49-63位保留，共15位，移动后变成1-15位，在通过0x0FFF保留12位1-12
(currentTimeMillis >> 48) & 0x0FFF：0



