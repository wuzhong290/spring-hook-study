package com.demo.javabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Test {

    public static void main(String[] args) {
        System.out.println(getResult());
    }

    public static String getResult()
    {
        String prizeID = "0";
        List<T_Prize> prize = new ArrayList<>();
        T_Prize prize1 = new T_Prize();
        prize1.setPrizeID("1");
        prize1.setLimit(0);
        prize1.setStock(2);
        prize1.setProbability(0);
        prize1.setPrizeName("11111111");
        T_Prize prize2 = new T_Prize();
        prize2.setPrizeID("2");
        prize2.setLimit(0);
        prize2.setStock(4);
        prize2.setProbability(0);
        prize2.setPrizeName("2222222");
        prize.add(prize1);
        prize.add(prize2);
        if ((prize != null) && (prize.size() > 0))
        {
            int pCount = prize.size();
            int total = 100000000;
            List<OpArea> opAreas = new ArrayList<>();
            for (int i = 0; i < pCount; i++)
            {
                OpArea opArea = new OpArea();
                if (i == 0) {
                    opArea.start = 0;
                } else {
                    opArea.start = (((OpArea)opAreas.get(i - 1)).end + 1);
                }
                opArea.end = (opArea.start + (int)(((T_Prize)prize.get(i)).getProbability() * total) - 1);
                opAreas.add(opArea);
            }
            int rnd = new Random(System.currentTimeMillis()).nextInt(total);
            if (opAreas.size() > 0) {
                for (int i = 0; i < opAreas.size(); i++) {
                    if ((rnd > ((OpArea)opAreas.get(i)).start) && (rnd < ((OpArea)opAreas.get(i)).end))
                    {
                        if (((T_Prize)prize.get(i)).getStock() <= 0) {
                            break;
                        }
                        prizeID = ((T_Prize)prize.get(i)).getPrizeID();

                        break;
                    }
                }
            }
        }
        return prizeID;
    }

}
