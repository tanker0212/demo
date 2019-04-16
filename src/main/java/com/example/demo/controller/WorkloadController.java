package com.example.demo.controller;

import com.example.demo.service.Workload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * workload
 */

@Controller
public class WorkloadController {
    @Autowired
    Workload workload;
    
    @GetMapping("/test")
    @ResponseBody
    public int getRequest() {
        int reval = workload.setLoad();
        return reval;
    }
}