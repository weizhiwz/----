package com.example.backweb.realm;
import com.example.backweb.common.util.JwtUtil;
import com.example.backweb.pojo.Teacher;
import com.example.backweb.service.impl.AdminServiceImpl;
import com.example.backweb.service.impl.TeacherServiceImpl;
import com.example.backweb.token.JwtToken;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.Set;

@Component
public class MyRealm extends AuthorizingRealm {
    @Autowired
    private TeacherServiceImpl teacherService;
    @Autowired
    private AdminServiceImpl adminService;
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.iterator().next();
        Set<String> roles = new HashSet<String>();
        if(adminService.selectrole(username)) roles.add("admin");
        else roles.add("teacher");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(roles);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        JwtToken jwtToken=(JwtToken) token;
        String jwt =(String) jwtToken.getPrincipal();
        Claims claims = JwtUtil.parseJWT(jwt);
        String username=claims.getId();
        Teacher teacher =teacherService.login(username);
        if(teacher == null){
            return null;
        }
        return new SimpleAuthenticationInfo(teacher.getTea_id(),teacher.getPassword(),getName());
    }
}
