<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <!--<meta name="viewport" content="width=device-width, initial-scale=1.0" />-->
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>SysContato</title>
        <link rel="stylesheet" href="css/foundation.css" type="text/css"/>
        <!--<link rel="stylesheet" href="css/normalize.css" type="text/css"/>-->
        <link rel="stylesheet" href="style.css" type="text/css">
        <script src="js/vendor/modernizr.js" type="text/javascript"></script>
        <script src="globals.js" type="text/javascript"></script>
    </head>
    <body>
        <header class="top-bar-section row large-12">
            <div class="top-bar docs-bar" data-topbar>
                <h1><a href="index.jsp">SysContatos</a></h1>
            </div>
        </header>
        
        <c:choose>
        <c:when test="${msg_tipo == 'error'}">
            <div class="row large-12">
                <div data-alert class="alert-box alert radius">
                    <span>${msg}</span>
                    <a href="#" class="close">&times;</a>                    
                </div>
            </div>                
        </c:when>
        <c:when test="${msg_tipo == 'warning'}">
            <div class="row large-12">
                <div data-alert class="alert-box warning radius">
                    <span>${msg}</span>
                    <a href="#" class="close">&times;</a>
                </div>
            </div>                
        </c:when>
        <c:when test="${msg_tipo == 'success'}">
            <div class="row large-12">
                <div data-alert class="alert-box success radius">
                    <span>${msg}</span>
                    <a href="#" class="close">&times;</a>
                </div>
            </div>                
        </c:when>
        <c:otherwise>
        </c:otherwise>
        </c:choose> 



