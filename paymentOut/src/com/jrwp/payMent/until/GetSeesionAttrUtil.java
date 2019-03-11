package com.jrwp.payMent.until;

import com.jrwp.core.entity.Core_Dept;
import com.jrwp.core.entity.Core_User;
import com.jrwp.core.service.IDeptService;
import com.jrwp.core.service.IUserService;

import javax.servlet.http.HttpSession;

public class GetSeesionAttrUtil {

    private static IUserService userService ;

    private static IDeptService deptService ;


    public static Core_User  getUser(HttpSession  session){
        Core_User user = (Core_User) session.getAttribute("user");
        user=userService.getByUserName(user.getUserName());
        return  user;
    }

    public static Long getCurrentDeptId(HttpSession session){
        Core_User user = (Core_User) session.getAttribute("user");
        Long deptid = userService.getdeptid(user.getUserName());
        return  deptid;
    }

    public static String[] getChildDeptids(HttpSession session){
        Core_User user = (Core_User) session.getAttribute("user");
        Long deptid = userService.getdeptid(user.getUserName());

        Core_Dept dept = deptService.getObjectById(deptid);
        String ordercode = dept.getOrderCode();
        // String deptcode = dept.getDeptCode();
        String code = ordercode + "%";
        String[] deptids = deptService.getAllchild(code);
        return  deptids;
    }

}
