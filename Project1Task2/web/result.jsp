<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%= request.getAttribute("doctype") %>

<html>
    <head>
        <title>State Picture</title>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    </head>
    <body>
<%--    check image and time frame is avaliable. end date > start date--%>
<%if (request.getAttribute("pictureURL") != null && request.getAttribute("timecompare") !=null){%>
         <h1>State: <%= request.getParameter("state")%></h1><br>
         <img src="<%= request.getAttribute("pictureURL")%>" ><br>
         <form action="getAnInterestingState" method="GET">
             <p>Credit: https://statesymbolsusa.org</p>
             <%String [] statistics = {"Positive Cases", "Negative Cases","Currently Hospitalized","Cumulative Hospitalized"}; %>
             <% int index=0;%>
             <%for (int i=0; i<statistics.length;i++)
             {
                 String ss = statistics[i].toLowerCase();
                 String getss = request.getParameter("statistic").toLowerCase();
                 if (ss.contains(getss.substring(getss.length() - 8)))
                 {
                     index=i;
                     break;
                 }
             }%>
             <h1>Statistic: <%= statistics[index]%></h1>
             <h1>From: <%= request.getAttribute("from")%></h1>
             <h1>To: <%= request.getAttribute("to")%></h1>
             <h1>Change: <%= request.getAttribute("change")%></h1><br>

        </form>
    <%} else {%>
        <p> End date should be bigger then start date. Go back to correct. </p>
    <%} %>
        <p><input type="button"  value="Continue" onclick="openPage()"/></p>
        <script>
            function openPage()
            {
                window.history.back(-1);
            }
        </script>
    </body>
</html>

