<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Register here</title>
</head>
<body>
<div align="center">
    <h1>New User Register</h1>
    <form action="/register" method="post">
        <table style="with: 80%">
            <tr>
                <td>Email</td>
                <td><input type="text" name="username" /></td>
            </tr>
            <tr>
                <td>Fingerprint</td>
                <td><input type="password" name="password" /></td>
            </tr>
            <tr>
                <td>Authority</td>
                <td><input type="text" name="authority" /></td>
            </tr>
        </table>
        <input type="submit" value="Submit" />
    </form>
</div>
</body>
</html>