package com.dhcc.community.controller;

import com.dhcc.community.mapper.UserMapper;
import com.dhcc.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("token")) {
                User user = userMapper.findByToken(cookie.getValue());
                request.getSession().setAttribute("user",user);
                break;
            }
        }
        return "index";
    }
}
