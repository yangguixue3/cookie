package com.example.cookieController;

import com.example.entity.User;
import com.example.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/sessionTest")
public class LoginSessionController {

    @Autowired
    MyService myService;

    @ModelAttribute
    public void before(HttpServletRequest request, HttpServletResponse response, Model model){
        HttpSession session = request.getSession();
        //获取sessionid
        String sessionId = session.getId();
        String SessionValue=(String) session.getAttribute("userInfoSession");
        System.out.println(sessionId);
        System.out.println(SessionValue);
        if (SessionValue == null) {
            //创建用户，假设用户创建为id = 1
            User user = myService.getUser(1);
            //设置Session
            setSession(session);
            // Model 添加用户信息
            model.addAttribute(user);
        }
        if (SessionValue != null) {
            // 根据SessionValue 查询 用户信息。假设 根据SessionValue=2
            User user = myService.getUser(2);
            model.addAttribute(user);
        }
    }

    @RequestMapping("/login")
    public String setCookie(HttpSession session,Model model){

        System.out.println(model);
        return "index";
    }
    @RequestMapping("/deleteSession")
    @ResponseBody
    public String deleteSession(){
        System.out.println("ok");
        return "1";
    }

    /**
     * 设置 用户 session
     * @param session
     */
    private void setSession(HttpSession session) {
        session.setAttribute("userInfoSession", "testSession");
        session.setMaxInactiveInterval(60);
    }

    @RequestMapping("/getUserInfo/{id}")
    @ResponseBody
    public String getUser(@PathVariable int id){
        User user = myService.getUser(id);
        System.out.println(user.toString());
        return user.toString();
    }

}
