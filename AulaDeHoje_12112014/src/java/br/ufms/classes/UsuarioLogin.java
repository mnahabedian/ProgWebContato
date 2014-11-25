/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufms.classes;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Hercules Sandim
 */
public class UsuarioLogin extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        super.doGet(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String login= (String) request.getParameter("login");
        String senha= (String) request.getParameter("senha");
        
        Usuario user = UsuarioDao.getUsuarioByLoginAndPassword(login, senha);
        
        if( user != null)
        {
            request.getSession().setAttribute("user", user);
            response.sendRedirect("index.jsp");
        }
        else
        {
            response.getWriter().print("<script>alert('Usuario ou senha invalidos!!!');"+
                                        "window.location='login.jsp';</script>");
        }
        
    }

}