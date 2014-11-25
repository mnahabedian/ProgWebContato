<%-- 
    Document   : logout
    Created on : 12/11/2014, 09:28:27
    Author     : Hercules Sandim
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Sistema - Logout</title>
        <%
            session.setAttribute("user", null);
        %>
        <script>
            window.location="login.jsp";
        </script>

    </head>
</html>
