<%-- 
    Document   : scriptSession
    Created on : 12/11/2014, 09:33:49
    Author     : Hercules Sandim
--%>

<%@page import="br.ufms.classes.Usuario"%>
<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%
    Usuario user;
    
    if( (user=(Usuario) session.getAttribute("user"))==null )
    {
        %>
       	<script>
            alert("Voce precisa estar autenticado para acessar!");
            window.location = "login.jsp";
	</script>
        <%
    }
%>