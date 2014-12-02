<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <c:import url="../header.jsp"></c:import>
    
    <c:if test="${create == 0}">
        <script>window.location="denied.jsp";</script>
    </c:if>
        
        
    <section class="row panel large-5">

        <fieldset class="large-12 column">
            <legend><h3 class="centered">Dados</h3></legend>
            <form action="ContatoCRUDServlet" method="post">
                <input type="hidden" id="action" name="action" value="create" />
                <input type="hidden" id="option" name="option" value="ok" />

                    <div class="row">
                        <div class="large-12 columns">
                          <div class="row collapse prefix-radius">
                            <div class="small-2 columns">
                                <a class="button prefix" for="nome">Nome</a>
                            </div>
                            <div class="small-10 columns">
                                <input type="text" autofocus required id="nome" name="nome" title="Digite o Nome" maxlength="128" value="" />
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
                                <input type="date" required id="dataNasc" name="dataNasc" title="Digite a Data de Nascimento (dia-mês-ano)" pattern="^(0[1-9]|[12][0-9]|3[01])(-)(0[1-9]|1[012])(-)(19|20)\d\d$" maxlength="10" min="1900-01-01" value="" />
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
                                <input type="text" required id="cel" name="cel" title="Digite o Número do Celular" pattern="^[0-9]{2}[ ]{1}[0-9]{4,5}[-]{1}[0-9]{4}$" placeholder="99 99999-9999" maxlength="32" value="" />
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
                                    <option value="" selected></option>
                                    <c:forEach var="operadora" items="${operadoras}">
                                        <option value="${operadora.idOperadora}">${operadora.nome}</option>
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
                                    <option value="" selected></option>
                                    <c:forEach var="estado" items="${estados}">
                                        <option value="${estado.idEstado}">${estado.sigla}</option>
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
                                <input type="email" required id="email" name="email" maxlength="256" value="" />
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
                                <input type="text" id="facebook" name="facebook" maxlength="256" value="" />
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
                                <input type="text" id="twitter" name="twitter" maxlength="256" value="" />
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
                                <input type="text" id="site" name="site" maxlength="256" value="" />
                            </div>
                          </div>
                        </div>                
                    </div>

                    <ul class="button-group">
                        <li class="large-6"><input class="button success large-12" type="submit" value="Incluir" title="Incluir" onclick="validar();" /></li>
                        <li class="large-6"><input class="button alert large-12" type="button" value="Cancelar" title="Cancelar" onclick="window.history.go(-1)" /></li>
                    </ul>

                <!--<input type="image" src="images/insert.png" width="64" height="64" title="Incluir" class="box" onclick="validar();" />
                <input type="image" src="images/cancel.png" width="64" height="64" title="Cancelar" class="box" onclick="window.history.go(-1)" />-->
            </form>
        </fieldset>        
    </section>
        
    <c:import url="../footer.jsp"></c:import>
