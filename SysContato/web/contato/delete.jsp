<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <c:import url="../header.jsp"></c:import>
    
    <c:if test="${delete == 0}">
        <script>window.location="denied.jsp";</script>
    </c:if>
            
        <script>
            var r = confirm("Deseja Excluir o Contato ID:${contato.idContato}");
            if (r == true) {
                window.location="ContatoCRUDServlet?action=delete&option=ok&id=${contato.idContato}";
            } else {
                window.location="ContatoCRUDServlet?action=read&option=list";
            }
        </script>

    <c:import url="../footer.jsp"></c:import>
