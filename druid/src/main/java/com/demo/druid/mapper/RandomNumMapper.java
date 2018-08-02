package com.demo.druid.mapper;


import com.demo.druid.model.RandomNum;

import java.util.List;

public interface RandomNumMapper {

    /**根据id查询random_num
     * @param id
     * @return
     */
    RandomNum selectRandomNum(Integer id);

    /**
     * 随机插入random_num
     */
    void insertRandomNum(RandomNum randomNum);

    void insertRandomNums(List<RandomNum> randomNums);
}
