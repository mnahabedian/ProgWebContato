<%-- 
    Document   : delete
    Created on : 16/11/2014, 11:07:22
    Author     : joshua
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Excluir Contato</title>
        <%@include file="scriptSession.jsp"%>
        <link href="style.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <c:if test="${delete == 0}">
            <script>window.location="denied.jsp";</script>
        </c:if>
            
        <script>
            var r = confirm("Deseja Excluir o Contato ID:${contato.id}");
            if (r == true) {
                window.location="ContatoCRUDServlet?action=delete&option=ok&id=${contato.id}";
            } else {
                window.location="ContatoCRUDServlet?action=read&option=list";
            }
        </script>
    </body>
</html>
