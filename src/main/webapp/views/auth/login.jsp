<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>Login Form</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/login.css">
</head>
<body>
<div class="container">
    <h2>Вход в аккаунт</h2>
    <form action="<%=request.getContextPath()%>/login" method="post" class="login-form">
        <div class="input-group">
            <input type="text" name="name" placeholder="Логин" required />
        </div>
        <div class="input-group">
            <input type="password" name="password" placeholder="Пароль" required />
        </div>
        <input type="submit" value="Войти" class="button" />
        <button type="button" class="button" onclick="redirectToRegister()">Зарегистрироваться</button>
    </form>
</div>

<script>
    function redirectToRegister() {
        window.location.href = '<%= request.getContextPath() %>/register';
    }
</script>
</body>
