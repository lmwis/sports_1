package com.example.sports.service.service.impl;

import com.example.sports.bean.CollegeTotal;
import com.example.sports.dao.CollegeMapper;
import com.example.sports.service.service.AdminServiceV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lmwis on 2019-04-22 18:27
 */
@Service
public class AdminServiceV1Impl implements AdminServiceV1 {

    @Autowired
    CollegeMapper collegeMapper;

    @Override
    public void updateTotal(List<CollegeTotal> collegeTotals) {
        if(collegeTotals==null || collegeTotals.size()==0){
            return;
        }
        for(CollegeTotal c: collegeTotals){
            if(c==null) continue;
            //更新数据库表
            this.updateTotal(c);
        }
    }

    @Override
    public void updateTotal(CollegeTotal collegeTotals) {
        if(collegeTotals==null){
            return;
        }
        //更新数据库表
        collegeMapper.updateTotalByCollegeId(collegeTotals);
    }
}
