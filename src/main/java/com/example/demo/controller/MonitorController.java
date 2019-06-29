package com.example.demo.controller;

import com.example.demo.service.Monitor;
import com.example.demo.service.Scaling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * MonitorController
 */
@Controller
public class MonitorController {

    @Autowired
    Monitor monitor;

    @Autowired
    Scaling scaling;

    @GetMapping("/monitor")
    @ResponseBody
    public String monitor() {
        return scaling.getReplicas() + "\t" + monitor.getCPU();
    }

    @GetMapping("/stop")
    @ResponseBody
    public String stopMonitoring() {
        monitor.stop();
        return "Monitoring Stop!";
    }

    @GetMapping("/startD")
    @ResponseBody
    public String startD() {
        monitor.start(false);
        return "default scheme Start";
    }

    @GetMapping("/startP")
    @ResponseBody
    public String startP() {
        monitor.start(true);
        return "predition scheme Start";
    }

    @GetMapping("/setTime")
    @ResponseBody
    public String setSchedulingTime(@RequestParam int time) {
        monitor.setSchedulingTime(time);
        return "Scheduling Time : " + monitor.getSchedulingTime() + " ms";
    }


    
}