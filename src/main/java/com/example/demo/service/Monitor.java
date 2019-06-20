package com.example.demo.service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * Monitor
 */
@Service
public class Monitor {
    @Autowired
    Workload workload;

    boolean isMonitoring = false;

    List<Double> cpuUasge = new ArrayList<Double>();

    double currentCPU = 0;
    double predictCPU = 0;
    double desiredCPU = 50.0;

    ScheduledExecutorService service;


    public double getDesiredCPU() {
        return desiredCPU;
    }

    public void setDesiredCPU(double target){
        desiredCPU = target;
    }

    public double getCPU(){
        return currentCPU;
    }

    public double showCPU(){
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        double load = osBean.getSystemCpuLoad();
        return load*100.0;
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
                predictCPU = predict();
                scaling(isPredict);
                // System.out.println(currentCPU);
                // System.out.println(out); 
            }
        };
        service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable, 0, 1000, TimeUnit.MILLISECONDS);
    }

    public void stop(){
        service.shutdown();
    }

    public void testPrint(){
        System.out.println(workload.getReplicas() + "\t" + currentCPU + "\t" + predictCPU);
    }

    public int scaling(boolean isPredict){
        int replicas = workload.getReplicas();

        int desiredReplicas = 1;
        if (isPredict){
            //예측모델
            desiredReplicas = (int) Math.ceil(replicas * (predictCPU / desiredCPU));
            if (replicas > desiredReplicas){
                desiredReplicas = (int) Math.ceil(replicas * (currentCPU / desiredCPU));
            }

        } else {
            //기존 모델
            desiredReplicas = (int) Math.ceil(replicas * (currentCPU / desiredCPU));
        }
        
        if (desiredReplicas == 0) desiredReplicas = 1;
        workload.setReplicas(desiredReplicas);
        System.out.println("\t\t" + desiredReplicas);
        return desiredReplicas;
    }


    public double predict(){
        return getExpect(cpuUasge, 1, 0.6);
    }

    private Double getExpect(List<Double> list, int year, Double modulus ) {
        if (list.size() < 10 || modulus <= 0 || modulus >= 1) {
            return (double) 0;
        }

        if (list.size() > 20){
            list = list.subList(list.size()-20, list.size());
        }

        // System.out.println("list : ");
        // System.out.println(list);
        // System.out.println("\n");

        Double modulusLeft = 1 - modulus;
        Double lastIndex = list.get(0);
        Double lastSecIndex = list.get(0);
        for (Double data :list) {
            lastIndex = modulus * data + modulusLeft * lastIndex;
            lastSecIndex = modulus * lastIndex + modulusLeft * lastSecIndex;
        }
        Double a = 2 * lastIndex - lastSecIndex;
        Double b = (modulus / modulusLeft) * (lastIndex - lastSecIndex);
        return a + b * year;
    }
}