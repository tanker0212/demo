package com.example.demo.controller;

import com.example.demo.service.Monitor;
import com.example.demo.service.Scaling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ScalingController
 */
public class ScalingController {

    @Autowired
    Monitor monitor;

    @Autowired
    Scaling scaling;
    
    @GetMapping("/init")
    @ResponseBody
    public int init() {
        scaling.setReplicas(1);
        return scaling.getReplicas();
    }

    @GetMapping("/setDesiredCPU")
    @ResponseBody
    public String setDesiredCPU(@RequestParam double set) {
        scaling.setDesiredCPU(set);
        return "Target Cpu usage : " + set + "%";
    }

    @GetMapping("/getDesiredCPU")
    @ResponseBody
    public String getDesiredCPU() {
        return "Target Cpu usage : " + scaling.getDesiredCPU() + "%";
    }

    @GetMapping("/setTime")
    @ResponseBody
    public String setSchedulingTime(@RequestParam int time) {
        monitor.setSchedulingTime(time);
        return "Scheduling Time : " + monitor.getSchedulingTime() + " ms";
    }

    @GetMapping("/setPodMinMax")
    @ResponseBody
    public String setPodMinMax(@RequestParam int min, @RequestParam int max) {
        scaling.setPodMinMax(min, max);
        return "ok!";
    }
    
}