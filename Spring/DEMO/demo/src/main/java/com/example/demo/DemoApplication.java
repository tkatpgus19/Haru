package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@MapperScan(basePackages = "com.example.demo.model.dao")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public Docket postsApi(){
		final ApiInfo apiInfo = new ApiInfoBuilder()
				.title("Final Project rest API")
				.description("<h1>테스트 용도입니다.</h1>")
				.license("MIT License")
				.version("1.0")
				.build();

		Docket docket = new Docket(DocumentationType.SWAGGER_2)
				.groupName("ssafyFinalProject")
				.apiInfo(apiInfo)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.example.demo.controller"))
				.build();
		return docket;
	}
}
