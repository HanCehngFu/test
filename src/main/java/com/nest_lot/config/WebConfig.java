package com.nest_lot.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.nest_lot.model.SysUserBean;

import cn.ocoop.framework.sql.TC;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

	@Bean
	public ViewResolver getViewResolver() {
		InternalResourceViewResolver internalResourceView = new InternalResourceViewResolver();
		internalResourceView.setPrefix("/WEB-INF/views/");
		internalResourceView.setSuffix(".jsp");
		internalResourceView.setExposeContextBeansAsAttributes(true);
		return internalResourceView;

	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
		super.configureDefaultServletHandling(configurer);
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		HandlerInterceptor interceptor =new HandlerInterceptor() {

			@Override
			public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
					throws Exception {
				SysUserBean sysUserBean = new SysUserBean();
				try {
					BeanUtils.copyProperties(sysUserBean, SecurityUtils.getSubject().getPrincipal());
				} catch (Exception e) {
				}
				if (sysUserBean.getTenant() != null && sysUserBean.getTenant().intValue()>0 ) {
					TC.set(sysUserBean.getTenant());
				}
				
				return true;
			}

			@Override
			public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
					ModelAndView modelAndView) throws Exception {
				TC.clear();
			}

			@Override
			public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
					Exception ex) throws Exception {
			}
			
		};
		registry.addInterceptor(interceptor);
		super.addInterceptors(registry);
	}

}
