<%-- 
    Document   : list
    Created on : 16/11/2014, 11:07:22
    Author     : joshua
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Lista de Contatos</title>
        <%@include file="scriptSession.jsp"%>
        <link href="style.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <c:if test="${read == 0}">
            <script>window.location="denied.jsp";</script>
        </c:if>
            
        <c:if test="${create == 1}">
            <img src="img/insert.png" width="64" height="64" alt="Incluir" class="box" onclick="window.location='ContatoCRUDServlet?action=create&option=show';">
        </c:if>
        <img src="img/logout.png" width="64" height="64" alt="Sair" class="box" onclick="window.location='logout.jsp';">
        
        <table cellpadding="0" cellspacing="0" border="1">
            <tr style="background-color:#666666; color:#FFFFFF; text-align:center;">
                <th>Ações</th>
                <th>Nome</th>
                <th>Email</th>
                <th>Celular</th>
                <th>Operadora</th>
            </tr>
            
            <c:forEach var="contato" items="${contatos}" varStatus="id">
                <tr bgcolor="#${id.count % 2 == 0 ? 'DDDDDD' : 'FFFFFF' }">
                    <td>
                        <c:if test="${read == 1}">
                            <img src="img/view.png" width="32" height="32" alt="Visualizar" class="box" onclick="window.location='ContatoCRUDServlet?action=read&option=view&id=${contato.id}';">
                        </c:if>
                        <c:if test="${update == 1}">
                            <img src="img/edit.png" width="32" height="32" alt="Editar" class="box" onclick="window.location='ContatoCRUDServlet?action=update&option=show&id=${contato.id}';">
                        </c:if>
                        <c:if test="${delete == 1}">
                            <img src="img/delete.png" width="32" height="32" alt="Excluir" class="box" onclick="window.location='ContatoCRUDServlet?action=delete&option=show&id=${contato.id}';">
                        </c:if>
                    </td>
                    <td>${contato.nome}</td>
                    <td>
                        <c:choose>
                            <c:when test="${not empty contato.email}">
                                <a href="mailto:${contato.email}">${contato.email}</a>
                            </c:when>
                            <c:otherwise>
                                E-mail não informado
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>${contato.cel}</td>
                    <td>${contato.operadora_cel}</td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
