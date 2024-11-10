<%@ page import="meetuphub.model.User" %>
<%@ page import="meetuphub.repository.UserRepository" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    int id = Integer.parseInt(request.getParameter("id"));
    User user = UserRepository.getUserData("SELECT * FROM \"user\" WHERE id = ?", id).get(0);
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Update User</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/updateUser.css">
</head>
<body>

<div class="container">
    <h2>Обновить пользователя</h2>
    <form action="<%= request.getContextPath() %>/user" method="post">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="id" value="<%= user.getId() %>">
        <label for="name">Новое имя пользователя:</label>
        <input type="text" name="name" id="name" value="<%= user.getName() %>" required>
        <button type="submit">Обновить</button>
    </form>
</div>

</body>
</html>
