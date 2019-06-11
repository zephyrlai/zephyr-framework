package cn.zephyr.demo.controller;

import cn.zephyr.demo.service.DemoService;
import cn.zephyr.webmvc.annoation.Controller;
import cn.zephyr.webmvc.annoation.RequestMapping;

/**
 * @Author: zephyrLai
 * @Description: TODO
 * @Date: 2019/6/11 11:28
 */
@Controller
@RequestMapping
public class DemoController {

    private DemoService demoService;
}
