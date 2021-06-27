package study.mvc.web.frontcontroller.v5;

import study.mvc.web.frontcontroller.ModelView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//어댑터는 handler(컨트롤러)를 호출하고 그 결과를 어댑터에 맞추어 반환한다
public interface MyHandlerAdapter {
    //어댑터가 해당 컨트롤러를 처리할 수 있는지 판단
    boolean supports(Object handler);

    //어댑터는 컨트롤러를 호출하고, 그 결과로 ModelView를 반환해야 한다.
    ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException;
}
