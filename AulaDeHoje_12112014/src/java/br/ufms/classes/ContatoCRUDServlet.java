/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufms.classes;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author joshua
 */
public class ContatoCRUDServlet extends HttpServlet {

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
        // Tratamento de Permissões
        Usuario user;
        if((user = (Usuario) request.getSession().getAttribute("user")) == null)
        {
            response.setContentType("text/html;charset=windows-1252");
            response.getWriter().print("<script>window.location='login.jsp';</script>");
        }
        else
        {
            switch (user.getTipo().toUpperCase()) {
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
        RequestDispatcher rd;
        Contato contato = null;
        switch (request.getParameter("action")) {
            case "create":
                switch (request.getParameter("option")) {
                    case "show":
                        rd = request.getRequestDispatcher("create.jsp");
                        rd.forward(request, response);
                        break;
                                
                    case "ok":
                        try {
                            contato = new Contato(0,
                                    request.getParameter("nome"),
                                    request.getParameter("email"),
                                    request.getParameter("cel"),
                                    request.getParameter("operadora_cel"),
                                    request.getParameter("cidade"),
                                    request.getParameter("estado"),
                                    request.getParameter("data_nasc"));
                        } catch (ParseException ex) {
                            Logger.getLogger(ContatoCRUDServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        ContatoDao.insert(contato);
                        
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
                        contato = ContatoDao.findById(Integer.parseInt(request.getParameter("id")));

                        request.setAttribute("contato", contato);

                        rd = request.getRequestDispatcher("read.jsp");
                        rd.forward(request, response);
                        break;
                        
                    case "list":
                        List<Contato> contatos = ContatoDao.findAll();

                        request.setAttribute("contatos", contatos);

                        rd = request.getRequestDispatcher("list.jsp");
                        rd.forward(request, response);
                        break;
                        
                    default:
                        break;
                }
                break;
                
            case "update":
                switch (request.getParameter("option")) {
                    case "show":
                        contato = ContatoDao.findById(Integer.parseInt(request.getParameter("id")));

                        request.setAttribute("contato", contato);

                        rd = request.getRequestDispatcher("update.jsp");
                        rd.forward(request, response);
                        break;
                                
                    case "ok":
                        try {
                            contato = new Contato(Integer.parseInt(request.getParameter("id")),
                                    request.getParameter("nome"),
                                    request.getParameter("email"),
                                    request.getParameter("cel"),
                                    request.getParameter("operadora_cel"),
                                    request.getParameter("cidade"),
                                    request.getParameter("estado"),
                                    request.getParameter("data_nasc"));
                        } catch (ParseException ex) {
                            Logger.getLogger(ContatoCRUDServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        ContatoDao.update(contato);
                        
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
                
            case "delete":
                switch (request.getParameter("option")) {
                    case "show":
                        contato = ContatoDao.findById(Integer.parseInt(request.getParameter("id")));

                        request.setAttribute("contato", contato);
                        
                        rd = request.getRequestDispatcher("delete.jsp");
                        rd.forward(request, response);
                        break;
                                
                    case "ok":
                        contato = ContatoDao.findById(Integer.parseInt(request.getParameter("id")));
                        
                        ContatoDao.delete(contato);
                        
                        response.setContentType("text/html;charset=windows-1252");
                        response.getWriter().print("<script>window.location='ContatoCRUDServlet?action=read&option=list';</script>");
                        break;

                    case "cancel":
                        response.setContentType("text/html;charset=windows-1252");
                        response.getWriter().print("<script>window.location='ContatoCRUDServlet?action=read&option=list&option=list';</script>");
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
