package com.example.demo.controller;

import java.io.IOException;

import com.example.demo.service.MetricPrediction;
import com.example.demo.service.Monitor;
import com.example.demo.service.Workload;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * workload
 */

@Controller
public class WorkloadController {
    @Autowired
    Workload workload;

    @Autowired
    Monitor monitor;

    // @Autowired
    // MetricPrediction metricPrediction;
    
    @GetMapping("/test")
    @ResponseBody
    public int getRequest(@RequestParam(required = false) Integer loop) {
        //System.out.println(loop);
        int retval = workload.setLoad(loop);
        return retval;
    }

    @GetMapping("/print")
    @ResponseBody
    public void print() {
        //System.out.println(loop);
        monitor.testPrint();

    }

    @GetMapping("/")
    @ResponseBody
    public String work() {
        workload.setLoad(100);
        return workload.getReplicas() + "\t" + monitor.getCPU();
    }

    @GetMapping("/setLoop")
    @ResponseBody
    public String setLoop(@RequestParam Integer loop) {
        workload.setLoop(loop);
        return "set loop : " + workload.getLoop();
    }



    @GetMapping("/init")
    @ResponseBody
    public int init() {
        workload.setReplicas(1);
        return workload.getReplicas();
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

    @GetMapping("/setDesiredCPU")
    @ResponseBody
    public String setDesiredCPU(@RequestParam double set) {
        monitor.setDesiredCPU(set);
        return "Target Cpu usage : " + set + "%";
    }

    @GetMapping("/getDesiredCPU")
    @ResponseBody
    public String getDesiredCPU() {
        return "Target Cpu usage : " + monitor.getDesiredCPU() + "%";
    }



    // @GetMapping("/work")
    // @ResponseBody
    // public void loadTheWork(@RequestParam String ip, @RequestParam int repeat, @RequestParam int load) throws ClientProtocolException, IOException {
    //     workload.startWorkload(ip, repeat, load);
    // }

    // @GetMapping("/save")
    // @ResponseBody
    // public String loadTheWork() {
    //     workload.saveLog();
    //     return "OK";
    // }
}