<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Event</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/eventCreate.css">
</head>
<body>

<div class="container">
    <h2>Create Event</h2>

    <form action="<%= request.getContextPath() %>/event/create" method="post" class="event-form">
        <div class="form-group">
            <label for="name">Имя ивента:</label>
            <input type="text" id="name" name="name" required placeholder="Введите название">
        </div>

        <div class="form-group">
            <label for="description">Описание:</label>
            <textarea id="description" name="description" required placeholder="Введите описание"></textarea>
        </div>

        <div class="form-group">
            <label for="status">Статус:</label>
            <input type="text" id="status" name="status" required placeholder="Выберите статус ивента">
        </div>
        <div class="form-group">
            <label for="startTime">Время начала:</label>
            <input type="datetime-local" id="startTime" name="startTime" required>
        </div>

        <div class="form-group">
            <label for="endTime">Время конца:</label>
            <input type="datetime-local" id="endTime" name="endTime" required>
        </div>


        <div class="form-group">
            <label for="locationId">ID локации:</label>
            <input type="number" id="locationId" name="locationId" required placeholder="Введите номер локации">
        </div>

        <div class="form-group">
            <label for="organizerId">ID организатора:</label>
            <input type="number" id="organizerId" name="organizerId" required placeholder="ID организатора">
        </div>

        <div class="form-group">
            <label for="category">Категории:</label>
            <select id="category" name="categoryIds" multiple>
                <c:forEach var="category" items="${categories}">
                    <option value="${category.id}">${category.name}</option>
                </c:forEach>
            </select>
            <small>Выбор нескольких категорий через ctrl.</small>
        </div>

        <div class="form-actions">
            <button type="submit" class="btn-submit">Создать</button>
            <button type="button" class="btn-create-category" onclick="window.location.href='<%= request.getContextPath() %>/category'">Создать категорию</button>
        </div>
    </form>
</div>

</body>
</html>
