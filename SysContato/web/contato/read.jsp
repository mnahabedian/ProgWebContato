<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <c:import url="../header.jsp"></c:import>
    
    <c:if test="${read == 0}">
        <script>window.location="denied.jsp";</script>
    </c:if>
            
    <section class="row panel large-12">

        <div class="row large-2">    
            <fieldset class="large-5 column centered">
                <legend><h3 class="centered">Foto</h3></legend>
                <div id="caixa-foto-read">
                    <img id="foto-read" src="${contato.foto}" />
                </div>
            </fieldset>

            <fieldset class="large-7 column right">
                <legend><h3 class="centered">Dados</h3></legend>
                <form action="ContatoCRUDServlet" method="post">
                    <input type="hidden" id="action" name="action" value="read" />
                    <input type="hidden" id="option" name="option" value="list" />

                    <div class="row">
                        <div class="large-12 columns">
                          <div class="row collapse prefix-radius">
                            <div class="small-2 columns">
                                <a class="button prefix" for="nome">Nome</a>
                            </div>
                            <div class="small-10 columns">
                              <input type="text" disabled="disabled" value="${contato.nome}" />
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
                                <input type="text" disabled="disabled" value="${contato.strDataNasc}" />
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
                                <input type="text" disabled="disabled" value="${contato.cel}" />
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
                                <input type="text" disabled="disabled" value="${contato.operadora.nome}" />
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
                                <input type="text" disabled="disabled" value="${contato.cidade.estado.sigla}" />
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
                                <input type="text" disabled="disabled" value="${contato.cidade.nome}" />
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
                                <input type="text" disabled="disabled" value="${contato.email}" />
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
                                <input type="text" disabled="disabled" value="${contato.facebook}" />
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
                                <input type="text" disabled="disabled" value="${contato.twitter}" />
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
                                <input type="text" disabled="disabled" value="${contato.site}" />
                            </div>
                          </div>
                        </div>                
                    </div>

                    <div class="row">
                        <div class="large-12 columns">
                          <div class="row collapse prefix-radius">
                            <div class="small-2 columns">
                                <a class="button prefix" for="foto">Foto</a>
                            </div>
                            <div class="small-10 columns">
                                <input type="text" disabled="disabled" value="${contato.foto}" />
                            </div>
                          </div>
                        </div>                
                    </div>

                    <input class="button warning radius expand" type="button" value="Voltar" title="Voltar" onclick="window.history.go(-1)" />
                    <!--<input type="image" src="images/view.png" width="64" height="64" title="Voltar" class="box" onclick="window.history.go(-1)" />-->
                </form>
            </fieldset>
        </div>
    </section>        
    <c:import url="../footer.jsp"></c:import>
    