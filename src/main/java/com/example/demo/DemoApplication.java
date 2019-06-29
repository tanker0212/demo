package com.example.demo;

import java.util.Timer;
import java.util.TimerTask;

import com.example.demo.service.Monitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {


	static Monitor monitor = new Monitor();

	public static void main(String[] args) {
		// Timer timer = new Timer();
		// TimerTask task = new TimerTask(){
		
		// 	@Override
		// 	public void run() {
		// 		System.out.println(monitor.showCPU()); 
		// 	}
		// };

		// timer.schedule(task, 100);

		SpringApplication.run(DemoApplication.class, args);

		

		
	}

}
