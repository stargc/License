package com.ehl.license.check.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author created by guanchen on 2020/2/18 16:16
 */
@RestController
@Log4j2
public class LicenseController {
    @GetMapping("test")
    public String test() {
        return "SUCCESS";
    }
}
