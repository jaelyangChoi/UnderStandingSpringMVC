package study.mvc.web.frontcontroller.v5;

import study.mvc.web.frontcontroller.ModelView;
import study.mvc.web.frontcontroller.MyView;
import study.mvc.web.frontcontroller.v3.controller.MemberFormControllerV3;
import study.mvc.web.frontcontroller.v3.controller.MemberListControllerV3;
import study.mvc.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import study.mvc.web.frontcontroller.v4.controller.MemberFormControllerV4;
import study.mvc.web.frontcontroller.v4.controller.MemberListControllerV4;
import study.mvc.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import study.mvc.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import study.mvc.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;

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
    private final Map<String, Object> handlerMappingMap = new HashMap<>(); //V3든 V4든 수용할 수 있도록 Object로 받는다.
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    //생성자는 핸들러 매핑과 어댑터를 초기화(등록)한다.
    public FrontControllerServletV5() {
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object handler = getHandler(request);
        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        //어댑터를 끼워 핸들러(컨트롤러) 실행
        MyHandlerAdapter adapter = getHandlerAdapter(handler);
        ModelView modelView = adapter.handle(request, response, handler);

        MyView view = viewResolver(modelView.getViewName());
        view.render(modelView.getModel(), request, response);
    }

    /* 새로운 컨트롤러와 그에 맞는 어댑터가 추가되더라도 이 부분만 수정하면 된다! 뼈대를 흔들지 않는다! */
    //url에 매핍되는 핸들러를 반환
    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());

        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
    }

    // 각 핸들러마다의 어댑터를 담아둔다.
    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }

    //핸들러를 처리할 수 있는 어댑터 조회
    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        for (MyHandlerAdapter adapter : handlerAdapters)
            if (adapter.supports(handler))
                return adapter;
        throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다. handler = " + handler);
    }

    //핸들러 매핑 정보인 handlerMappingMap 에서 URL에 매핑된 핸들러(컨트롤러) 객체를 찾아서 반환
    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return handlerMappingMap.get(requestURI);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
}
