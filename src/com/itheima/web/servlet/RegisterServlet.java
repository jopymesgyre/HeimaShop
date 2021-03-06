package com.itheima.web.servlet;

import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.service.impl.UserServiceImpl;
import com.itheima.utils.MailUtils;
import com.itheima.utils.UUIDUtils;
import com.itheima.utils.UserBeanUtils;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;


/**
 * Created by 13718 on 2017/8/28.
 */
@WebServlet(name = "RegisterServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        User user = new User();

        try {
            UserBeanUtils.populate(user, request.getParameterMap());
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        user.setUid(UUIDUtils.getUUID());
        user.setTelephone(null);
        user.setCode(UUIDUtils.getUUID());
        user.setState(0);

        UserService userService = new UserServiceImpl();
        int i = 0;
        try {
            i = userService.regist(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (i>0){
            request.setAttribute("msg","注册成功，请邮件激活后登陆");

            try {
                MailUtils.sendMail(user.getEmail(),user.getCode());
            } catch (MessagingException e) {
                e.printStackTrace();
            }

            response.sendRedirect(request.getContextPath()+"/registerSuccess.jsp");

        }else {
            response.sendRedirect(request.getContextPath()+"/registerFail.jsp");
        }



//        return "/jsp/longin.jsp";
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
