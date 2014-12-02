<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

    <c:import url="../header.jsp"></c:import>

    <c:if test="${read == 0}">
        <script>window.location="denied.jsp";</script>
    </c:if>

        <section class="row panel large-12 radius">
            
            <div class="row large-12">
                <c:if test="${create == 1}">
                    <div class="large-6 columns left">
                        <div class="large-6 columns left">
                            <a href="ContatoCRUDServlet?action=create&option=show" class="button success postfix">Novo Contato</a>
                        </div>
                    </div>
                </c:if>

                <div class="large-6 columns right">
                        <div class="large-4 columns right">
                            <a href="SessionServlet?action=logout" class="button alert postfix">Sair</a>
                        </div>
                </div>
            </div>
            
            <!--<form action="" method="post">
                    <div class="large-12"
                        <%--<c:if test="${create == 1}">--%>
                            <div class="row large-6 left"
                                <input type="image" autofocus src="images/insert.png" width="10%" title="Incluir" class="box" formaction="ContatoCRUDServlet?action=create&option=show" onclick="submit();" />
                            </div>
                        <%--</c:if>--%>
                        <div class="large-6 right"
                            <input type="image" src="images/logout.png" width="10%" title="Sair" class="box" formaction="SessionServlet?action=logout" onclick="submit();" />
                        </div>
                    </div>
            </form>-->

            <div class="large-12 columns">
                <form method="post">
                    <table>
                        <thead>
                            <th class="large-2">Ações</th>
                            <th class="large-1">Foto</th>
                            <th class="large-3">Nome</th>
                            <th class="large-2">Email</th>
                            <th class="large-2">Celular</th>
                            <th class="large-1">Operad.</th>
                        </thead>

                        <c:forEach var="contato" items="${contatos}" varStatus="id">
                            <tr>
                                <td>
                                    <form action="" method="post">
                                    <c:if test="${read == 1}">
                                        <span data-tooltip aria-haspopup="true" class="has-tip" title="Visualizar Contato">
                                            <input type="image" src="images/view.png" width="32" height="32" formaction="ContatoCRUDServlet?action=read&option=view&id=${contato.idContato}" onclick="submit();" />
                                        </span>
                                    </c:if>
                                    <c:if test="${update == 1}">
                                        <span data-tooltip aria-haspopup="true" class="has-tip" title="Editar Contato">
                                            <input type="image" src="images/edit.png" width="32" height="32" formaction="ContatoCRUDServlet?action=update&option=show&id=${contato.idContato}" onclick="submit();" />
                                        </span>
                                    </c:if>
                                    <c:if test="${update == 1}">
                                        <span data-tooltip aria-haspopup="true" class="has-tip" title="Inserir Foto">
                                            <input type="image" src="images/photo.png" width="32" height="32" formaction="ContatoCRUDServlet?action=photo&option=show&id=${contato.idContato}" onclick="submit();" />
                                        </span>
                                    </c:if>
                                    <c:if test="${delete == 1}">
                                        <%--<!--<input type="image" src="images/delete.png" width="32" height="32" title="Excluir" class="box" formaction="ContatoCRUDServlet?action=delete&option=show&id=${contato.idContato}" onclick="submit();" />-->--%>
                                        <span data-tooltip aria-haspopup="true" class="has-tip" title="Excluir Contato">
                                            <input data-reveal-id="myModal" type="image" src="images/delete.png" width="32" height="32" formaction="ContatoCRUDServlet?action=delete&option=show&id=${contato.idContato}" onclick="submit();" />
                                        </span>
                                    </c:if>
                                    </form>
                                </td>
                                <td>
                                    <img src="${contato.foto}" width="51" />
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
                                <td class="text-center">${contato.operadora.nome}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </form>
            </div>
        </section>

                
    <c:import url="../footer.jsp"></c:import>
