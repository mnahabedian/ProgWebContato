<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <c:import url="../header.jsp"></c:import>
    
    <c:if test="${read == 0}">
        <script>window.location="denied.jsp";</script>
    </c:if>
            
        <form action="ContatoCRUDServlet" method="post">
            <input type="hidden" id="action" name="action" value="read" />
            <input type="hidden" id="option" name="option" value="list" />
            
            <img src="${contato.foto}" width="10%" height="10%" /><br />
            <label for="nome">Nome:</label><span>${contato.nome}</span><br />
            <label for="dataNasc">Nascimento:</label><span>${contato.strDataNasc}</span><br />
            <label for="cel">Celular:</label><span>${contato.cel}</span><br />
            <label for="operadoraCel">Operadora:</label><span>${contato.operadora.nome}</span><br />
            <label for="idEstado">Estado:</label><span>${contato.cidade.estado.sigla}</span><br />
            <label for="idCidade">Cidade:</label><span>${contato.cidade.nome}</span><br />
            <label for="email">Email:</label><span>${contato.email}</span><br />
            <label>Facebook:</label><span>${contato.facebook}</span><br />
            <label>Twitter:</label><span>${contato.twitter}</span><br />
            <label>Site:</label><span>${contato.site}</span><br />
            <label>Foto:</label><span>${contato.foto}</span><br />
            <br />
            <input type="image" src="images/view.png" width="64" height="64" title="Voltar" class="box" onclick="window.history.go(-1)" />
        </form>
    
    <c:import url="../footer.jsp"></c:import>
    