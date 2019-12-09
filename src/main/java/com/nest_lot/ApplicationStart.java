package com.nest_lot;

import org.apache.activemq.command.ActiveMQQueue;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { MultipartAutoConfiguration.class,DataSourceAutoConfiguration.class })
@ComponentScan("com.nest_lot")
@EnableScheduling
public class ApplicationStart extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationStart.class, args);
		System.out.println("启动成功");
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// 注意这里要指向原先用main方法执行的Application启动类
		return builder.sources(ApplicationStart.class);
	}

	// @Bean
	// public ActiveMQQueue queue(final StringBuffer s1) {
	// s1.append("123");
	// return new ActiveMQQueue("ActivityUuid");
	// }

	/**
	 * 文件上传配置
	 * 
	 * @return
	 */

	@Bean(name = "multipartResolver")
	public MultipartResolver MultipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding("UTF-8");
		// resolver.setResolveLazily(true);//
		// resolveLazily属性启用是为了推迟文件解析，以在在UploadAction中捕获文件大小异常
		resolver.setMaxInMemorySize(40960);
		resolver.setMaxUploadSize(10 * 1024 * 1024);// 上传文件大小 5M 5*1024*1024
		return resolver;
	}

	@Bean
	public ActiveMQQueue queue() {
		// s1.append("123");
		return new ActiveMQQueue("ActivityUuid");
	}

	/*
	 * @Override public void customize(ConfigurableEmbeddedServletContainer
	 * container) { container.setPort(8003);
	 * 
	 * }
	 */
}
