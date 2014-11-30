<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <c:import url="../header.jsp"></c:import>
    
    <c:if test="${update == 0}">
        <script>window.location="denied.jsp";</script>
    </c:if>
            
        <form action="FileUploadServlet" method="post" enctype="multipart/form-data">
            <input type="hidden" name="id" value="${contato.idContato}"/>
            <label>Foto do Contato: </label>${contato.nome}<br />
            <input type="file" id="foto" name="foto" /><br />
            <br />
            <input type="image" src="images/edit.png" width="64" height="64" title="Editar" class="box" onclick="validar();" />
            <input type="image" src="images/cancel.png" width="64" height="64" title="Cancelar" class="box" onclick="window.history.go(-1)" />
        </form>
    
    <c:import url="../footer.jsp"></c:import>
    