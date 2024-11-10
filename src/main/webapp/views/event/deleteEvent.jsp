<%@ page import="meetuphub.model.Event" %>
<%@ page import="meetuphub.repository.EventRepository" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    int id = Integer.parseInt(request.getParameter("id"));
    Event event = EventRepository.getEventData("SELECT * FROM event WHERE id = ?", id).get(0);
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Delete Event</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/deleteEvent.css">
</head>
<body>

<div class="container">
    <h2>Удалить мероприятие</h2>
    <p class="warning">Вы уверены, что хотите удалить это мероприятие? Это действие необратимо.</p>

    <div class="event-details">
        <p><strong>ID:</strong> <%= event.getId() %></p>
        <p><strong>Имя:</strong> <%= event.getName() %></p>
        <p><strong>Описание:</strong> <%= event.getDescription() %></p>
        <p><strong>Статус:</strong> <%= event.getStatus() %></p>
        <p><strong>Время начала:</strong> <%= event.getStartTime() %></p>
        <p><strong>Время окончания:</strong> <%= event.getEndTime() %></p>
    </div>

    <form action="<%= request.getContextPath() %>/event/delete" method="post">
        <input type="hidden" name="action" value="delete">
        <input type="hidden" name="id" value="<%= event.getId() %>">
        <button type="submit" class="btn-confirm">Подтвердить удаление</button>
        <button type="button" class="btn-cancel" onclick="window.history.back();">Отменить</button>
    </form>
</div>

</body>
</html>
