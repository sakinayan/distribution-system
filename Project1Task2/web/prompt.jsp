<%@ page import="java.util.ArrayList" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%= request.getAttribute("doctype") %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>JSP Page</title>
    </head>
    <body>
    <h1>Covid Trends by Sta</h1>
    <p1>Created by Yanyan Jin</p1>
    <br>
    <form action="getAnInterestingState" method="GET">
        <label for="statename">Choose the name of a state</label>
        <select id="statename" name="state">
                <% ArrayList<String> statenames = (ArrayList<String>) request.getAttribute("statelist"); %>
                <% for (int i = 0; i < statenames.size(); i++) { %>
                        <% String state = statenames.get(i); %>
                <% if (i == 0){ %>
                    <option value="<%= state %>" selected><%= state %></option>
                <% } else {%>
                    <option value="<%= state %>"><%= state %></option>
            <% }%>
            <% }%>
        </select>
        <br><br>

<%--        choice for statitic--%>
        <label for="statistic">Enter the name of a statistic</label>
        <select name="statistic" id="statistic">
            <option value="positive" selected>Positive Cases</option>
            <option value="negative">Negative Cases</option>
            <option value="hospitalizedCurrently">Currently Hospitalized</option>
            <option value="hospitalizedCumulative">Cumulative Hospitalized</option>
        </select>
        <br><br>

<%--        design date--%>
        <p>Enter the start date <input type="date" id="start" name="start" min="2020-03-01" %>
        <p>Enter the end date <input type="date" id="end" name="end"  max=<%= request.getAttribute("maxdate")%>>
        <p><input type="submit" value="Submit" />
    </form>
    </body>
</html>

