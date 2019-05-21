package com.example.sports.controller;

import com.example.sports.bean.MatchProject;
import com.example.sports.pojo.Match;
import com.example.sports.response.CommonReturnType;
import com.example.sports.service.service.MatchProjectServiceV1;
import com.example.sports.util.AjaxResult;
import com.example.sports.util.PageCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("event")
@Controller
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class EventController extends BaseController{


    @RequestMapping("index")
    public String event() {
        return "event";
    }


//    @ResponseBody
//    @RequestMapping("/queryData")
//    public Object queryData(@RequestParam(required = false, defaultValue = "1") Integer pageNo,
//                            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
//                            @RequestParam(required = false, defaultValue = "5") Integer step) {
//
//        AjaxResult result = new AjaxResult();
//        try {
//            Map<String, Object> map = new HashMap<>();
//            map.put("startIndex", (pageNo - 1) * pageSize);
//            map.put("pageSize", pageSize);
//            List<Match> matches = matchService.queryData(map);
//            if (matches == null || matches.size() == 0) {
//                result.setSuccess(true);
//                return result;
//            }
//            int totalSize = matchService.getCount(map);
//            PageCount<Match> pageCount = new PageCount<>();
//            pageCount.count(matches, pageNo, pageSize, totalSize, step);
//            result.setData(pageCount.getPageCustom());
//            result.setSuccess(true);
//        } catch (Exception e) {
//            e.printStackTrace();
//            result.setSuccess(false);
//        } finally {
//            return result;
//        }
//
//    }

    @Autowired
    MatchProjectServiceV1 matchProjectServiceV1;

    @ResponseBody
    @RequestMapping("/queryDataV1")
    public CommonReturnType queryDataV1(@RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                        @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                        @RequestParam(required = false, defaultValue = "5") Integer step) {

        Map<String, Object> map = new HashMap<>();
        map.put("startIndex", (pageNo - 1) * pageSize);
        map.put("pageSize", pageSize);
        List<MatchProject> matches = matchProjectServiceV1.queryData(map);
        if (matches == null || matches.size() == 0) {
            return CommonReturnType.create(null);
        }
        int totalSize = matchProjectServiceV1.getCount(map);
        PageCount<MatchProject> pageCount = new PageCount<>();
        pageCount.count(matches, pageNo, pageSize, totalSize, step);
        return CommonReturnType.create(pageCount.getPageCustom());
    }

    @ResponseBody
    @RequestMapping("/test")
    public CommonReturnType test() {
        return CommonReturnType.create(matchProjectServiceV1.queryMatchProjectList());
    }

}