<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%= request.getAttribute("doctype") %>

<html>
    <head>
        <meta charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Result</title>
    </head>
    <body>
    <h1>Distributed Systems Class Clicker</h1>
    <% HashMap<String,Integer> result = (HashMap<String,Integer>)(request.getAttribute("check"));%>
    <%if (!result.isEmpty()) {%>
        <p1>The results from the survey are as follows:</p1><br>
    <% List<String> l = (List<String>) request.getAttribute("keys"); %>
        <% List<Integer> v = (List<Integer>) request.getAttribute("values"); %>
        <% for (int i = 0; i < l.size(); i++){%>
            <p1> <%=l.get(i)%> : <%=v.get(i)%></p1>
            <br>
        <%}%>
    <br>
        <p1>The results have now been cleared.</p1>
    <%}else{%>
            <p>There are currently no results.</p>
    <% } %>
    </body>
</html>

