package com.example.demo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

/**
 * workload
 */
@Service
public class Workload {

    public int setLoad(Integer loop) {
        if (loop == null)
            loop = 1024;
        System.out.println(loop);
        int x = 1;
        for (int i = 0; i < loop; i++) {
            for (int j = 0; j < i; j++) {
                for (int k = 0; k < j; k++) {
                    x += i + j + k;
                }
            }
        }
        return x;
    }

    public void startWorkload(String ip, int repeat, int load) throws ClientProtocolException, IOException {

        String GET_URL = "http://" + ip + "/test?loop=" + load;
        long start = System.currentTimeMillis();
        long tmp = start;

        for (int i = 0; i < repeat; i++) {
            //http client 생성
            CloseableHttpClient httpClient = HttpClients.createDefault();
    
            //get 메서드와 URL 설정
            HttpGet httpGet = new HttpGet(GET_URL);
    
            //get 요청
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            
            System.out.println("::GET Response Status::");
            
            //response의 status 코드 출력
            System.out.println(httpResponse.getStatusLine().getStatusCode());
    
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    httpResponse.getEntity().getContent()));
    
            String inputLine;
            StringBuffer response = new StringBuffer();
    
            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
            
            reader.close();
    
            //Print result

            System.out.println((System.currentTimeMillis() - tmp)/1000.0);
            tmp = System.currentTimeMillis();
            
            httpClient.close();
        }

        long end = System.currentTimeMillis();
        double runningTime = (end - start)/1000.0;
        System.out.println("실행 시간 : " + runningTime);
        System.out.println("총 요청 수 : " + repeat);
    }
    
        public String showCPU(){
        OperatingSystemMXBean osbean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        RuntimeMXBean runbean = (RuntimeMXBean) ManagementFactory.getRuntimeMXBean();
        long bfprocesstime = osbean.getProcessCpuTime();
        long bfuptim = runbean.getUptime();
        long ncpus = osbean.getAvailableProcessors();
        for (int i = 0; i < 1000000; ++i) {
            ncpus = osbean.getAvailableProcessors();
        }
        long afprocesstime = osbean.getProcessCpuTime();
        long afuptime = runbean.getUptime();
        float cal = (afprocesstime - bfprocesstime) / ((afuptime - bfuptim) * 10000f);
        float usage = Math.min(99f, cal);
        
        return "CPU usage : " + usage;    
    }

}
