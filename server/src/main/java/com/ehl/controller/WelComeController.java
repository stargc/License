package com.ehl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("")
public class WelComeController {
    @RequestMapping({"","/"})
    public String welComePage(){
        return "index";
    }
}