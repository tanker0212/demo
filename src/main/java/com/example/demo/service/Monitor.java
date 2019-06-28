package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * Monitor
 */
@Service
public class Monitor {
    @Autowired
    Scaling scaling;

    @Autowired
    Prediction prediction;

    List<Double> cpuUasge = new ArrayList<Double>();

    boolean isMonitoring = false;

    double currentCPU = 0;
    double predictCPU = 0;

    int schedulingTime = 5000;

    ScheduledExecutorService service;

    OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    public int getSchedulingTime() {
        return schedulingTime;
    }

    public void setSchedulingTime(int time){
        schedulingTime = time;
    }

    public double getCPU(){
        return currentCPU;
    }

    public double showCPU(){
        return osBean.getSystemCpuLoad() * 100.0;
    }

    public void start(boolean isPredict){
        if(isMonitoring)
            return;
        isMonitoring = true;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                currentCPU = showCPU();
                cpuUasge.add(currentCPU);
                
                if (cpuUasge.size() > 40){
                    cpuUasge = cpuUasge.subList(cpuUasge.size()-40, cpuUasge.size());
                }

                System.out.println("list : ");
                System.out.println(cpuUasge);
                System.out.println("\n");

                predictCPU = getPredict();
                scaling.scaling(isPredict, currentCPU, predictCPU);
            }
        };
        service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable, 0, schedulingTime, TimeUnit.MILLISECONDS);
    }

    public void stop(){
        service.shutdown();
        currentCPU = 0;
        predictCPU = 0;
        cpuUasge.clear();
        isMonitoring = false;
    }

    public double getPredict(){
        return prediction.prediction(cpuUasge, 1, 0.6);
    }
}