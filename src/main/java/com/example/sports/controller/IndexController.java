package com.example.sports.controller;

import com.example.sports.controller.viewobject.CollegePoint;
import com.example.sports.dao.AthletesMapper;
import com.example.sports.dao.CollegeMapper;
import com.example.sports.error.BusinessException;
import com.example.sports.error.EmBusinessError;
import com.example.sports.pojo.College;
import com.example.sports.response.CommonReturnType;
import com.example.sports.service.CollegeService;
import com.example.sports.service.service.CollegeServiceV1;
import com.example.sports.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("index")
@Controller
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class IndexController extends BaseController{

    @Autowired
    private CollegeService collegeService;

    //新数据库的服务
    @Autowired
    private CollegeServiceV1 collegeServiceV1;

    static int count=0;

    @RequestMapping("index")
    public String index(){
        count++;
        System.out.println(count);
        return "index";
    }

//    @ResponseBody
//    @RequestMapping("getColleges")
//    public Object getColleges(){
//        AjaxResult result=new AjaxResult();
//        try {
//            List<College> collegeList=collegeService.getCollegesOrder();
//            CollegePoint[] collegePoints = collegeServiceV1.getAllCollegePoint();
//            result.setData(collegeList);
//            result.setSuccess(true);
//        }catch (Exception e){
//            result.setSuccess(false);
//        }finally {
//            return result;
//        }
//    }
    @ResponseBody
    @RequestMapping("getCollegesV1")
    public CommonReturnType getCollegesV1(){
//        CollegePoint[] collegePoints = collegeServiceV1.getAllCollegePoint();
//        CollegePoint[] collegePoints = collegeServiceV1.getAllCollegePoint();
        return CommonReturnType.create(collegeServiceV1.getAllCollegePointAsList());
    }

    @Autowired
    CollegeMapper collegeMapper;
    @Autowired
    AthletesMapper athletesMapper;
    @ResponseBody
    @RequestMapping("test")
    public CommonReturnType test(){
        return CommonReturnType.create(athletesMapper.queryAthleteByStuNum("0101"));
    }



    @ResponseBody
    @RequestMapping("getCollegesByRedis")
    public CommonReturnType redisTest() throws BusinessException {
        List<CollegePoint> collegePoints = collegeServiceV1.getCollegeByRedis();

        if(collegePoints.size()!=0){
            return CommonReturnType.create(collegePoints);
        }

        throw new BusinessException(EmBusinessError.UNKNOWN_ERROR);
    }
}
