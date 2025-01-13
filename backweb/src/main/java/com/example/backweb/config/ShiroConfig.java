package com.example.backweb.config;

import com.example.backweb.realm.MyRealm;
import com.example.backweb.shiro.JwtFilter;
import com.example.backweb.shiro.MyCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//import javax.servlet.Filter;
import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig{
    @Autowired
    private MyRealm myRealm;
    @Autowired
    private MyCredentialsMatcher myCredentialsMatcher;
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(){
        DefaultWebSecurityManager manager=new DefaultWebSecurityManager();
        myRealm.setCredentialsMatcher(myCredentialsMatcher);
        manager.setRealm(myRealm);
        return manager;
    }
    @Bean
    public  JwtFilter getJwtFilter(){
        return new JwtFilter();
    }
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager manager){
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        filterFactoryBean.setSecurityManager(manager);
        //加自定义filter
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("jwt",new JwtFilter());
        filterFactoryBean.setFilters(filterMap);
        //加拦截路径
        Map<String,String> map = new LinkedHashMap<>();
//        map.put("/user/logout", "logout");
        map.put("/user/login","anon");
        map.put("/user/enroll","anon");
        map.put("/user/forget","anon");
//        map.put("/user/wohao","authc");
        map.put("/swagger-ui.html**", "anon");
        map.put("/v2/api-docs", "anon");
        map.put("/swagger-resources/**", "anon");
        map.put("/webjars/**", "anon");
        map.put("/**", "jwt"); // 使用过滤器名称
        filterFactoryBean.setFilterChainDefinitionMap(map);
        return filterFactoryBean;
    }
    // 开启注解代理
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}