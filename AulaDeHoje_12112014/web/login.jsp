<%-- 
    Document   : login
    Created on : 12/11/2014, 09:28:18
    Author     : Hercules Sandim
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Sistema - Login</title>
        <script src="cript.js"></script>
        <script src="functions.js"></script>
    </head>
    <body>
	<form action="UsuarioLogin" method="post" onSubmit="return criptografaSenha();">
            <input type="hidden" name="confirma" value="1"/>

            <label>Nome de Usuario: </label><input type="text" name="login" id="ID_login" value="" /><br/>
            <label>Senha:</label><input type="password" name="senha" id="ID_senha" value=""/><br/>

            <input type="submit" value="Entrar" />
        </form>
    </body>
</html>
