<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <c:import url="../header.jsp"></c:import>
    
    <c:if test="${update == 0}">
        <script>window.location="denied.jsp";</script>
    </c:if>
            
        <fieldset class="row panel large-5">
            <div class="large-12 column">
                <form action="FileUploadServlet" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="id" value="${contato.idContato}"/>
                    
                <fieldset>
                    <legend>Foto do Contato: ${contato.nome}</legend>
                    <input class="button secondary" type="file" id="foto" name="foto" />

                    <ul class="button-group">
                        <li class="large-6"><input class="button success large-12" type="submit" value="Salvar" title="Salvar" onclick="validar();" /></li>
                        <li class="large-6"><input class="button alert large-12" type="button" value="Cancelar" title="Cancelar" onclick="window.history.go(-1)" /></li>
                    </ul>
                    
                    <!--<input type="image" src="images/edit.png" width="64" height="64" title="Editar" class="box" onclick="validar();" />
                    <input type="image" src="images/cancel.png" width="64" height="64" title="Cancelar" class="box" onclick="window.history.go(-1)" />-->
                </fieldset>
                </form>
            </div>
        </fieldset>
    
    <c:import url="../footer.jsp"></c:import>
    