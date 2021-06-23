package study.mvc.basic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {
    //WAS가 reqeust, response 객체를 만들고 서블릿을 호출.서블릿이 호출되면 service 메소드가 호출된다
    @Override
    protected void service(HttpServletRequest reqeust, HttpServletResponse response) throws ServletException, IOException {
        //WAS 별 서블릿 표준 스펙의 구현체
        System.out.println("reqeust = " + reqeust);
        System.out.println("response = " + response);

        //서블릿은 HTTP 쿼리 파라미터를 쉽게 읽도록 지원
        String username = reqeust.getParameter("username");
        System.out.println("username = " + username);

        response.setContentType("text/plain"); //header 정보
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("hello "+username);//http message body에 data가 들어감
    }
}
