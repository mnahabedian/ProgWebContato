/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufms.facom.progweb.syscontato.servlet;

import br.ufms.facom.progweb.syscontato.controller.CidadeJpaController;
import br.ufms.facom.progweb.syscontato.controller.ContatoJpaController;
import br.ufms.facom.progweb.syscontato.controller.EstadoJpaController;
import br.ufms.facom.progweb.syscontato.model.Cidade;
import br.ufms.facom.progweb.syscontato.model.Contato;
import br.ufms.facom.progweb.syscontato.model.Estado;
import br.ufms.facom.progweb.syscontato.model.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ContatoCRUDServlet extends HttpServlet {
    
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
            throws ServletException, IOException{
        RequestDispatcher rd;
        
        // Persistência
        ContatoJpaController contatoJpaController = new ContatoJpaController(utx, emf);
        EstadoJpaController estadoJpaController = new EstadoJpaController(utx, emf);
        CidadeJpaController cidadeJpaController = new CidadeJpaController(utx, emf);
        
        // Tratamento de Permissões
        Usuario usuario = null;
        if((usuario = (Usuario) request.getSession().getAttribute("usuario")) == null)
        {
            rd = request.getRequestDispatcher("login.jsp");
            rd.forward(request, response);
            
            return;
        }
        else
        {
            switch (usuario.getTipo().toUpperCase()) {
                case "ROOT":
                    request.setAttribute("create", 1);
                    request.setAttribute("read", 1);
                    request.setAttribute("update", 1);
                    request.setAttribute("delete", 1);
                    break;
                    
                case "REGULAR":
                    request.setAttribute("create", 0);
                    request.setAttribute("read", 1);
                    request.setAttribute("update", 0);
                    request.setAttribute("delete", 0);
                    break;
                    
                default:
                    request.setAttribute("create", 0);
                    request.setAttribute("read", 0);
                    request.setAttribute("update", 0);
                    request.setAttribute("delete", 0);
                    break;
                           
            }
        }
        
        // Tratamento de Ações e suas Opções
        Contato contato = null;
        switch (request.getParameter("action")) {
            case "create":
                switch (request.getParameter("option")) {
                    case "show":
                        List<Estado> estados = estadoJpaController.findEstadoEntities();
                        request.setAttribute("estados", estados);
                        
                        rd = request.getRequestDispatcher("contato/create.jsp");
                        rd.forward(request, response);
                        break;
                                
                    case "ok":
                        contato = new Contato();
                        contato.setNome(request.getParameter("nome"));
                        contato.setDataNasc(request.getParameter("dataNasc"));
                        contato.setCel(request.getParameter("cel"));
                        contato.setOperadoraCel(request.getParameter("operadoraCel"));
                        Cidade cidade = cidadeJpaController.findCidade(Integer.parseInt(request.getParameter("idCidade")));
                        contato.setIdCidade(cidade);
                        contato.setEmail(request.getParameter("email"));
                        //contato.setFacebook(request.getParameter("facebook"));
                        //contato.setTwitter(request.getParameter("twitter"));
                        //contato.setSite(request.getParameter("site"));
                        //contato.setFoto(request.getParameter("foto"));
                        
                        try {
                            contatoJpaController.create(contato);
                        } catch (Exception ex) {
                            Logger.getLogger(ContatoCRUDServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }                        
                        
                        response.setContentType("text/html;charset=windows-1252");
                        response.getWriter().print("<script>window.location='ContatoCRUDServlet?action=read&option=list';</script>");
                        break;

                    case "cancel":
                        response.setContentType("text/html;charset=windows-1252");
                        response.getWriter().print("<script>window.location='ContatoCRUDServlet?action=read&option=list';</script>");
                        break;
                                
                    default:
                        break;
                }
                break;
                
            case "read":
                switch (request.getParameter("option")) {
                    case "view":
                        break;
                        
                    case "list":
                        List<Contato> contatos = contatoJpaController.findContatoEntities();

                        request.setAttribute("contatos", contatos);

                        rd = request.getRequestDispatcher("contato/list.jsp");
                        rd.forward(request, response);
                        break;
                        
                    default:
                        break;
                }
                break;
                
            case "update":
                switch (request.getParameter("option")) {
                    case "show":
                        break;
                                
                    case "ok":
                        break;

                    case "cancel":
                        break;
                                
                    default:
                        break;
                }
                break;
                
            case "delete":
                switch (request.getParameter("option")) {
                    case "show":
                        break;
                                
                    case "ok":
                        break;

                    case "cancel":
                        break;
                                
                    default:
                        break;
                }
                break;
                
            default:
                response.setContentType("text/html;charset=windows-1252");
                try (PrintWriter out = response.getWriter()) {
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Servlet ContatoCRUDServlet</title>");            
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<h1> Action:" + request.getParameter("action") + "; ID:" + request.getParameter("id") + ".</h1>");
                    out.println("</body>");
                    out.println("</html>");
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
