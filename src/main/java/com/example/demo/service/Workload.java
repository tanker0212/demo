package com.example.demo.service;

import org.springframework.stereotype.Service;

/**
 * workload
 */
@Service
public class Workload {

    public int setLoad() {
        int x = 1;
        for (int i = 0; i < 4096; i++) {
            for (int j = 0; j < i; j++) {
                for (int k = 0; k < j; k++) {
                    x += i + j + k;
                }
            }
        }
        return x;
    }
}