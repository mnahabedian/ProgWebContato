<%-- 
    Document   : update
    Created on : 16/11/2014, 11:07:22
    Author     : joshua
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Editar Contato</title>
        <%@include file="scriptSession.jsp"%>
        <link href="style.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="masked_input_1.3.js"></script>
    </head>
    <body>
        <c:if test="${update == 0}">
            <script>window.location="denied.jsp";</script>
        </c:if>
            
        <form action="ContatoCRUDServlet" method="post">
            <img src="img/edit.png" width="64" height="64" alt="Editar" class="box" onclick="submit();">
            <img src="img/cancel.png" width="64" height="64" alt="Cancelar" class="box" onclick="window.location='ContatoCRUDServlet?action=read&option=list';">

            <br />
            <input type="hidden" name="action" value="update"/>
            <input type="hidden" name="option" value="ok"/>
            <input type="hidden" name="id" value="${contato.id}"/>
            <label>Nome:</label><input type="text" required="required" maxlength="128" id="nome" name="nome" value="${contato.nome}" /><br />
            <label>Email:</label><input type="text" required="required" maxlength="128" id="email" name="email" value="${contato.email}" /><br />
            <label>Celular:</label><input type="text" required="required" maxlength="32" id="cel" name="cel" value="${contato.cel}" /><br />
            <label>Operadora:</label><input type="text" required="required" maxlength="32" id="operadora_cel" name="operadora_cel" value="${contato.operadora_cel}" /><br />
            <label>Cidade:</label><input type="text" required="required" maxlength="128" id="cidade" name="cidade" value="${contato.cidade}" /><br />
            <label>Estado:</label><input type="text" required="required" maxlength="2" id="estado" name="estado" value="${contato.estado}" /><br />
            <label>Nascimento:</label><input type="text" required="required" maxlength="10" id="data_nasc" name="data_nasc" value="${contato.str_data_nasc}" /><br />
        </form>
    </body>
</html>
