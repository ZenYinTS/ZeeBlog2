package com.crm.util;

import com.crm.domain.Employee;
import com.crm.domain.Log;
import com.crm.service.ILogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

public class LogUtils {
    @Autowired
    private ILogService logService;

    public void writeLog(JoinPoint joinPoint){
        //会出现自己切自己的情况：
        // 切面类切的是所有Service类所有方法，在Service类的方法运行完成后，执行切面类的通知方法
        // 由于通知方法中又调用了Service类的方法，又将会继续切面，再次调用此方法
        // 从而导致网页一直处于加载状态
        //避免这种情况可以先判断是否是ILogService的实例，如果是，就不需要进行切面，直接返回即可
        if(joinPoint.getTarget() instanceof ILogService){
            return;
        }

        //获取request与session
        HttpServletRequest request = UserContext.get();
        HttpSession session = request.getSession();

        Log log = new Log();
        log.setOptime(new Date());
        //ip从request中获取
        log.setOpip(request.getRemoteAddr());
        //用户从session中获取
        log.setOpuser((Employee) session.getAttribute(UserContext.USERINSESSION));
        //使用ObjectMapper将返回的参数对象转化为json字符串存入数据库
        ObjectMapper mapper = new ObjectMapper();

        String params = null;
        try {
            params = mapper.writeValueAsString(joinPoint.getArgs());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        log.setParams(params);
        //获取方法所在的类与方法名
        log.setFunction(joinPoint.getTarget().toString() + ":" + joinPoint.getSignature().getName());
        logService.insert(log);
    }
}
