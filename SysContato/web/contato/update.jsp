<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <c:import url="../header.jsp"></c:import>
    
    <c:if test="${update == 0}">
        <script>window.location="denied.jsp";</script>
    </c:if>

    <section class="row panel large-5">

        <fieldset class="large-12 column">
            <legend><h3 class="centered">Dados</h3></legend>
            <form action="ContatoCRUDServlet" method="post">
                <input type="hidden" id="action" name="action" value="update" />
                <input type="hidden" id="option" name="option" value="ok" />
                <input type="hidden" name="id" value="${contato.idContato}"/>

                    <div class="row">
                        <div class="large-12 columns">
                          <div class="row collapse prefix-radius">
                            <div class="small-2 columns">
                                <a class="button prefix" for="nome">Nome</a>
                            </div>
                            <div class="small-10 columns">
                                <input type="text" autofocus required id="nome" name="nome" title="Digite o Nome" maxlength="128" value="${contato.nome}" />
                            </div>
                          </div>
                        </div>                
                    </div>

                    <div class="row">
                        <div class="large-12 columns">
                          <div class="row collapse prefix-radius">
                            <div class="small-2 columns">
                                <a class="button prefix" for="dataNasc">Nascimento</a>
                            </div>
                            <div class="small-10 columns">
                                <input type="date" required id="dataNasc" name="dataNasc" title="Digite a Data de Nascimento (dia-mês-ano)" pattern="^(0[1-9]|[12][0-9]|3[01])(-)(0[1-9]|1[012])(-)(19|20)\d\d$" maxlength="10" min="1900-01-01" value="${contato.strDataNasc}" />
                            </div>
                          </div>
                        </div>                
                    </div>

                    <div class="row">
                        <div class="large-12 columns">
                          <div class="row collapse prefix-radius">
                            <div class="small-2 columns">
                                <a class="button prefix" for="cel">Celular</a>
                            </div>
                            <div class="small-10 columns">
                                <input type="text" required id="cel" name="cel" title="Digite o Número do Celular" pattern="^[0-9]{2}[ ]{1}[0-9]{4,5}[-]{1}[0-9]{4}$" placeholder="99 99999-9999" maxlength="32" value="${contato.cel}" />
                            </div>
                          </div>
                        </div>                
                    </div>

                    <div class="row">
                        <div class="large-12 columns">
                          <div class="row collapse prefix-radius">
                            <div class="small-2 columns">
                                <a class="button prefix" for="idOperadora">Operadora</a>
                            </div>
                            <div class="small-10 columns">
                                <select required id="idOperadora" name="idOperadora" title="Selecione a Operadora do Celular">
                                    <c:forEach var="operadora" items="${operadoras}">
                                        <option value="${operadora.idOperadora}" <c:if test="${contato.idOperadora == operadora.idOperadora}">selected</c:if> >${operadora.nome}</option>
                                    </c:forEach>
                                </select>
                            </div>
                          </div>
                        </div>                
                    </div>

                    <div class="row">
                        <div class="large-12 columns">
                          <div class="row collapse prefix-radius">
                            <div class="small-2 columns">
                                <a class="button prefix" for="idEstado">Estado</a>
                            </div>
                            <div class="small-10 columns">
                                <select required id="idEstado" name="idEstado" title="Selecione o Estado" onchange="dropdownCidade(this.value)">
                                    <c:forEach var="estado" items="${estados}">
                                        <option value="${estado.idEstado}" <c:if test="${contato.cidade.idEstado == estado.idEstado}">selected</c:if> >${estado.sigla}</option>
                                    </c:forEach>
                                </select>
                            </div>
                          </div>
                        </div>                
                    </div>

                    <div class="row">
                        <div class="large-12 columns">
                          <div class="row collapse prefix-radius">
                            <div class="small-2 columns">
                                <a class="button prefix" for="idCidade">Cidade</a>
                            </div>
                            <div class="small-10 columns">
                                <select required id="idCidade" name="idCidade" title="Selecione a Cidade">
                                    <c:forEach var="cidade" items="${cidades}">
                                        <option value="${cidade.idCidade}" <c:if test="${contato.idCidade == cidade.idCidade}">selected</c:if> >${cidade.nome}</option>
                                    </c:forEach>
                                </select>
                            </div>
                          </div>
                        </div>                
                    </div>

                    <div class="row">
                        <div class="large-12 columns">
                          <div class="row collapse prefix-radius">
                            <div class="small-2 columns">
                                <a class="button prefix" for="email">Email</a>
                            </div>
                            <div class="small-10 columns">
                                <input type="email" required id="email" name="email" maxlength="256" value="${contato.email}" />
                            </div>
                          </div>
                        </div>                
                    </div>

                    <div class="row">
                        <div class="large-12 columns">
                          <div class="row collapse prefix-radius">
                            <div class="small-2 columns">
                                <a class="button prefix" for="facebook">Facebook</a>
                            </div>
                            <div class="small-4 columns">
                                <span class="prefix">https://www.facebook.com/</span>
                            </div>
                            <div class="small-6 columns">
                                <input type="text" id="facebook" name="facebook" maxlength="256" value="${contato.facebook}" />
                            </div>
                          </div>
                        </div>                
                    </div>

                    <div class="row">
                        <div class="large-12 columns">
                          <div class="row collapse prefix-radius">
                            <div class="small-2 columns">
                                <a class="button prefix" for="twitter">Twitter</a>
                            </div>
                            <div class="small-3 columns">
                                <span class="prefix">https://twitter.com/</span>
                            </div>
                            <div class="small-7 columns">
                                <input type="text" id="twitter" name="twitter" maxlength="256" value="${contato.twitter}" />
                            </div>
                          </div>
                        </div>                
                    </div>

                    <div class="row">
                        <div class="large-12 columns">
                          <div class="row collapse prefix-radius">
                            <div class="small-2 columns">
                                <a class="button prefix" for="site">Site</a>
                            </div>
                            <div class="small-1 columns">
                                <span class="prefix">http://</span>
                            </div>
                            <div class="small-9 columns">
                                <input type="text" id="site" name="site" maxlength="256" value="${contato.site}" />
                            </div>
                          </div>
                        </div>                
                    </div>

                    <ul class="button-group">
                        <li class="large-6"><input class="button success large-12" type="submit" value="Editar" title="Editar" onclick="validar();" /></li>
                        <li class="large-6"><input class="button alert large-12" type="button" value="Cancelar" title="Cancelar" onclick="window.history.go(-1)" /></li>
                    </ul>

                <!--<input type="image" src="images/insert.png" width="64" height="64" title="Incluir" class="box" onclick="validar();" />
                <input type="image" src="images/cancel.png" width="64" height="64" title="Cancelar" class="box" onclick="window.history.go(-1)" />-->
            </form>
        </fieldset>        
    </section>
        
        
