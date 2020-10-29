package com.example.cookieController;

import com.example.entity.SysUser;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class LoginController {

    @Autowired
    private UserService userService;


    /**
     *  判断cookieId是否存在，不存在->注册 login.html，存在-> 首页 index.html
     * @param cookieId
     * @return
     */


    @GetMapping("/login")
    public String login(Model model,@CookieValue(value = "cookieId",defaultValue = "noCookieId")  String cookieId){
        if (cookieId != null&& (!cookieId.equals("noCookieId")) ) {
            System.out.println(cookieId);
            model.addAttribute("name",cookieId);
            return "index";
        }
        model.addAttribute(new SysUser());
        return "login";
    }

    /**
     * 注册账号，创建cookie
     * @param sysUser
     * @param model
     * @return
     */
    @PostMapping("/logging")
    public String logging(@ModelAttribute("sysUser") SysUser sysUser, Model model, HttpServletResponse response){
        if (sysUser != null) {
            // 用户信息存储
            System.out.println(sysUser.getUsername());
            model.addAttribute("name",sysUser.getUsername());
            // 设置 Cookie，设置时间
            Cookie cookie = new Cookie("cookieId","1");
            cookie.setMaxAge(60); // 60秒
//            cookie.setSecure(true);  //Https 安全cookie
            cookie.setHttpOnly(true);  //不能被js访问的Cookie
            response.addCookie(cookie);
            return "index";
        }
        System.out.println("ssss");
        return "index";
    }

    /**
     * cookie 时间设置为零
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/logout")
    @ResponseBody
    public String logout (HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for (Cookie cookie : cookies){
                if ((cookie.getName()) .equals("cookieId")) {
                    System.out.println(cookie.getName());
                }
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        return "退出";
    }
}
