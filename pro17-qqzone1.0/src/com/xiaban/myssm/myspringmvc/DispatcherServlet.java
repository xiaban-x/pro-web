package com.xiaban.myssm.myspringmvc;

import com.xiaban.myssm.io.BeanFactory;
import com.xiaban.myssm.io.ClassPathXmlApplicationContext;
import com.xiaban.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@WebServlet("*.do")
public class DispatcherServlet extends ViewBaseServlet {

    private BeanFactory beanFactory;

    public DispatcherServlet() {
        beanFactory = new ClassPathXmlApplicationContext();
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("utf-8");

        //假设url是：http://localhost:8080/pro11/hello.do
        //servletPath是：/hello.do
        //现在的思路是
        //第一步：/hello.do -> hello
        //第二步： 通过配置文件让 hello 和 HelloController对应   ==》  fruit 和 fruitController对应
        String servletPath = req.getServletPath();//得到的是  /*.do  *是访问的对象的名字 如访问pro11/hello.do  那么servletPath就是/hello.do
        //System.out.println(servletPath);
        servletPath = servletPath.substring(1);
        int lastDotIndex = servletPath.lastIndexOf(".do");

        servletPath = servletPath.substring(0, lastDotIndex);
        //System.out.println(servletPath);
        Object controllerBeanObj = beanFactory.getBean(servletPath);


        String operate = req.getParameter("operate");
        if (StringUtil.isEmpty(operate)) {
            operate = "index";
        }

        //获取当前类中所有的方法
        try {
            Method[] methods = controllerBeanObj.getClass().getDeclaredMethods();
            for (Method m : methods) {
                //获取方法名称
                String methodName = m.getName();
                if (operate.equals(methodName)) {
                    //1.统一获取请求参数

                    //1.1获取当前方法的参数，获取参数数组
                    Parameter[] parameters = m.getParameters();
                    //1.2parameterValues用来承载参数的值
                    Object[] parameterValues = new Object[parameters.length];
                    for (int i = 0; i < parameters.length; i++) {
                        Parameter parameter = parameters[i];
                        String parameterName = parameter.getName();
                        if ("req".equals(parameterName)) {
                            //如果参数名是req，resp，session，那么就不是通过请求中获取参数的形式了
                            parameterValues[i] = req;
                        } else if ("session".equals(parameterName)) {
                            parameterValues[i] = req.getSession();
                        } else {
                            //从请求中获取参数值
                            String parameterValue = req.getParameter(parameterName);
                            String typeName = parameter.getType().getName();

                            Object parameterObj = parameterValue;
                            if (parameterObj != null) {
                                if ("java.lang.Integer".equals(typeName)) {
                                    if (parameterObj == ""){
                                        parameterObj = 0;
                                    }else{
                                        parameterObj = Integer.parseInt(parameterValue);
                                    }
                                }
                            }
                            parameterValues[i] = parameterObj;
                        }
                    }
                    m.setAccessible(true);
                    //2.找到和operate同名字的方法，然后通过反射调用
                    Object methodReturnObj = m.invoke(controllerBeanObj, parameterValues);

                    //3.视图处理
                    String methodReturnStr = (String) methodReturnObj;
                    if (methodReturnStr.startsWith("redirect:")) { // 比如：redirect:fruit.do
                        String redirectStr = methodReturnStr.substring("redirect:".length());
                        resp.sendRedirect(redirectStr);
                    } else { // 比如 "edit"
                        super.processTemplate(methodReturnStr, req, resp);
                    }
                    return;
                }
            }
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            throw new DispatcherServletException("DispatcherServlet 报错了");
        }

        throw new RuntimeException("operate非法之");

//        switch(operate){
//            case "index":
//                index(req,resp);
//                break;
//            case "add":
//                add(req,resp);
//                break;
//            case "del":
//                del(req,resp);
//                break;
//            case "edit":
//                edit(req,resp);
//                break;
//            case "update":
//                update(req,resp);
//                break;
//            default:
//                throw new RuntimeException("operate非法之");
//        }
    }
}
