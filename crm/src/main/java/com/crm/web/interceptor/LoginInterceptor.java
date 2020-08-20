package com.crm.web.interceptor;

import com.crm.util.PermissionUtils;
import com.crm.util.UserContext;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        //将request绑定到线程中
        UserContext.set(request);

        //判断handler是都是HandlerMethod的对象
        //如果是，才是相当于url请求，否则就是导入一些静态资源
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            String ctp = request.getContextPath();
            if (request.getSession().getAttribute(UserContext.USERINSESSION) == null) {
                response.sendRedirect(ctp + "/login.jsp");
                return false;
            }

            //已经登录了，再进行权限检查，在所有请求必经之路进行权限控制
            String function = handlerMethod.getBean().getClass().getName() + ":" + handlerMethod.getMethod().getName();
            //根据权限表达式查询用户是否有该权限
            Boolean flag = PermissionUtils.checkPermission(function);
            if (flag) {
                //具有权限，直接放行
                return true;
            } else {
                //如果方法上带有@ResponseBody标签
                if (handlerMethod.getMethod().isAnnotationPresent(ResponseBody.class)) {
                    //ajax请求响应
                    response.sendRedirect("nopermission.json");
                } else {
                    //页面请求响应
                    response.sendRedirect("nopermission.jsp");
                }
                return false;
            }
        }
        //导入的是静态资源，直接返回，拦截器也会拦截静态资源，所以要进行如上的判断
        return true;
    }

}
