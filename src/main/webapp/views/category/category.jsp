<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Manage Categories</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/category.css">
</head>
<body>

<div class="container">
    <h2>Категории</h2>

    <table>
        <tr>
            <th>ID</th>
            <th>Название</th>
            <th>Описание</th>
        </tr>
        <c:forEach var="category" items="${categories}">
            <tr>
                <td>${category.id}</td>
                <td>${category.name}</td>
                <td>${category.description}</td>
            </tr>
        </c:forEach>
    </table>

    <h3>Добавить новую категорию</h3>
    <form action="<%= request.getContextPath() %>/category" method="post">
        <label for="newCategoryName">Название категории:</label>
        <input type="text" id="newCategoryName" name="newCategoryName" required>

        <label for="newCategoryDescription">Описание категории:</label>
        <input type="text" id="newCategoryDescription" name="newCategoryDescription">

        <div class="form-buttons">
            <button type="submit" class="btn-add">Добавить категорию</button>
            <button type="button" class="btn-back" onclick="window.location.href='<%= request.getContextPath() %>/event/create'">Вернуться</button>
        </div>
    </form>
</div>

</body>
</html>
