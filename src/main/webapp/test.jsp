<%@page import="java.sql.Connection" %>
<%@page import="DAO.MemberDAO" %>
<%@page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>INSERT TITLE HERE</title>
</head>
<body>
<%
MemberDAO memDao = MemberDAO.getInstance();
Connection conn = memDao.getConnection();
out.print("성공");
%>
</body>

</html>
