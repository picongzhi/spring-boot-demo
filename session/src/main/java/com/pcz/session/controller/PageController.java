package com.pcz.session.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.pcz.session.constants.Consts;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author picongzhi
 */
@Controller
@RequestMapping("/page")
public class PageController {
    @GetMapping("/index")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        String token = (String) request.getSession().getAttribute(Consts.SESSION_KEY);
        modelAndView.setViewName("index");
        modelAndView.addObject("token", token);

        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView login(Boolean redirect) {
        ModelAndView modelAndView = new ModelAndView();
        if (ObjectUtil.isNotNull(redirect) && ObjectUtil.equal(true, redirect)) {
            modelAndView.addObject("message", "请先登录!");
        }
        modelAndView.setViewName("login");

        return modelAndView;
    }

    @GetMapping("/doLogin")
    public String doLogin(HttpSession session) {
        session.setAttribute(Consts.SESSION_KEY, IdUtil.fastUUID());

        return "redirect:/page/index";
    }
}
