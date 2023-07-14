<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Errore ${pageContext.errorData.statusCode}</title>
</head>
<body>
    <h1>Oops! Something went wrong.</h1>
    <p>An error occurred while processing your request.</p>
    <p>${pageContext.errorData.throwable}</p>
    <button onclick="window.history.back()">Go Back</button>
</body>
</html>
