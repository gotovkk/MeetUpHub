<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>Registration Form</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/register.css">
</head>
<body>
<div class="container">
    <h2>Регистрация</h2>
    <form action="<%= request.getContextPath() %>/register" method="post" class="login-form">
        <div class="input-group">
            <input type="text" id="name" name="name" placeholder="Логин" required />
        </div>

        <div class="input-group">
            <input type="email" id="email" name="email" placeholder="Почта" required />
        </div>

        <div class="input-group">
            <input type="password" id="passwordHash" name="passwordHash" placeholder="Пароль" required />
        </div>

        <div class="input-group" style="text-align: left;">
            Выберите роль:<br>
            <input type="checkbox" name="roles" value="user"> Пользователь<br>
            <input type="checkbox" name="roles" value="organizer"> Организатор<br>
        </div>

        <button type="submit" class="button">Зарегистрироваться</button>
        <button type="button" class="button" onclick="redirectToLogin()">Войти в аккаунт</button>
    </form>
</div>

<script>
    function redirectToLogin() {
        window.location.href = '<%= request.getContextPath() %>/login';
    }
</script>
</body>
