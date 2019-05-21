package com.example.sports.controller;

import com.example.sports.bean.MatchProject;
import com.example.sports.controller.viewobject.Athletes4QueryView;
import com.example.sports.controller.viewobject.AthletesViw;
import com.example.sports.controller.viewobject.MatchProjectViw;
import com.example.sports.dao.CollegeMapper;
import com.example.sports.error.BusinessException;
import com.example.sports.error.EmBusinessError;
import com.example.sports.pojo.Athletes;
import com.example.sports.pojo.College;
import com.example.sports.pojo.Finals;
import com.example.sports.response.CommonReturnType;
import com.example.sports.service.AthletesService;
import com.example.sports.service.service.AthletesServiceV1;
import com.example.sports.service.service.MatchProjectServiceV1;
import com.example.sports.util.AjaxResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;


@RequestMapping("query")
@Controller
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class QueryController extends BaseController{

    @Autowired
    private AthletesService athletesService;

    @Autowired
    private AthletesServiceV1 athletesServiceV1;
    @Autowired
    private MatchProjectServiceV1 matchProjectServiceV1;

    @RequestMapping("index")
    public String query(){
        return "query";
    }



//    //查询个人(按姓名查询的sql语句有误,自行检查)
//    @ResponseBody
//    @RequestMapping("/queryAthletes")
//    public Object queryAthletes(@RequestParam(required = false,defaultValue = "",name = "queryText") String queryText,
//                                @RequestParam(required = false,defaultValue = "",name = "service") String service){
//        AjaxResult result=new AjaxResult();
//        try{
//            List<Athletes> athletes=null;
//            if(service.equals("queryAthletes")){//查询所有运动员
//                athletes= athletesServiceV1.
//            }else if(service.equals("queryAthletesByName")){
//                athletes=athletesService.queryDataByName(queryText);
//            }else if(service.equals("queryAthletesById")){
//                athletes=athletesService.queryDataById(queryText);
//                System.out.println(athletes);
//            }else{
//                result.setSuccess(false);
//                return result;
//            }
//
//            if(athletes==null||athletes.size()==0){
//                result.setSuccess(false);
//                return result;
//            }
//            result.setData(athletes);
//            result.setSuccess(true);
//        }catch (Exception e){
//            e.printStackTrace();
//            result.setSuccess(false);
//        }finally {
//            return result;
//        }
//    }

    @ResponseBody
    @RequestMapping("/queryAthletesV1")
    public CommonReturnType queryAthletesV1(@RequestParam(required = false,defaultValue = "",name = "queryText") String queryText,
                                @RequestParam(required = false,defaultValue = "",name = "service") String service) throws BusinessException {
        List<Athletes4QueryView> athletes = null;
        if (service.equals("queryAthletes")) {//查询所有运动员
//            athletes = athletesServiceV1.
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        } else if (service.equals("queryAthletesByName")) {//通过名称查询
            athletes = athletesServiceV1.getAthletesByAthName(queryText);
        } else if (service.equals("queryAthletesById")) {
            athletes = athletesServiceV1.getAthletesByAthId(queryText);
        } else {
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST,"运动员不存在");
        }

        if (athletes == null || athletes.size() == 0) {//抛异常
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST,"运动员不存在");
        }
        return CommonReturnType.create(athletes);
    }
////
////
////    //查询项目(未处理个人项与团队项)
////    @Autowired
////    private FinalsService finalsService; matchProjectServiceV1

//    @ResponseBody
//    @RequestMapping("/getFinalses")
//    public Object getFinalses(){
//        AjaxResult result=new AjaxResult();
//        try{
//            List<Finals> finalses = finalsService.getFinalses();
//            result.setData(finalses);
//            result.setSuccess(true);
//        }catch (Exception e){
//            e.printStackTrace();
//            result.setSuccess(false);
//        }finally {
//            return result;
//        }
        @ResponseBody
        @RequestMapping("/getFinalsesV1")
        public CommonReturnType getFinalses() {
            List<MatchProjectViw> finalses = matchProjectServiceV1.queryMatchProjectList();
            return CommonReturnType.create(finalses);
        }
////
////
////    }
////
//    //根据项目查询个人
//    @ResponseBody
//    @RequestMapping("/getAthletesByFinalsId")
//    public Object getAthletesByFinalsId(@RequestParam(required = false) String id){
//        AjaxResult result=new AjaxResult();
//        try{
//            Finals finals = finalsService.getFinalsById(id);
//            String str=finals.getFin_rank();
//            String [] arr=str.split(",");
//            System.out.println(Arrays.toString(arr));
//            List<Athletes> athletes=athletesService.queryDataByIn(arr);
//            System.out.println(athletes);
//            result.setData(athletes);
//            result.setSuccess(true);
//
//        }catch (Exception e){
//            e.printStackTrace();
//            result.setSuccess(false);
//        }finally {
//            return result;
//        }
//    }
    //根据项目查询个人
    @ResponseBody
    @RequestMapping("/getAthletesByFinalsIdV1")
    public CommonReturnType getAthletesByFinalsIdV1(@RequestParam() String name){
            List<Athletes4QueryView> athletes4QueryViews= athletesServiceV1.getAthletesByMatchName(name);
            return CommonReturnType.create(athletes4QueryViews);
    }
////
////
////
//////    @Autowired
//////    private CollegeMapper collegeMapper;
//////
//////    //查询学院
//////    @ResponseBody
//////    @RequestMapping("/getColleges")
//////    public Object getColleges(){
//////        AjaxResult result=new AjaxResult();
//////        try {
//////            List<College> colleges=collegeMapper.getColleges();
//////            result.setData(colleges);
//////            result.setSuccess(true);
//////        }catch (Exception e){
//////            e.printStackTrace();
//////            result.setSuccess(false);
//////        }finally {
//////            return result;
//////        }
//////
//////    }
////
////
    //根据学院查询个人
//    @ResponseBody
//    @RequestMapping("/getAthletesByCollegeId")
//    public Object getAthletesByCollegeId(@RequestParam(required = false) String id){
//        AjaxResult result=new AjaxResult();
//        try {
//            List<Athletes> athletes = athletesService.queryDataByCollegeId(id);
//            result.setData(athletes);
//            result.setSuccess(true);
//        }catch (Exception e){
//            e.printStackTrace();
//            result.setSuccess(false);
//        }finally {
//            return result;
//        }
//    }

    //根据学院查询个人
    @ResponseBody
    @RequestMapping("/getAthletesByCollegeIdV1")
    public CommonReturnType getAthletesByCollegeIdV1(@RequestParam(required = false) String id){
            List<Athletes4QueryView> athletes = athletesServiceV1.getAthletesByCollege4Query(Integer.parseInt(id));

            return CommonReturnType.create(athletes);
    }




}
