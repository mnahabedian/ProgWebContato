<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

    <c:import url="header.jsp"></c:import>

        <script src="cript.js" type="text/javascript"></script>
        <script type="text/javascript">
            function criptografaSenha()
            {
                document.getElementById('senha').value = calcMD5(document.getElementById('senha').value);
                document.getElementById('loginForm').submit();
            }            
        </script>
        
        <fieldset class="row panel large-3">
            <div class="large-12 column">
                <form id="loginForm" action="SessionServlet" method="post">
                    <input type="hidden" name="action" value="login"/>
                    <label class="label">Login:</label>
                    <input type="text" autofocus required id="login" name="login" title="Digite o Login" maxlength="32" value="" />
                    <label class="label">Senha:</label>
                    <input type="password" required id="senha" name="senha" title="Digite a Senha" maxlength="32" value="" />
                    <input class="button expand" type="button" value="Entrar" title="Login" onclick="criptografaSenha();" />
                    <!--<input type="image" src="images/login.png" width="64" height="64" title="Login" class="box" onclick="criptografaSenha();" />-->
                </form>
            </div>
        </fieldset>
    
    <c:import url="footer.jsp"></c:import>
    