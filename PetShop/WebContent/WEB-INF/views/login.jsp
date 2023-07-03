<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> <!-- Include jQuery -->
</head>
<body>
    <h1>Login</h1>
    <form id="loginForm" method="POST">
        <label for="nomeUtente">Nome Utente:</label>
        <input type="text" id="nomeUtente" name="nomeUtente" required><br><br>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>

        <input type="submit" value="Login">
    </form>

    <div id="error"></div>

    <script>
    $(document).ready(function() {
        $('#loginForm').on('submit', function(event) {
            event.preventDefault(); // Prevent form from causing a page refresh

            $.ajax({
                url: 'login', // The URL of your servlet
                type: 'POST', // HTTP method
                data: $(this).serialize(), // Serialize form data for the request
                success: function(data) {  
                    if (data.stato === 'ok')
                        window.location.href = "login";
                    else if (data.stato == 'errore')
                       $("#error").html("Errore. Credenziali non valide!")
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    $("#error").html("Errore server");
                }
            });
        });
    });

    </script>
</body>
</html>
