<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>OTP verification</title>
</head>
<body>
<div align="center">
    <h1>Enter OTP code</h1>
    <h3>OTP code successfully sent to your email!</h3>
    <form action="/authenticate/generateOtp/validateOtp" method="post">
        <table style="with: 80%">
            <tr>
                <td>Enter OTP here</td>
                <td><input type="text" name="otp" /></td>
            </tr>
        </table>
        <input type="submit" value="Submit" />
    </form>
</div>
</body>
</html>