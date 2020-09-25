<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%= request.getAttribute("doctype") %>

<html>
<head>
    <title>No Picture</title>
</head>
<body>
<%  if (request.getAttribute("pictureURL") == null){%>
    <h1> State picture of a <%= request.getParameter("state")%> could not be found</h1><br>
    <p><input type="button" value="Continue" onclick="openPage()"/></p>
<script>
    function openPage()
    {
        window.history.back(-1);
    }
</script>
<% } %>
</body>
</html>
