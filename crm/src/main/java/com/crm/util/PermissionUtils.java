package com.crm.util;

import com.crm.domain.Employee;
import com.crm.domain.Permission;
import com.crm.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.List;

@Component
public class PermissionUtils {
    //被static修饰变量，是部署于任何实例化的对象拥有，
    // spring的依赖注入只能在对象层级上进行依赖注入，所以不能直接使用@Autowired标签进行注入。
    private static IPermissionService permissionService;

    //解决方法： 在该变量的set方法上添加@Autowired注解，再将变量注入
    @Autowired
    public void setPermissionService(IPermissionService permissionService){
        this.permissionService = permissionService;
    }
    public static Boolean checkPermission(String function) {
        //超级管理员，直接放行
        HttpSession session = UserContext.get().getSession();
        Employee emp = (Employee)session.getAttribute(UserContext.USERINSESSION);
        if (emp.getAdmin())
            return true;

        //获取数据库中的所有权限，存入集合中
        if (CommonUtils.allPermissions.size()==0){
            List<Permission> permissions = permissionService.selectAll();
            for (Permission permission:permissions){
                CommonUtils.allPermissions.add(permission.getResource());
            }
        }

        //判断表达式是否需要权限控制
        //如果function权限表达式存在数据库的权限集合中，说明需要进行权限控制
        if (CommonUtils.allPermissions.contains(function)){
            //首先获取用户的权限，从session中获取
            List<String> userPermissions = (List<String>) session.getAttribute(UserContext.PERMISSIONINSESSION);

            if (userPermissions == null)
                return false;

            //完全匹配
            if (userPermissions.contains(function)){
                return true;
            }else{
                //All权限匹配
                String allPermission = function.split(":")[0]+":ALL";
                if (userPermissions.contains(allPermission)){
                    return true;
                }else
                    return false;
            }
        }else
            return true;
    }
}
