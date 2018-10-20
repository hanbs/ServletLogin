<%@page import="java.sql.Connection" %>
<%@page import="javax.sql.DataSource" %>
<!--context.xml에서 주입 받음. -->
<%@page import="javax.naming.InitialContext" %>
<%@page import="javax.naming.Context" %>

<%@page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>INSERT TITLE HERE</title>
</head>
<body>
<h4>DB 연동</h4>
<%
Context initContext = new InitialContext();
Context envContext = (Context) initContext.lookup("java:/comp/env");
DataSource ds = (DataSource)envContext.lookup("jdbc/myoracle");
Connection conn = ds.getConnection();
out.print("연결 테스트");

%>
</body>

</html>
