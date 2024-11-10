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
    <title>Update Event</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/eventUpdate.css">
</head>
<body>

<div class="container">
    <h2>Update Event</h2>

    <form action="<%= request.getContextPath() %>/event/update" method="post" class="event-form">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="id" value="<%= event.getId() %>">

        <!-- Event Name -->
        <div class="form-group">
            <label for="name">Название:</label>
            <input type="text" name="name" id="name" value="<%= event.getName() %>" required placeholder="Enter event name">
        </div>

        <!-- Description -->
        <div class="form-group">
            <label for="description">Описание:</label>
            <input type="text" name="description" id="description" value="<%= event.getDescription() %>" required placeholder="Enter event description">
        </div>

        <!-- Status -->
        <div class="form-group">
            <label for="status">Состояние:</label>
            <input type="text" name="status" id="status" value="<%= event.getStatus() %>" required placeholder="Enter event status">
        </div>

        <div class="form-group">
            <label for="startTime">Время начала:</label>
            <input type="datetime-local" name="startTime" id="startTime" value="<%= event.getStartTime().toString().replace(' ', 'T') %>" required>
        </div>

        <div class="form-group">
            <label for="endTime">Время окончания:</label>
            <input type="datetime-local" name="endTime" id="endTime" value="<%= event.getEndTime().toString().replace(' ', 'T') %>" required>
        </div>

        <div class="form-group">
            <label for="locationId">Локация ID:</label>
            <input type="number" name="locationId" id="locationId" value="<%= event.getLocationId() %>" required placeholder="Enter location ID">
        </div>

        <div class="form-group">
            <label for="organizerId">Организация ID:</label>
            <input type="number" name="organizerId" id="organizerId" value="<%= event.getOrganizerId() %>" required placeholder="Enter organizer ID">
        </div>

        <div class="form-actions">
            <button type="submit" class="btn-submit">Обновить</button>
        </div>
    </form>
</div>

</body>
</html>
