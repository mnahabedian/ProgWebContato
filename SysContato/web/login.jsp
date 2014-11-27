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
        <section>
            <form id="loginForm" action="SessionServlet" method="post">
                <input type="hidden" name="action" value="login"/>
                <label>Login:</label><input type="text" autofocus required id="login" name="login" title="Digite o Login" maxlength="32" value="" /><br/>
                <label>Senha:</label><input type="password" required id="senha" name="senha" title="Digite a Senha" maxlength="32" value="" /><br/>
                <br />
                <input type="image" src="img/login.png" width="64" height="64" title="Login" class="box" onclick="criptografaSenha();" />
            </form>
        </section>
    
    <c:import url="footer.jsp"></c:import>
    