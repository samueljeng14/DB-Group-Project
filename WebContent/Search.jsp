<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>GoogleSearch</title>
</head>
<body>

<style>

#search{
color:white;
background-color:blue;
border:none;
padding:3px 8px;
}

</style>

<center>
<form action='${requestUri}' method='get'>
<input type='text' name='keyword' placeholder = 'keyword'/>
<input id="search" type='submit' value='search' />
</form>
</center>
</body>
</html>