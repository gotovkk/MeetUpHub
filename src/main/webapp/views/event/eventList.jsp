<%@ page import="meetuphub.model.User" %>
<%@ page import="meetuphub.model.Event" %>
<%@ page import="java.util.List" %>
<%@ page import="meetuphub.model.Category" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Event List</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/event.css">
</head>
<body>

<%
    User loggedInUser = (User) session.getAttribute("loggedInUser");
    if (loggedInUser != null) {
%>
<div class="user-info">
    Вход выполнен под логином: <%= loggedInUser.getName() %>
</div>
<%
    }
%>

<h2 class="page-title">Список мероприятий</h2>
<h3 class="create-event-title">Создать мероприятие</h3>
<form action="<%= request.getContextPath() %>/event/create" method="get">
    <button type="submit" class="create-button">Добавить</button>
</form>

<%
    String registrationMessage = (String) request.getAttribute("registrationMessage");
    if (registrationMessage != null) {
%>
<p class="success-message"><%= registrationMessage %></p>
<%
    }
%>

<%
    List<Event> events = (List<Event>) request.getAttribute("events");
    if (events != null && !events.isEmpty()) {
        for (Event event : events) {
            boolean isRegistered = Boolean.TRUE.equals(request.getAttribute("isRegistered_" + event.getId()));

%>
<div class="event-card">
    <p><strong>ID:</strong> <%= event.getId() %></p>
    <p><strong>Имя:</strong> <%= event.getName() %></p>
    <p><strong>Описание:</strong> <%= event.getDescription() %></p>
    <p><strong>Статус:</strong> <%= event.getStatus() %></p>
    <p><strong>Время начала:</strong> <%= event.getStartTime() %></p>
    <p><strong>Время конца:</strong> <%= event.getEndTime() %></p>

    <%
        List<Category> categories = (List<Category>) request.getAttribute("categories_" + event.getId());
        if (categories != null && !categories.isEmpty()) {
            for (Category category : categories) {
                out.print("<span class='category'>" + category.getName() + "</span>");
            }
        } else {
    %>
    <em>Категория не добавлена</em>
    <%
        }
    %>

    <% if (isRegistered) { %>
    <p class="status-message">Вы зарегистрированы</p>
    <% } else { %>
    <form action="<%= request.getContextPath() %>/event/register" method="post" class="register-form">
        <input type="hidden" name="eventId" value="<%= event.getId() %>">
        <button type="submit" class="register-button">Учавствовать</button>
    </form>
    <% } %>

    <div class="event-actions">
        <a href="<%= request.getContextPath() %>/event/update?id=<%= event.getId() %>" class="action-link">Обновить</a> |
        <a href="<%= request.getContextPath() %>/event/delete?id=<%= event.getId() %>" class="action-link">Удалить</a>
    </div>
</div>

<%
    }
} else {
%>
<p>Мероприятий пока не найдено.</p>
<%
    }
%>

</body>
</html>
