package com.example.sports.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("interaction")
@Controller
public class InteractionController extends BaseController{

    @RequestMapping("/index")
    public String index(){
        return "interaction";
    }
}