<%--        
<!--        <form action="ContatoCRUDServlet" method="post">
            <input type="hidden" id="action" name="action" value="update" />
            <input type="hidden" id="option" name="option" value="ok" />
            <input type="hidden" name="id" value="${contato.idContato}"/>
            
            <label for="nome">Nome:</label><input type="text" autofocus required id="nome" name="nome" title="Digite o Nome" maxlength="128" value="${contato.nome}" /><br />
            <label for="dataNasc">Nascimento:</label><input type="date" required id="dataNasc" name="dataNasc" title="Digite a Data de Nascimento (dia-mês-ano)" pattern="^(0[1-9]|[12][0-9]|3[01])(/)(0[1-9]|1[012])(/)(19|20)\d\d$" value="${contato.strDataNasc}" /><br />
            <label for="cel">Celular:</label><input type="text" required id="cel" name="cel" title="Digite o Número do Celular" pattern="^[0-9]{2}[ ]{1}[0-9]{4,5}[-]{1}[0-9]{4}$" placeholder="99 99999-9999" maxlength="32" value="${contato.cel}" /><br />
            <label for="idOperadora">Operadora:</label>
            <select required id="idOperadora" name="idOperadora" title="Selecione a Operadora do Celular">
                <c:forEach var="operadora" items="${operadoras}">
                    <option value="${operadora.idOperadora}" <c:if test="${contato.idOperadora == operadora.idOperadora}">selected</c:if> >${operadora.nome}</option>
                </c:forEach>
            </select>
            <br />
            <label for="idEstado">Estado:</label>
            <select required id="idEstado" name="idEstado" title="Selecione o Estado" onchange="dropdownCidade(this.value)">
                <c:forEach var="estado" items="${estados}">
                    <option value="${estado.idEstado}" <c:if test="${contato.cidade.idEstado == estado.idEstado}">selected</c:if> >${estado.sigla}</option>
                </c:forEach>
            </select>
            <br />
            <label for="idCidade">Cidade:</label>
            <select required id="idCidade" name="idCidade" title="Selecione a Cidade">
                <c:forEach var="cidade" items="${cidades}">
                    <option value="${cidade.idCidade}" <c:if test="${contato.idCidade == cidade.idCidade}">selected</c:if> >${cidade.nome}</option>
                </c:forEach>
            </select>
            <br />
            <label for="email">Email:</label><input type="email" required id="email" name="email" maxlength="256" value="${contato.email}" /><br />
            <label>Facebook:</label><input type="text" id="facebook" name="facebook" maxlength="256" value="${contato.facebook}" /><br />
            <label>Twitter:</label><input type="text" id="twitter" name="twitter" maxlength="256" value="${contato.twitter}" /><br />
            <label>Site:</label><input type="text" id="site" name="site" maxlength="256" value="${contato.site}" /><br />
            <br />
            <input type="image" src="images/edit.png" width="64" height="64" title="Editar" class="box" onclick="validar();" />
            <input type="image" src="images/cancel.png" width="64" height="64" title="Cancelar" class="box" onclick="window.history.go(-1)" />
        </form>-->--%>
    
    <c:import url="../footer.jsp"></c:import>
    