package com.example.demo.service;

import org.springframework.stereotype.Service;

/**
 * Scaling
 */
@Service
public class Scaling {

    int currnetReplicas = 1;

    int minReplicas = 1;
    int maxReplicas = 20;

    double desiredCPU = 50.0;

    public void setPodMinMax(int min, int max) {
        minReplicas = min;
        maxReplicas = max;
    }

    public void setReplicas(int replicas) {
        currnetReplicas = replicas;
    }

    public int getReplicas() {
        return currnetReplicas;
    }
    
    public double getDesiredCPU() {
        return desiredCPU;
    }

    public void setDesiredCPU(double target){
        desiredCPU = target;
    }

    public int scaling(boolean isPredict, double currentCPU, double predictCPU){
        int replicas = getReplicas();

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
        
        if (desiredReplicas < minReplicas) desiredReplicas = minReplicas;
        else if (desiredReplicas > maxReplicas) desiredReplicas = maxReplicas;
        
        setReplicas(desiredReplicas);
        System.out.println("\t\t" + desiredReplicas);
        return desiredReplicas;
    }

}