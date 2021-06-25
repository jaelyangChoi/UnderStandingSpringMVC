package study.mvc.web.frontcontroller.v2.controller;

import study.mvc.domain.member.Member;
import study.mvc.domain.member.MemberRepository;
import study.mvc.web.frontcontroller.MyView;
import study.mvc.web.frontcontroller.v2.ControllerV2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MemberListControllerV2 implements ControllerV2 {
    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //비즈니스 로직
        List<Member> members = memberRepository.findAll();

        //Model에 저장
        request.setAttribute("members", members);

        //View로 forwarding
        return new MyView("/WEB-INF/views/members.jsp");
    }
}
