/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufms.facom.progweb.syscontato.servlet;

import br.ufms.facom.progweb.syscontato.controller.CidadeJpaController;
import br.ufms.facom.progweb.syscontato.controller.ContatoJpaController;
import br.ufms.facom.progweb.syscontato.controller.EstadoJpaController;
import br.ufms.facom.progweb.syscontato.controller.OperadoraJpaController;
import br.ufms.facom.progweb.syscontato.controller.exceptions.RollbackFailureException;
import br.ufms.facom.progweb.syscontato.model.Cidade;
import br.ufms.facom.progweb.syscontato.model.Contato;
import br.ufms.facom.progweb.syscontato.model.Estado;
import br.ufms.facom.progweb.syscontato.model.Operadora;
import br.ufms.facom.progweb.syscontato.model.Usuario;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import javax.servlet.http.Part;
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
        
        if (request.getSession().getAttribute("msg_show") != null) {
            request.getSession().setAttribute("msg_show", null);
        }
        else {
            request.getSession().setAttribute("msg_tipo", null);
            request.getSession().setAttribute("msg", null);
        }
        
        
        // Persistência
        ContatoJpaController contatoJpaController = new ContatoJpaController(utx, emf);
        OperadoraJpaController operadoraJpaController = new OperadoraJpaController(utx, emf);
        EstadoJpaController estadoJpaController = new EstadoJpaController(utx, emf);
        CidadeJpaController cidadeJpaController = new CidadeJpaController(utx, emf);
        
        // Tratamento de Permissões
        Usuario usuario;
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
        Contato contato;
        switch (request.getParameter("action")) {

            case "create":
                switch (request.getParameter("option")) {
                    case "show":
                        List<Operadora> operadoras = operadoraJpaController.findOperadoraEntities();
                        request.setAttribute("operadoras", operadoras);
                        
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
                        Operadora operadora = operadoraJpaController.findOperadora(Integer.parseInt(request.getParameter("idOperadora")));
                        contato.setOperadora(operadora);
                        Cidade cidade = cidadeJpaController.findCidade(Integer.parseInt(request.getParameter("idCidade")));
                        contato.setCidade(cidade);
                        contato.setEmail(request.getParameter("email"));
                        contato.setFacebook(request.getParameter("facebook"));
                        contato.setTwitter(request.getParameter("twitter"));
                        contato.setSite(request.getParameter("site"));
                        
                        if(contato.validar(contato)){
                            try {
                                contatoJpaController.create(contato);
                            } catch (Exception ex) {
                                Logger.getLogger(ContatoCRUDServlet.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            request.getSession().setAttribute("msg_tipo", "notice");
                            request.getSession().setAttribute("msg", "Contato Incluído com Sucesso!!!");
                            request.getSession().setAttribute("msg_show", "1");
                        }
                        else{
                            request.getSession().setAttribute("msg_tipo", "error");
                            request.getSession().setAttribute("msg", "ERROR! O contato não foi Salvo!!! Tente Novamente.");
                            request.getSession().setAttribute("msg_show", "1");
                        }
                        
                        rd = request.getRequestDispatcher("default.jsp");
                        rd.forward(request, response);
                        break;

                    default:
                        break;
                }
                break;
                
            case "read":
                switch (request.getParameter("option")) {
                    case "view":
                        contato = contatoJpaController.findContato(Integer.parseInt(request.getParameter("id")));
                        request.setAttribute("contato", contato);

                        List<Operadora> operadoras = operadoraJpaController.findOperadoraEntities();
                        request.setAttribute("operadoras", operadoras);
                        
                        List<Estado> estados = estadoJpaController.findEstadoEntities();
                        request.setAttribute("estados", estados);
                        
                        rd = request.getRequestDispatcher("contato/read.jsp");
                        rd.forward(request, response);
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
                        contato = contatoJpaController.findContato(Integer.parseInt(request.getParameter("id")));
                        request.setAttribute("contato", contato);

                        List<Operadora> operadoras = operadoraJpaController.findOperadoraEntities();
                        request.setAttribute("operadoras", operadoras);
                        
                        List<Estado> estados = estadoJpaController.findEstadoEntities();
                        request.setAttribute("estados", estados);

                        List<Cidade> cidades = contato.getCidade().getEstado().getCidadeList();
                        request.setAttribute("cidades", cidades);
                        
                        rd = request.getRequestDispatcher("contato/update.jsp");
                        rd.forward(request, response);
                        break;
                                
                    case "ok":
                        contato = contatoJpaController.findContato(Integer.parseInt(request.getParameter("id")));

                        contato.setNome(request.getParameter("nome"));
                        contato.setDataNasc(request.getParameter("dataNasc"));
                        contato.setCel(request.getParameter("cel"));
                        Operadora operadora = operadoraJpaController.findOperadora(Integer.parseInt(request.getParameter("idOperadora")));
                        contato.setOperadora(operadora);
                        Cidade cidade = cidadeJpaController.findCidade(Integer.parseInt(request.getParameter("idCidade")));
                        contato.setCidade(cidade);
                        contato.setEmail(request.getParameter("email"));
                        contato.setFacebook(request.getParameter("facebook"));
                        contato.setTwitter(request.getParameter("twitter"));
                        contato.setSite(request.getParameter("site"));
                        
                        if(contato.validar(contato)){
                            try {
                                contatoJpaController.edit(contato);
                            } catch (RollbackFailureException ex) {
                                Logger.getLogger(FileUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (Exception ex) {
                                Logger.getLogger(FileUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
                            }                    
                        
                            request.getSession().setAttribute("msg_tipo", "notice");
                            request.getSession().setAttribute("msg", "Contato Alterado com Sucesso!!!");
                            request.getSession().setAttribute("msg_show", "1");
                        }
                        else{
                            request.getSession().setAttribute("msg_tipo", "error");
                            request.getSession().setAttribute("msg", "ERROR! O contato não foi Atualizado!!! Tente Novamente.");
                            request.getSession().setAttribute("msg_show", "1");
                        }
                        

                        rd = request.getRequestDispatcher("default.jsp");
                        rd.forward(request, response);
                        break;

                    default:
                        break;
                }
                break;
                
            case "delete":
                switch (request.getParameter("option")) {
                    case "show":
                        contato = contatoJpaController.findContato(Integer.parseInt(request.getParameter("id")));
                        request.setAttribute("contato", contato);
                        
                        rd = request.getRequestDispatcher("contato/delete.jsp");
                        rd.forward(request, response);
                        break;
                                
                    case "ok":
                        try {
                            contatoJpaController.destroy(Integer.parseInt(request.getParameter("id")));
                            request.getSession().setAttribute("msg_tipo", "notice");
                            request.getSession().setAttribute("msg", "Contato Excluído com Sucesso!!!");
                            request.getSession().setAttribute("msg_show", "1");
                        } catch (RollbackFailureException ex) {
                            Logger.getLogger(ContatoCRUDServlet.class.getName()).log(Level.SEVERE, null, ex);
                            request.getSession().setAttribute("msg_tipo", "error");
                            request.getSession().setAttribute("msg", "ERROR! O contato não foi Excluído!!! Tente novamente.");
                            request.getSession().setAttribute("msg_show", "1");
                        } catch (Exception ex) {
                            Logger.getLogger(ContatoCRUDServlet.class.getName()).log(Level.SEVERE, null, ex);
                            request.getSession().setAttribute("msg_tipo", "error");
                            request.getSession().setAttribute("msg", "ERROR! O contato não foi Excluído!!! Tente novamente.");
                            request.getSession().setAttribute("msg_show", "1");
                        }
                        
                        response.setContentType("text/html;charset=windows-1252");
                        response.getWriter().print("<script>window.location='ContatoCRUDServlet?action=read&option=list';</script>");
                        break;
                                
                    default:
                        break;
                }
                break;
                
            case "photo":
                switch (request.getParameter("option")) {
                    case "show":
                        contato = contatoJpaController.findContato(Integer.parseInt(request.getParameter("id")));
                        request.setAttribute("contato", contato);

                        rd = request.getRequestDispatcher("contato/photo.jsp");
                        rd.forward(request, response);
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
