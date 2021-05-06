package com.coin.kimp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@ServletComponentScan(basePackages = { "com.coin.kimp.*" })
@SpringBootApplication
public class KimpApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(KimpApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(KimpApplication.class, args);
	}
}
