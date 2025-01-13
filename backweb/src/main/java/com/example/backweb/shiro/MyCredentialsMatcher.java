package com.example.backweb.shiro;

import com.example.backweb.token.JwtToken;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Component;

@Component
public class MyCredentialsMatcher extends SimpleCredentialsMatcher {
    //自定义密码加密校验器
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        JwtToken jwtToken = (JwtToken) token;
        if(jwtToken.getPassword()==null) return true;
        String inPassword= new Md5Hash(jwtToken.getPassword(),"",3).toString();
        String dbPassword = String.valueOf(info.getCredentials());
        return this.equals(inPassword,dbPassword);
    }
}
//该方法是整个类的核心，用于进行密码校验操作，它重写了父类 SimpleCredentialsMatcher 的 doCredentialsMatch 方法，实现了自定义的密码比对逻辑
// 判断用户输入的密码（经过一定处理后）与存储在数据源中的密码是否一致，以此来确定认证是否能够成功通过。