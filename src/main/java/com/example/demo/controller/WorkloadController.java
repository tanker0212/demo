package com.example.demo.controller;

import com.example.demo.service.Workload;

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

    @GetMapping("/work")
    @ResponseBody
    public void work() {
        workload.startLoad();
    }

    @GetMapping("/setLoop")
    @ResponseBody
    public String setLoop(@RequestParam Integer loop) {
        workload.setLoop(loop);
        return "set loop : " + workload.getLoop();
    }

}