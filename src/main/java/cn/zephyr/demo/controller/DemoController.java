package cn.zephyr.demo.controller;

import cn.zephyr.demo.service.DemoService;
import cn.zephyr.webmvc.annoation.Autowired;
import cn.zephyr.webmvc.annoation.Controller;
import cn.zephyr.webmvc.annoation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: zephyrLai
 * @Description: TODO
 * @Date: 2019/6/11 11:28
 */
@Controller
@RequestMapping()
public class DemoController {

    @Autowired
    private DemoService demoService;

    @RequestMapping("/index.json")
    public void index(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().write("hello world！");
    }
}
