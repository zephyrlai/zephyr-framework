package cn.zephyr.webmvc.servlet;

import cn.zephyr.webmvc.annoation.Controller;
import cn.zephyr.webmvc.annoation.Service;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * @Author: zephyrLai
 * @Description: TODO
 * @Date: 2019/6/11 11:09
 */
public class DispatchServlet extends HttpServlet {

    // 存储配置文件中的配置
    private Properties contextConfig = new Properties();
    // 保存全部的类名
    private List<String> classNameList = new ArrayList<String>();
    // 存储类名与对应实例的IOC容器
    private Map<String,Object> iocMap = new HashMap<String, Object>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    /**
     * 1. 加载配置文件
     * 2. 扫描
     * 3. 实例化
     * 4. 依赖注入
     * 5. 初始化HandlerMapping
     * 6. 接收请求
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        // 1. 加载配置文件
        doLoadConfig(config.getInitParameter("contextConfigLocation"));
        // 2. 扫描所有相关的类
        doScanner(contextConfig.getProperty("scanPackage"));
        // 3. 初始化所有关联的类，并将所有扫描到的类实例化到IOC容器中
        doInstance();
        // 4. 自动化的依赖注入
        doAutowired();
        // 5. 初始化HandlerMapping
        initHandlerMapping();
    }

    private void doLoadConfig(String contextConfigLocation) {
        // 拿到Spring配置文件的路径，读取配置文件中的所有内容
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
        try {
            contextConfig.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(null != inputStream)
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    private void doScanner(String scanPackage) {
        URL url = this.getClass().getClassLoader().getResource(scanPackage.replace("\\.", "/"));
        File classDir = new File(url.getFile());
        for (File file : classDir.listFiles()) {
            if(file.isDirectory())
                doScanner(scanPackage+"."+file.getName());
            else{
                String className = (scanPackage+"."+file.getName()).replace(".class","");
                classNameList.add(className);
            }
        }

    }

    private void doInstance() {
        if(classNameList.isEmpty())
            return ;
        else{
            for (String className : classNameList) {
                try {
                    Class<?> clazz = Class.forName(className);
                    if(clazz.isAnnotationPresent(Controller.class)){
                        Object instance = clazz.newInstance();
                        String key = lowerFirstLetter(clazz.getSimpleName());
                        iocMap.put("",instance);
                    }else if(clazz.isAnnotationPresent(Service.class)){

                        // 1. 优先使用自定义的beanId(默认的beanId是类名首字母小写)
                        Service service = clazz.getAnnotation(Service.class);
                        String beanName = service.value();
                        if( "".equals(beanName.trim()))
                            beanName = lowerFirstLetter(clazz.getSimpleName());
                        Object ins = clazz.newInstance();
                        iocMap.put(beanName,ins);
                        // 2. 根据接口类型来实例化
                        Class<?>[] interfaces = clazz.getInterfaces();
                        for (Class<?> i : interfaces) {
                            iocMap.put(i.getName(),ins);
                        }
                    }else{
                        continue;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String lowerFirstLetter(String simpleName) {
        char[] chars = simpleName.toCharArray();
        // 利用ASCII码，大写字母与小写字母的值相差32
        chars[0] +=32;
        return String.valueOf(chars);
    }

    private void doAutowired() {
    }

    private void initHandlerMapping() {
    }

}
