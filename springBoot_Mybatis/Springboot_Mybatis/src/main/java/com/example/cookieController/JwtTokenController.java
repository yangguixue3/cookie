package com.example.cookieController;

import com.example.entity.User;
import com.example.service.JwtToken;
import com.example.service.MyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 设想: 访问 loginTokenIn,
 * 1 检测 Token，合格则跳转index。不合格跳转loginToken，进行注册后跳转index
 */
@Controller
public class JwtTokenController {
    @Autowired
    MyService myService;
    @Autowired
    JwtToken jwtToken;
    @RequestMapping("/loginToken")
    public String loginToken(Model model){
        User user = new User();
        model.addAttribute(user);
        return "loginToken";
    }

    /**
     *新用户，验证密码，生成token，存入客户cookie
     */
    @RequestMapping("/loginTokenIn")
    public String loginToken(HttpServletRequest request, HttpServletResponse response,
                             @CookieValue("cookieId") String tokenFromcookie,@ModelAttribute(value = "user") User user, Model model) throws UnsupportedEncodingException, JsonProcessingException {

        // 解析 Token ，过期会生成空user
        User user1 = JwtToken.unsign(tokenFromcookie,User.class);
        //判断 cookieId 中的Token 是否合格
        if (tokenFromcookie != null&& user1.getName() != null) {
            System.out.println(user1.toString());
            return "index";
        }

        //  初次登录验证用户名，密码
         User userLogin = myService.getUser(user.getCatId());
        if (userLogin == null) {
            // 用户不存在返回登录页面loginToken
            return "loginToken";
        }
        model.addAttribute(userLogin);
      // 生成Token
        String token = JwtToken.createToken(user,60*20);
        System.out.println(token);
        //token 放入 Cookie
        Cookie cookie = new Cookie("cookieId",token);
        response.addCookie(cookie);
        System.out.println(user.toString());
        return "index";
    }


}
