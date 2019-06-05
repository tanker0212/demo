package com.example.demo.controller;

import java.io.IOException;

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
    
    @GetMapping("/test")
    @ResponseBody
    public int getRequest(@RequestParam(required = false) Integer loop) {
        //System.out.println(loop);
        int retval = workload.setLoad(loop);
        return retval;
    }


    @GetMapping("/work")
    @ResponseBody
    public void loadTheWork(@RequestParam String ip, @RequestParam int repeat, @RequestParam int load) throws ClientProtocolException, IOException {
        workload.startWorkload(ip, repeat, load);
    }
    
        @GetMapping("/cpuUsage")
    @ResponseBody
    public String hanqing() {
        return workload.showCPU();
    }
    
}
