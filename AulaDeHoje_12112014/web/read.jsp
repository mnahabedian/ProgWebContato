<%-- 
    Document   : read
    Created on : 16/11/2014, 11:07:22
    Author     : joshua
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Visualizar Contato</title>
        <%@include file="scriptSession.jsp"%>
        <link href="style.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <c:if test="${read == 0}">
            <script>window.location="denied.jsp";</script>
        </c:if>
        
        <img src="img/view.png" width="64" height="64" alt="Visualizar" class="box" onclick="window.location='ContatoCRUDServlet?action=read&option=list';">
        <br />
        <label>Nome: </label><span>${contato.nome}</span>
        <br />
        <label>Email: </label><span>${contato.email}</span>
        <br />
        <label>Celular: </label><span>${contato.cel}</span>
        <br />
        <label>Operadora: </label><span>${contato.operadora_cel}</span>
        <br />
        <label>Cidade: </label><span>${contato.cidade}</span>
        <br />
        <label>Estado: </label><span>${contato.estado}</span>
        <br />
        <label>Nascimento: </label><span>${contato.str_data_nasc}</span>
    </body>
</html>
