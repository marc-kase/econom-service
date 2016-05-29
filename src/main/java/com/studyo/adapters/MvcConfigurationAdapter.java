package com.studyo.adapters;

import com.studyo.config.DbConfiguration;
import com.studyo.services.DbServiceBuilder;
import com.studyo.services.DbServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class MvcConfigurationAdapter extends WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter {
    public static void main(String[] args) {
        SpringApplication.run(MvcConfigurationAdapter.class, args);
    }

    @Autowired
    private DbConfiguration dbConfiguration;

    @Bean(name = "dataSource")
    public DbServiceManager dataSource() {
/*        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(dbConfiguration.getDatabaseDriverClassName());
        driverManagerDataSource.setUrl(dbConfiguration.getDatasourceUrl());
        driverManagerDataSource.setUsername(dbConfiguration.getDatabaseUsername());
        driverManagerDataSource.setPassword(dbConfiguration.getDatabasePassword());*/

        return new DbServiceBuilder().newBuilder(dbConfiguration).build();
    }

    @Bean(name="userDetailsService")
    public UserDetailsService userDetailsService(){
        JdbcDaoImpl jdbcImpl = new JdbcDaoImpl();
        jdbcImpl.setDataSource(dataSource());
        jdbcImpl.setUsersByUsernameQuery("select username,password,enabled from users where username=?");
        jdbcImpl.setAuthoritiesByUsernameQuery("select b.username, a.role from roles a, users b where b.username=? and a.user_id=b.id");
        return jdbcImpl;
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Bean
    ProtobufHttpMessageConverter protobufHttpMessageConverter() {
        return new ProtobufHttpMessageConverter();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/hello").setViewName("hello");
    }
}
