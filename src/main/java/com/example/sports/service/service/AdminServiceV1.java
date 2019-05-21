package com.example.sports.service.service;

import com.example.sports.bean.CollegeTotal;

import java.util.List;

/**
 * @author lmwis on 2019-04-22 18:26
 */
public interface AdminServiceV1 {
    public void updateTotal(List<CollegeTotal> collegeTotals);
    public void updateTotal(CollegeTotal collegeTotals);
}
