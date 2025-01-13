package com.example.backweb.shiro;

import com.alibaba.fastjson.JSON;
import com.example.backweb.common.lang.Result;
import com.example.backweb.common.util.JwtUtil;
import com.example.backweb.token.JwtToken;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtFilter extends AuthenticatingFilter {
    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String jwt = request.getHeader("Authorization");
        if(StringUtils.isEmpty(jwt)) {
            return null;
        }
        return new JwtToken(jwt);
    }
    //用于创建基于 JWT 的认证令牌（AuthenticationToken）对象
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String jwt = request.getHeader("Authorization");
        if(StringUtils.isEmpty(jwt)) {
            return false;
        }
        else
        {
            // 校验jwt
            Claims claim = JwtUtil.parseJWT(jwt);
            if(claim == null || JwtUtil.isTokenExpired(claim.getExpiration())) {
                HttpServletResponse response = (HttpServletResponse)servletResponse;
                response.setContentType("application/plain;charset=utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(new Result().setCode(401).setMsg("身份已过期，请重新登录")));
                return false;
            }
            // 执行登录
            return executeLogin(servletRequest, servletResponse);
        }
    }
//当请求访问被拒绝时（比如未通过认证等情况），该方法会被调用，用于处理这种访问拒绝的情况，根据 JWT 的校验结果来决定是返回错误信息给客户端还是继续执行登录认证流程。
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        Throwable throwable = e.getCause() == null ? e : e.getCause();
        Result result = new Result().setMsg(e.getMessage());
        String json = JSON.toJSONString(result);

        try
        {
            httpServletResponse.getWriter().print(json);
        } catch (IOException ioException) {

        }
        return false;
    }
//当登录认证失败时，该方法会被调用，用于处理认证失败的情况，主要是将认证失败的错误信息以 JSON 格式返回给客户端，告知客户端认证失败的原因。
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个OPTIONS请求，这里我们给OPTIONS请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(org.springframework.http.HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}
//该方法在过滤器链处理请求之前被调用，主要用于处理跨域相关的设置以及对 OPTIONS 请求（浏览器在跨域时会先发送的预检请求类型）的特殊处理，确保客户端能够正常跨域访问资源并且在预检请求时返回合适的响应状态。