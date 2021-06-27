package study.mvc.web.frontcontroller.v5;

import study.mvc.web.frontcontroller.ModelView;
import study.mvc.web.frontcontroller.MyView;
import study.mvc.web.frontcontroller.v3.controller.MemberFormControllerV3;
import study.mvc.web.frontcontroller.v3.controller.MemberListControllerV3;
import study.mvc.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import study.mvc.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {
    //private Map<String, ControllerV4> controllerMap = new HashMap<>(); 이전 버전
    private final Map<String, Object> handlerMappingMap = new HashMap<>(); //V3든 V4든 수용할 수 있도록
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerServletV5() {
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
    }

    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object handler = getHandler(request);
        if (handler == null) { //url에 해당하는 컨트롤러가 없을 경우 404 에러
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        //어댑터를 끼워 핸들러(컨트롤러) 실행
        MyHandlerAdapter adapter = getHandlerAdapter(handler);
        ModelView modelView = adapter.handle(request, response, handler);

        MyView view = viewResolver(modelView.getViewName());
        view.render(modelView.getModel(), request, response);
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        for (MyHandlerAdapter adapter : handlerAdapters)
            if (adapter.supports(handler))
                return adapter;
        throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다. handler = " + handler);
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return handlerMappingMap.get(requestURI);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
}
