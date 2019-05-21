package com.example.sports.controller;

import com.alibaba.druid.util.StringUtils;
import com.example.sports.bean.CollegeTotal;
import com.example.sports.error.BusinessException;
import com.example.sports.error.EmBusinessError;
import com.example.sports.response.CommonReturnType;
import com.example.sports.service.service.*;
import com.example.sports.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lmwis on 2019-04-03 20:20
 */
@Controller
@RequestMapping("/admin")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class AdminController extends BaseController{

    @Autowired
    CollegeServiceV1 collegeServiceV1;

    @Autowired
    MatchProjectServiceV1 matchProjectServiceV1;

    @Autowired
    UserServiceV1 userServiceV1;

    @RequestMapping(value = "login",method = RequestMethod.POST)
    @ResponseBody
    public CommonReturnType login(HttpServletRequest res, @RequestParam("username") String username, @RequestParam("password") String password) throws BusinessException {
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"用户名或密码为不能为空");
        }
        //boolean flag = userServiceV1.login(username,password);
        boolean flag = true;
        if(userServiceV1.login(username,password)){
            HttpSession session = res.getSession();
            session.setMaxInactiveInterval(30*60);//以秒为单位，即在没有活动30分钟后，session将失效
            session.setAttribute("username", username);
            flag=false;
            return CommonReturnType.create(flag);
        }
        throw new BusinessException(EmBusinessError.LOGIN_ERROR,"账号或密码错误");
    }



    @RequestMapping(value = "index")
    public String admin(){
        return "admin";
    }
    @RequestMapping(value = "dataChange")
    public String dataChange(HttpServletRequest res){
        HttpSession session = res.getSession();
        if (session.getAttribute("username") == null) {
            return "admin";
        }
        System.out.println(session.getAttribute("username"));
        return "data-change";
    }
    @RequestMapping(value = "history")
    public String history(HttpServletRequest res){
        HttpSession session = res.getSession();
        if (session.getAttribute("username") == null) {
            return "admin";
        }
        System.out.println(session.getAttribute("username"));
        return "history";
    }

    @RequestMapping(value = "admin")
    public String index(HttpServletRequest res){
        HttpSession session = res.getSession();
        if (session.getAttribute("username") == null) {
            return "admin";
        }
        System.out.println(session.getAttribute("username"));
        return "admin-index";
    }

    @RequestMapping(value = "insertFinal")
    public String insertFinall(HttpServletRequest res){
        HttpSession session = res.getSession();
        if (session.getAttribute("username") == null) {
            return "admin";
        }
        System.out.println(session.getAttribute("username"));
        return "insert-final";
    }

    @RequestMapping(value = "test",method = RequestMethod.GET)
    @ResponseBody
    public CommonReturnType test(){
//        CollegePoint[] collegePoint =collegeServiceV1.getAllCollegePoint();
        return  CommonReturnType.create(matchProjectServiceV1.queryMatchProjectList());
    }

    @Autowired
    ConnectServiceV1 connectServiceV1;

    /**
     * 录入决赛成绩的接口
     * @param matchName
     * @param athletes_id
     * @param grade
     * @param rank
     * @param score
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "uploadData",method = RequestMethod.POST)
    @ResponseBody
    public CommonReturnType uploadData(@RequestParam("match_name")String matchName,
                                       @RequestParam("athletes_id[]")String[] athletes_id,
                                       @RequestParam("grade[]")String[] grade,
                                       @RequestParam("rank[]")String[] rank,
                                       @RequestParam("score[]")String[] score) throws BusinessException {
        for(int i=0;i<athletes_id.length;i++){
            if(StringUtils.isEmpty(athletes_id[i])&&StringUtils.isEmpty(grade[i])
                    &&StringUtils.isEmpty(rank[i])&&StringUtils.isEmpty(score[i])){
                continue;
            }else if((!StringUtils.isEmpty(athletes_id[i])&&!StringUtils.isEmpty(grade[i])
                    &&!StringUtils.isEmpty(rank[i])&&!StringUtils.isEmpty(score[i]))){
                connectServiceV1.updateConnect(0,athletes_id[i],matchName,Integer.parseInt(rank[i]),
                        grade[i],Integer.parseInt(score[i]));
            }else {
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"请输入完整的一条数据");
            }
        }
        //作为上传图片的文件名
        this.matchName = matchName;
        return CommonReturnType.create(null);
    }
    private String matchName;
    @RequestMapping(value = "uploadDataImg",method = RequestMethod.POST)
    @ResponseBody
    public CommonReturnType uploadData(MultipartFile file) throws BusinessException {
        if(file.isEmpty()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"文件不存在");
        }
        String fileName = file.getOriginalFilename();

        String path = filePath+matchName+ StringUtil.reName(fileName);
        File dest = new File(path);
        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest); //保存文件
        } catch (IOException e) {
            e.printStackTrace();
        }

        return CommonReturnType.create(null);
    }
//    private String filePath="D:\\img\\";
    //linux的路径
    private String filePath="/home/ygn/img/";

    /**
     * 进行数据关联恢复的接口
     * @param password
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "totalSyn",method = RequestMethod.GET)
    @ResponseBody
    public CommonReturnType totalSyn(@RequestParam("password") String password) throws BusinessException {
        if(!"web003".equals(password)){
            throw new BusinessException(EmBusinessError.LOGIN_ERROR,"密钥错误");
        }
        //进行同步
        connectServiceV1.totalSyn();
        return CommonReturnType.create("同步成功");
    }

    @Autowired
    AdminServiceV1 adminServiceV1;

    /**
     * 管理员直接修改学院总积分的接口
     * @param collegeIds
     * @param totals
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "adminUpdateTotal",method = RequestMethod.POST)
    @ResponseBody
    public CommonReturnType adminUpdateTotal(@RequestParam("college_id[]") String[] collegeIds,
                                             @RequestParam("total[]") String[] totals) throws BusinessException {
        //参数校验
        if(collegeIds.length==0 || totals.length==0){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        //逐条遍历数据更新
        for(int i=0;i<collegeIds.length;i++){
            if(StringUtils.isEmpty(collegeIds[i]) || StringUtils.isEmpty(totals[i])){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"积分必须为整形数字");
            }
            CollegeTotal collegeTotal = new CollegeTotal();
            collegeTotal.setTotal(Double.valueOf(totals[i]));
            collegeTotal.setCollegeId(Integer.parseInt(collegeIds[i]));
            adminServiceV1.updateTotal(collegeTotal);
        }
        return CommonReturnType.create("保存成功");
    }

    @Autowired
    AthletesServiceV1 athletesServiceV1;

    /**
     * 管理员修改运动员的信息
     * @param athleteId
     * @param collegeId
     * @param name
     * @param matchsName
     * @param ranks
     * @param scores
     * @param grade
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "updateAthleteInfo",method = RequestMethod.POST)
    @ResponseBody
    public CommonReturnType updateAthleteInfo(@RequestParam("athlete_id") String athleteId,
                                              @RequestParam("college_id") String collegeId,
                                              @RequestParam("name") String name,
                                              @RequestParam("matchs_name[]") String[] matchsName,
                                              @RequestParam("rank[]") String[] ranks,
                                              @RequestParam("score[]") String[] scores,
                                              @RequestParam("grade[]") String[] grade) throws BusinessException {
        for(int i=0;i<ranks.length;i++){
            athletesServiceV1.updateAthletesByStuNumV1(athleteId,name,Integer.parseInt(collegeId));
            if (grade.length == 0) {
                connectServiceV1.updateConnect(0,athleteId,matchsName[i],Integer.parseInt(ranks[i]),null,Integer.parseInt(scores[i]));
            } else {
                connectServiceV1.updateConnect(0,athleteId,matchsName[i],Integer.parseInt(ranks[i]),grade[i],Integer.parseInt(scores[i]));
            }
        }

        return CommonReturnType.create("修改成功");
    }


    /**
     * 插入进入决赛运动员的信息
     * 分数默认为0
     * @param matchName
     * @param athletes_id
     * @param grade
     * @param rank
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "insertFinalList",method = RequestMethod.POST)
    @ResponseBody
    public CommonReturnType insertFinalList(@RequestParam("match_name")String matchName,
                                       @RequestParam("athletes_id[]")String[] athletes_id) throws BusinessException {
        if(athletes_id.length<=0 || StringUtils.equals(matchName,"")){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "请输入至少一条数据");
        }
        for (int i = 0; i < athletes_id.length; i++) {
            //插入数据，只录个人
            if (athletes_id[i].isEmpty())
                continue;
            connectServiceV1.insertConnect(0,athletes_id[i],matchName);
        }
        return CommonReturnType.create(null);
    }

}
