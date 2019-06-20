package com.example.demo.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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

    BufferedWriter bw = null;
    Calendar cal = null;
    SimpleDateFormat timeFormat = null;
    int squ = 0;

    int currnetReplicas = 1;

    int loadLoop = 1024;

    public void setLoop(int loop){
        loadLoop = loop;
    }

    public int getLoop(){
        return loadLoop;
    }

    public void setReplicas(int replicas) {
        currnetReplicas = replicas;
    }

    public int getReplicas() {
        return currnetReplicas;
    }

    // public Workload(){
    //     try {
	// 		bw = Files.newBufferedWriter(Paths.get("C:\\Users\\lim\\Documents\\dev\\log\\loadfordefalt.csv"));
	// 	} catch (IOException e) {
	// 		e.printStackTrace();
    //     }

    //     try {
    //         bw.write("time,squ,response,taken\n");
	// 	} catch (IOException e) {
	// 		e.printStackTrace(); 
    //     }
    //     // addInLineLog("squ");
    //     // addInLineLog("time");
    //     // addInLineLog("response");
    //     // addEndLineLog("taken");

    //     timeFormat = new SimpleDateFormat("hh:mm:ss");
    // }

    public int setLoad(Integer loop) {
        loop = loadLoop;
        if (loop == null)
            loop = 1024;
        //System.out.println(loop);
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

    // public void startWorkload(String ip, int repeat, int load) throws ClientProtocolException, IOException {
    //     squ += 1;
    //     int tmpSqu = squ;
        
        
    //     String GET_URL = "http://" + ip + "/test?loop=" + load;

    //     logLine(Integer.toString(tmpSqu) + "," + GET_URL);
    //     // addInLineLog(Integer.toString(tmpSqu));
    //     // addTimeToLog();
    //     // addEndLineLog(GET_URL);
        
    //     long start = System.currentTimeMillis();
    //     long tmp = start;

    //     for (int i = 0; i < repeat; i++) {

    //         CloseableHttpClient httpClient = HttpClients.createDefault();
    //         HttpGet httpGet = new HttpGet(GET_URL);
    //         CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
    //         BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));

    //         String inputLine;
    //         StringBuffer response = new StringBuffer();

    //         while ((inputLine = reader.readLine()) != null) {
    //             response.append(inputLine);
    //         }
            
    //         reader.close();

    //         logLine(Integer.toString(tmpSqu) + "," + Integer.toString(httpResponse.getStatusLine().getStatusCode()) + "," + Long.toString(System.currentTimeMillis() - tmp));
    //         // addInLineLog(Integer.toString(tmpSqu));
    //         // addTimeToLog();
    //         // addInLineLog(Integer.toString(httpResponse.getStatusLine().getStatusCode()));
    //         // addEndLineLog(Long.toString(System.currentTimeMillis() - tmp));

    //         tmp = System.currentTimeMillis();
    //         httpClient.close();
    //     }

    //     long end = System.currentTimeMillis();
    //     double runningTime = (end - start)/1000.0;

    //     logLine(Integer.toString(tmpSqu) + "," + Double.toString(runningTime));
    //     // addInLineLog(Integer.toString(tmpSqu));
    //     // addTimeToLog();
    //     // addEndLineLog(Double.toString(runningTime));
    // }

    // private void addInLineLog(String logs){
    //     try {
    //         bw.write(logs + ",");
	// 	} catch (IOException e) {
	// 		e.printStackTrace(); 
	// 	}
    // }

    // private void addEndLineLog(String logs){
    //     try {
    //         bw.write(logs);
    //         bw.newLine();
	// 	} catch (IOException e) {
	// 		e.printStackTrace(); 
	// 	}
    // }

    // private void addTimeToLog(){
    //     cal = Calendar.getInstance();
    //     String timeNow = timeFormat.format(cal.getTime());
    //     addInLineLog(timeNow);
    // }

    // private void logLine(String log){
    //     cal = Calendar.getInstance();
    //     String timeNow = timeFormat.format(cal.getTime());
    //     try {
    //         bw.write(timeNow + "," + log + "\n");
	// 	} catch (IOException e) {
	// 		e.printStackTrace(); 
    //     }
    // }

    // public void saveLog(){
    //     try {
	// 		bw.close();
	// 	} catch (IOException e) {
	// 		e.printStackTrace();
	// 	}
    // }
}
