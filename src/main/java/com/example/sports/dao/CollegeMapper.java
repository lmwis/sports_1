package com.example.sports.dao;

import com.example.sports.bean.College;
import com.example.sports.bean.CollegeTotal;

import java.util.List;

/**
 * @author lmwis on 2019-04-20 20:31
 */
public interface CollegeMapper {
    /**
     * 查询所有单一学院信息
     * @return
     */
    public List<College> queryCollegeList();

    /**
     * ？？？
     * @return
     */
    public List<CollegeTotal> queryCollegeTotalList();

    /**
     * 根据学院id更新表分数
     * @param collegeTotal
     */
    public void updateTotalByCollegeId(CollegeTotal collegeTotal);

    /**
     * 通过学院id查询学院总分
     * @param id
     * @return
     */
    public CollegeTotal queryCollegeTotalByCollegeId(int id);

}
