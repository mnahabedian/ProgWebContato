<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>SysContato</title>
        <link href="style.css" rel="stylesheet" type="text/css">
        <script src="globals.js" type="text/javascript"></script>
    </head>
    <body>
        <header>
            <h1>
                SysContatos
            </h1>
        </header>
        <h1 <c:choose>
            <c:when test="${msg_tipo == 'error'}">
                style="color: red;"
            </c:when>
            <c:when test="${msg_tipo == 'warning'}">
                style="color: yellow;"
            </c:when>
            <c:otherwise>
                style="color: green;"
            </c:otherwise>
        </c:choose>>${msg}</h1>
        