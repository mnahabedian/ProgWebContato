/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufms.facom.progweb.syscontato.servlet;

import br.ufms.facom.progweb.syscontato.controller.UsuarioJpaController;
import br.ufms.facom.progweb.syscontato.model.Usuario;
import java.io.IOException;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

/**
 *
 * @author joshua
 */
public class SessionServlet extends HttpServlet {
    
    @PersistenceUnit
    private EntityManagerFactory emf;
    @Resource
    private UserTransaction utx;
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd;
        
        // Persistência
        UsuarioJpaController jpaController = new UsuarioJpaController(utx, emf);
        
        // Tratamento de Ações e suas Opções
        Usuario usuario = null;
        switch (request.getParameter("action")) {
            case "login":
                usuario = jpaController.findUsuarioByLoginSenha(request.getParameter("login"), request.getParameter("senha"));
                request.getSession().setAttribute("usuario", usuario);
                
                rd = request.getRequestDispatcher("index.jsp");
                rd.forward(request, response);                
                break;
                
            case "logout":
                request.getSession().setAttribute("usuario", null);
                
                rd = request.getRequestDispatcher("login.jsp");
                rd.forward(request, response);                
                break;
                
            case "check":
            default:
                if((usuario = (Usuario) request.getSession().getAttribute("usuario")) == null)
                {
                    rd = request.getRequestDispatcher("login.jsp");
                    rd.forward(request, response);
                }
                break;
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
