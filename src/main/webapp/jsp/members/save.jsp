<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="study.mvc.domain.member.Member" %>
<%@ page import="study.mvc.domain.member.MemberRepository" %>
<%
    //request, response는 그냥 사용 가능. 왜냐면 jsp도 결국 서블릿으로 변환되기 때문.
    MemberRepository memberRepository = MemberRepository.getInstance();
    String username = request.getParameter("username");
    int age = Integer.parseInt(request.getParameter("age"));

    Member member = new Member(username, age);
    memberRepository.save(member);
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
성공
<ul>
    <li>id = <%=member.getId()%></li>
    <li>username = <%=member.getUsername()%></li>
    <li>age = <%=member.getAge()%></li>
</ul>
<a href="/index.html">메인</a>
</body>
</html>