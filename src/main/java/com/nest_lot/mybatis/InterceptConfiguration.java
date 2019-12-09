package com.nest_lot.mybatis;

import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.nest_lot.config.TenantIntercept;

@Configuration
public class InterceptConfiguration {
    
    @Autowired
    private List<SqlSessionFactory> sqlSessionFactoryList;

    @PostConstruct
    public void addInterceptor() {
        Interceptor pagingInterceptor = new TenantIntercept();
        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
            sqlSessionFactory.getConfiguration().addInterceptor(pagingInterceptor);
        }
    }

}
