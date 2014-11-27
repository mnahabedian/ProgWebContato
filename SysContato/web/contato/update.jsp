<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <c:import url="../header.jsp"></c:import>
    
    <c:if test="${update == 0}">
        <script>window.location="denied.jsp";</script>
    </c:if>
            
        <form action="ContatoCRUDServlet" method="post">
            <input type="hidden" id="action" name="action" value="update" />
            <input type="hidden" id="option" name="option" value="ok" />
            <input type="hidden" name="id" value="${contato.idContato}"/>            
            
            <label for="nome">Nome:</label><input type="text" autofocus required id="nome" name="nome" title="Digite o Nome" maxlength="128" value="${contato.nome}" /><br />
            <label for="dataNasc">Nascimento:</label><input type="date" required id="dataNasc" name="dataNasc" title="Digite a Data de Nascimento (dia-mês-ano)" pattern="^(0[1-9]|[12][0-9]|3[01])(/)(0[1-9]|1[012])(/)(19|20)\d\d$" value="${contato.strDataNasc}" /><br />
            <label for="cel">Celular:</label><input type="text" required id="cel" name="cel" title="Digite o Número do Celular" maxlength="32" value="${contato.cel}" /><br />
            <label for="operadoraCel">Operadora:</label><input type="text" required id="operadoraCel" name="operadoraCel" title="Digite a Operadora do Celular" maxlength="32" value="${contato.operadoraCel}" /><br />
            <label for="idEstado">Estado:</label>
            <select required id="idEstado" name="idEstado">
                <c:forEach var="estado" items="${estados}">
                    <option value="${estado.idEstado}" <c:if test="${contato.cidade.estado.idEstado == estado.idEstado}">selected</c:if> >${estado.sigla}</option>
                </c:forEach>
            </select>
            <br />
            <label for="idCidade">Cidade:</label>
            <select required id="idCidade" name="idCidade">
                <option value="0"></option>
                <option value="1506">Campo Grande</option>
            </select>
            <br />
            <label for="email">Email:</label><input type="email" required id="email" name="email" maxlength="256" value="${contato.email}" /><br />
            <label>Facebook:</label><input type="text" id="facebook" name="facebook" maxlength="256" value="${contato.facebook}" /><br />
            <label>Twitter:</label><input type="text" id="twitter" name="twitter" maxlength="256" value="${contato.twitter}" /><br />
            <label>Site:</label><input type="text" id="site" name="site" maxlength="256" value="${contato.site}" /><br />
            <label>Foto:</label><input type="file" id="foto" name="foto" /><br />
            <br />
            <input type="image" src="img/edit.png" width="64" height="64" title="Editar" class="box" onclick="submit();" />
            <input type="image" src="img/cancel.png" width="64" height="64" title="Cancelar" class="box" onclick="window.history.go(-1)" />
        </form>
    
    <c:import url="../footer.jsp"></c:import>
    