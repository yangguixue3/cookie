package com.example.cookieController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 设置Cookie
 */
@Controller
public class SessionController {
    @ResponseBody
    @RequestMapping("a/login")
    public String setCookie(HttpSession session, HttpServletRequest request){
        session.setAttribute("userInfoSession", "userInfoSession");
        session.setMaxInactiveInterval(60);

        return "添加Session信息成功";
    }

    @RequestMapping("a/logb") // , method = RequestMethod.POST)
    @ResponseBody
    public String logb() {
        Object pp=getSession().getAttribute("userInfoSession");
        String result="1";
        if(pp!=null)
        {
            result=(String) pp;
        }
        return result;
    }

    //获取session缓存内容
    private HttpSession getSession() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        return request.getSession();
    }
}
