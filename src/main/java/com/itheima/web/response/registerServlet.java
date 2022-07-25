package com.itheima.web.response;

/**
 * @Author LIXUBO
 * @Date 2022-07-25 22:38
 * @description
 * @Version 1.0
 */

import com.itheima.mapper.USerMapper;
import com.itheima.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@WebServlet("/registerServlet")
public class registerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("UTF-8");
        //接收用户数据
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        //封装用户对象
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        // 调用mapper ，根据用户名查询用户对象
        // 2.1 获取 sqlSessionFactory对象
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        // 2.2 获取 sqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 2.3 获取mapper
        USerMapper userMapper = sqlSession.getMapper(USerMapper.class);
        // 2.4 调用方法
        User user1 = userMapper.selectByUsername(username);

        // 判断用户对象是否为null
        if (user1 == null) {
            //用户名不存在，添加用户
            userMapper.add(user);
            //提交事务
            sqlSession.commit();
            //释放资源
            sqlSession.close();
        } else {
            //用户名已经存在，给出提示
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write("用户名已经存在");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
