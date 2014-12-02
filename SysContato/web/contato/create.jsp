<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <c:import url="../header.jsp"></c:import>
    
    <c:if test="${create == 0}">
        <script>window.location="denied.jsp";</script>
    </c:if>
        
        <form action="ContatoCRUDServlet" method="post" onsubmit="return validar()">
            <input type="hidden" id="action" name="action" value="create" />
            <input type="hidden" id="option" name="option" value="ok" />
            
            <label for="nome">Nome:</label><input type="text" autofocus required id="nome" name="nome" title="Digite o Nome" maxlength="128" value="" /><br />
            <label for="dataNasc">Nascimento:</label><input type="date" required id="dataNasc" name="dataNasc" title="Digite a Data de Nascimento (dia-mês-ano)" pattern="^(0[1-9]|[12][0-9]|3[01])(-)(0[1-9]|1[012])(-)(19|20)\d\d$" maxlength="10" min="1900-01-01" value="" /><br />
            <label for="cel">Celular:</label><input type="text" required id="cel" name="cel" title="Digite o Número do Celular" pattern="^[0-9]{2}[ ]{1}[0-9]{4,5}[-]{1}[0-9]{4}$" placeholder="99 99999-9999" maxlength="32" value="" /><br />
            <label for="idOperadora">Operadora:</label>
            <select required id="idOperadora" name="idOperadora" title="Selecione a Operadora do Celular">
                <option value="" selected></option>
                <c:forEach var="operadora" items="${operadoras}">
                    <option value="${operadora.idOperadora}">${operadora.nome}</option>
                </c:forEach>
            </select>
            <br />
            <label for="idEstado">Estado:</label>
            <select required id="idEstado" name="idEstado" title="Selecione o Estado" onchange="dropdownCidade(this.value)">
                <option value="" selected></option>
                <c:forEach var="estado" items="${estados}">
                    <option value="${estado.idEstado}">${estado.sigla}</option>
                </c:forEach>
            </select>
            <br />
            <label for="idCidade">Cidade:</label>
            <select required id="idCidade" name="idCidade" title="Selecione a Cidade">
            </select>
            <br />
            <label for="email">Email:</label><input type="email" required id="email" name="email" maxlength="256" value="" /><br />
            <label>Facebook:</label><input type="text" id="facebook" name="facebook" maxlength="256" value="" /><br />
            <label>Twitter:</label><input type="text" id="twitter" name="twitter" maxlength="256" value="" /><br />
            <label>Site:</label><input type="text" id="site" name="site" maxlength="256" value="" /><br />
            <br />
            <input type="image" src="images/insert.png" width="64" height="64" title="Incluir" class="box" onclick="validar()" />
            <input type="image" src="images/cancel.png" width="64" height="64" title="Cancelar" class="box" onclick="window.history.go(-1)" />
        </form>
    
    <c:import url="../footer.jsp"></c:import>
    