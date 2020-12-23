<%--<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>--%>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%--<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>--%>

<%--<c:set var="contextPath" value="${pageContext.request.contextPath}"/>--%>

<%--<!DOCTYPE html>--%>
<%--<html lang="en">--%>
<%--<head>--%>
<%--    <meta charset="utf-8">--%>
<%--    <meta http-equiv="X-UA-Compatible" content="IE=edge">--%>
<%--    <meta name="viewport" content="width=device-width, initial-scale=1">--%>
<%--    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->--%>
<%--    <meta name="description" content="">--%>
<%--    <meta name="author" content="">--%>

<%--    <title>Authentication</title>--%>

<%--    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">--%>
<%--</head>--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Insert title here</title>
</head>

<body>

<%--<div class="container">--%>

<%--    <form method="POST" action="/authenticate" class="form-signin" modelAttribute="authenticationRequest">--%>
<%--        <h2 class="form-heading">Authenticate!</h2>--%>

<%--        <div class="form-group ${error != null ? 'has-error' : ''}">--%>
<%--            <span>${msg}</span>--%>
<%--            <input name="username" type="text" class="form-control" placeholder="Your Email here"--%>
<%--                   autofocus="true"/>--%>
<%--            <input name="password" type="password" class="form-control" placeholder="Your Fingerprint here"/>--%>
<%--            <span>${errorMsg}</span>--%>

<%--            <button class="btn btn-lg btn-primary btn-block" type="submit">Open-Door</button>--%>
<%--        </div>--%>
<%--    </form>--%>
<%--    <form action="/register" class="inline">--%>
<%--        <button class="btn btn-lg btn-primary btn-block">Register as an new User</button>--%>
<%--    </form>--%>

<%--</div>--%>
<%--<!-- /container -->--%>

<div align="center">
    <h1>Authenticate</h1>
    <form action="/authenticate" method="post">
        <table style="with: 80%">
            <tr>
                <td>Email</td>
                <td><input type="text" name="username" /></td>
            </tr>
            <tr>
                <td>Fingerprint</td>
                <td><input type="password" name="password" /></td>
            </tr>
        </table>
        <input type="submit" value="Submit" />
    </form>
    <form action="/register" class="inline">
        <button class="btn btn-lg btn-primary btn-block">Register as an new User</button>
    </form>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script></body>
</html>
