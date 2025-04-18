package com.wzy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.wzy.mapper")
@SpringBootApplication()
public class Main {
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}
