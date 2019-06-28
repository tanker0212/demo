package com.example.demo.service;

import org.springframework.stereotype.Service;

/**
 * workload
 */
@Service
public class Workload {

    int loadLoop = 2000;

    public void setLoop(int loop){
        loadLoop = loop;
    }

    public int getLoop(){
        return loadLoop;
    }

    public int startLoad() {
        int x = 1;
        for (int i = 0; i < loadLoop; i++) {
            for (int j = 0; j < i; j++) {
                for (int k = 0; k < j; k++) {
                    x += i + j + k;
                }
            }
        }
        return x;
    }
}