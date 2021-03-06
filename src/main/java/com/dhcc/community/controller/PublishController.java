package com.dhcc.community.controller;

import com.dhcc.community.mapper.QuestionMapper;
import com.dhcc.community.mapper.UserMapper;
import com.dhcc.community.model.Question;
import com.dhcc.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag,
                            HttpServletRequest request,
                            Model model) {
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if ((title==null)||(title.equals(""))) {
            model.addAttribute("error","标题不能为空");
            return "/publish";
        }
        if ((description==null)||(description.equals(""))) {
            model.addAttribute("error","问题描述不能为空");
            return "/publish";
        }
        if ((tag==null)||(tag.equals(""))) {
            model.addAttribute("error","标签不能为空");
            return "/publish";
        }
        User user = null;
        Cookie[] cookies = request.getCookies();
        if ((cookies!=null)&&(cookies.length!=0)) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    user = userMapper.findByToken(cookie.getValue());
                    request.getSession().setAttribute("user", user);
                    break;
                }
            }
        }
        if (user == null) {
            model.addAttribute("error","用户未登录");
            return "/publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        question.setCreator(user.getId());
        questionMapper.create(question);
        return "redirect:/";
    }
}
