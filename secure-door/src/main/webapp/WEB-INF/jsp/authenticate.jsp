<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Insert title here</title>
</head>
<body>
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
