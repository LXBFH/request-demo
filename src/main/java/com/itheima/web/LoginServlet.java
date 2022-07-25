package com.itheima.web;

/**
 * @Author LIXUBO
 * @Date 2022-07-25 22:36
 * @description
 * @Version 1.0
 */

import com.itheima.mapper.USerMapper;
import com.itheima.pojo.User;
import com.itheima.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置字符输入流的编码
        request.setCharacterEncoding("UTF-8");

        //1. 接收用户名和密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        // 2.调用mybatis完成查询
        // 2.1 获取 sqlSessionFactory对象
//        String resource = "mybatis-config.xml";
//        InputStream inputStream = Resources.getResourceAsStream(resource);
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        // 2.2 获取 sqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 2.3 获取mapper
        USerMapper userMapper = sqlSession.getMapper(USerMapper.class);
        // 2.4 调用方法
        User user = userMapper.select(username, password);
        // 2.5 释放资源
        sqlSession.close();

        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        // 3. 判断user是否为null
        if (user != null) {
            //登录成功
            writer.write("登录成功");
        } else {
            //登录失败
            writer.write("登录失败");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
