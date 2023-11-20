package com.ssafy.haru;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@MapperScan(basePackages = "com.ssafy.haru.model.dao")
public class HaruApplication {

	public static void main(String[] args) {
		SpringApplication.run(HaruApplication.class, args);
	}

	@Bean
	public Docket postsApi(){
		final ApiInfo apiInfo = new ApiInfoBuilder()
				.title("Final Project rest API")
				.description("<h1>4조 최종 관통 프로젝트 서버.</h1>")
				.license("MIT License")
				.version("1.0")
				.build();

		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("ssafyFinalProject")
				.apiInfo(apiInfo)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.ssafy.haru.api"))
				.build();
	}
}
