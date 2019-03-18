package com.clnews.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

/**
 * @program: cl-news
 * @description:
 * @analysis:
 * @author: 李学亮    email: 18222027300@163.com
 * @create: 2019-03-16 23:02
 **/
@Configuration
public class DataSourceConfiguration {
    @Autowired
    public DataSourceConfiguration(Environment env) {
        this.env = env;
    }

    private final Environment env;

    //destroy-method="close"的作用是当数据库连接不使用的时候,就把该连接重新放到数据池中,方便下次使用调用.

    @Bean
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        //用户名
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        //密码
        dataSource.setPassword(env.getProperty("spring.datasource.password"));
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        //初始化时建立物理连接的个数
        dataSource.setInitialSize(2);
        //最大连接池数量
        dataSource.setMaxActive(20);
        //最小连接池数量
        dataSource.setMinIdle(0);
        //获取连接时最大等待时间，单位毫秒。
        dataSource.setMaxWait(60000);
        //用来检测连接是否有效的sql
        dataSource.setValidationQuery("SELECT 1");
        //申请连接时执行validationQuery检测连接是否有效
        dataSource.setTestOnBorrow(false);
        //建议配置为true，不影响性能，并且保证安全性。
        dataSource.setTestWhileIdle(true);
        //是否缓存preparedStatement，也就是PSCache
        dataSource.setPoolPreparedStatements(false);
        return dataSource;
    }
}
