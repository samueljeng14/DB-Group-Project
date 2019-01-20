<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, test.ReturnCites" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>GoogleSearch</title>
</head>
<body>
<blockquote>
<%
ArrayList<ReturnCites> a = (ArrayList<ReturnCites>)  request.getAttribute("query");
for(int i =0 ; i < a.size();i++){%>
 <a href='<%= a.get(i).EachCite %>'><%= a.get(i).EachTitle %></a><br><h style="font-size:5px ;"><%= a.get(i).EachCite %></h><br><br>
<%
}
%>
</blockquote>
</body>
</html>