package com.easycloudstorage.controller;

import com.easycloudstorage.pojo.Test;
import com.easycloudstorage.service.TestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class TestController {
    @Autowired
    private TestService testService;

    @RequestMapping("testPage")
    public ModelAndView list() {
        List<Test> tests = testService.list();

        ModelAndView mv = new ModelAndView();
        mv.setViewName("test/testPage");
        mv.addObject("tests", tests);

        return mv;
    }
}
