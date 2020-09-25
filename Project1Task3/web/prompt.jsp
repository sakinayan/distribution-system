<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%= request.getAttribute("doctype") %>

<html>
    <head>
        <meta charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Click Page</title>
    </head>
    <body>
    <h1>Distributed Systems Class Clicker</h1>
        <% if(request.getAttribute("c")!=null){%>
            <P>Your "<%=request.getAttribute("c")%>" response has been registered </P>
        <%}%>
        <p>Submit your answer to the current question:</p>
        <form action="submit" method="post">
            <p><input type="radio" id="A" name="option" value="A" >A</p>
            <p><input type="radio" id="B" name="option" value="B" >B</p>
            <p><input type="radio" id="C" name="option" value="C" >C</p>
            <p><input type="radio" id="D" name="option" value="D" >D</p>
            <input type="submit" value="Submit" />
        </form>
    </body>
</html>

