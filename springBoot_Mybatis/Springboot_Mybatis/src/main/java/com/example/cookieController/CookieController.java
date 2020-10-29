package com.example.cookieController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 设置Cookie
 */
@Controller
public class CookieController {
    @ResponseBody
    @RequestMapping(value="Cookie",method = RequestMethod.GET)
    public String setCookie(HttpServletResponse response){
        Cookie cookie = new Cookie("cookieId","1");
//        cookie.setMaxAge(7 * 24 * 60 * 60); // 7天过期
        cookie.setMaxAge(60); //
        response.addCookie(cookie);
        return "添加cookies信息成功";
    }

    /**
     * 普通方法获取 cookie
     * @param Request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value="getCookie",method = RequestMethod.GET)
    public String getCookie(HttpServletRequest Request, HttpServletResponse response){
        Cookie[] cookies = Request.getCookies();
        if (cookies != null) {
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("cookieId")){
                        cookie.getValue();
                        System.out.println(cookie.getValue());
                    return cookie.getValue();
                }
            }

        }
        return null;
    }

    /**
     * 注解拿cookie
     * @param cookieId
     * @return
     */
    @RequestMapping("/testCookieValue")
    @ResponseBody
    public String testCookieValue(@CookieValue("cookieId") String cookieId ) {
        //把对应的key值拿出来了。
        System.out.println("testCookieValue的cookieId="+cookieId);


        return "SUCCESS";
    }
}
