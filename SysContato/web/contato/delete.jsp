<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <c:import url="../header.jsp"></c:import>
    
    <c:if test="${delete == 0}">
        <script>window.location="denied.jsp";</script>
    </c:if>

        
        <div id="myModal_background" class="reveal-modal-bg" style="display: block; visibility: visible"></div>
        <div id="myModal" class="reveal-modal open radius" data-reveal="" style="display: block; opacity: 1; visibility: visible; top: 200px;">
            <h2 class="text-center">Excluir</h2>
            <p class="painel warning text-center">Deseja excluir o contato: ${contato.idContato} ${contato.nome}?</p>
            <ul class="button-group radius">
                <li class="large-6"><input class="button large-12" type="submit" value="Excluir" title="Excluir" onclick="window.location='ContatoCRUDServlet?action=delete&option=ok&id=${contato.idContato}'" /></li>
                <li class="large-6"><input class="button alert large-12" type="button" value="Cancelar" title="Cancelar" onclick="window.location='ContatoCRUDServlet?action=read&option=list';" /></li>
            </ul>
                <a class="close-reveal-modal" onclick="window.location='ContatoCRUDServlet?action=read&option=list';">×</a>
        </div>
 
        
<%--<!--        
        <script>
            var r = confirm("Deseja Excluir o Contato ID:${contato.idContato}");
            if (r == true) {
                window.location="ContatoCRUDServlet?action=delete&option=ok&id=${contato.idContato}";
            } else {
                window.location="ContatoCRUDServlet?action=read&option=list";
            }
        </script>
-->--%>
    <c:import url="../footer.jsp"></c:import>
