<%--
  Created by IntelliJ IDEA.
  User: sakin
  Date: 9/17/2020
  Time: 21:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<p>
  <head>
    <title>$Project Task 1$</title>
  </head>
  <body>
  <h2>Enter a string of text data for hash computation:</h2>
  <form action="ComputeHashes" method="get" >
  <input type="text" id="inputt" name="inputt" required
         minlength="4" maxlength="8" size="10">
  <p><input type="radio" id ="MD5" name="hashtype" value="MD5" checked>MD5</p>
  <p><input type="radio" id="SHA-256" name="hashtype" value="SHA-256">SHA-256</p>
  <p><input type="submit" id="action" name="action" value="Submit"></p>
  <% if (request.getAttribute("back")!=null){
  %>
     <%=request.getAttribute("back") %>
  <%}%>
  <br>
  </form>
  </body>
</html>
