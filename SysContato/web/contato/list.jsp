<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

    <c:import url="../header.jsp"></c:import>

    <c:if test="${read == 0}">
        <script>window.location="denied.jsp";</script>
    </c:if>
        <section>
            <form action="" method="post">
            <c:if test="${create == 1}">
                <input type="image" autofocus src="img/insert.png" width="64" height="64" title="Incluir" class="box" formaction="ContatoCRUDServlet?action=create&option=show" onclick="submit();" />
            </c:if>
                <input type="image" src="img/logout.png" width="64" height="64" title="Sair" class="box" formaction="SessionServlet?action=logout" onclick="submit();" />
            </form>
            <form method="post">
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
                                <form action="" method="post">
                                <c:if test="${read == 1}">
                                    <input type="image" src="img/view.png" width="32" height="32" title="Visualizar" class="box" formaction="ContatoCRUDServlet?action=read&option=view&id=${contato.idContato}" onclick="submit();" />
                                </c:if>
                                <c:if test="${update == 1}">
                                    <input type="image" src="img/edit.png" width="32" height="32" title="Editar" class="box" formaction="ContatoCRUDServlet?action=update&option=show&id=${contato.idContato}" onclick="submit();" />
                                </c:if>
                                <c:if test="${delete == 1}">
                                    <input type="image" src="img/delete.png" width="32" height="32" title="Excluir" class="box" formaction="ContatoCRUDServlet?action=delete&option=show&id=${contato.idContato}" onclick="submit();" />
                                </c:if>
                                </form>
                            </td>
                            <td>${contato.nome}</td>
                            <td>
                            <c:choose>
                                <c:when test="${not empty contato.email}">
                                    <a href="mailto:${contato.email}">${contato.email}</a>
                                </c:when>
                                <c:otherwise>
                                    E-mail não informado!
                                </c:otherwise>
                            </c:choose>
                            </td>
                            <td>${contato.cel}</td>
                            <td>${contato.operadoraCel}</td>
                        </tr>
                    </c:forEach>
                </table>
            </form>
        </section>
                    
    <c:import url="../footer.jsp"></c:import>
