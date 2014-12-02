/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufms.facom.progweb.syscontato.servlet;

import br.ufms.facom.progweb.syscontato.controller.ContatoJpaController;
import br.ufms.facom.progweb.syscontato.controller.exceptions.RollbackFailureException;
import br.ufms.facom.progweb.syscontato.model.Contato;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.transaction.UserTransaction;

/**
 *
 * @author joshua
 */
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
                 maxFileSize=1024*1024*10,      // 10MB
                 maxRequestSize=1024*1024*50)   // 50MB
public class FileUploadServlet extends HttpServlet {
    
    @PersistenceUnit
    private EntityManagerFactory emf;
    @Resource
    private UserTransaction utx;

    private final static Logger LOGGER = Logger.getLogger(FileUploadServlet.class.getCanonicalName());
    private final String UPLOAD_DIR = "uploads";

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
        if (request.getContentType() != null &&
            request.getContentType().toLowerCase().indexOf("multipart/form-data") > -1 ) {

            int id = Integer.parseInt(request.getParameter("id"));
            ContatoJpaController contatoJpaController = new ContatoJpaController(utx, emf);
            Contato contato = contatoJpaController.findContato(id);
            
            final String path = request.getServletContext().getRealPath(UPLOAD_DIR);
            final Part filePart = request.getPart("foto");
            final String fileName = "foto_" + System.currentTimeMillis() + ".jpg";
    
            OutputStream out = null;
            InputStream fileContent = null;
            
            try {
                out = new FileOutputStream(new File(path + File.separator
                        + fileName));

                int read = 0;
                final byte[] bytes = new byte[1024];

                fileContent = filePart.getInputStream();
                while ((read = fileContent.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }

                contato.setFoto(UPLOAD_DIR + File.separator + fileName);
                try {
                    contatoJpaController.edit(contato);
                } catch (RollbackFailureException ex) {
                    Logger.getLogger(FileUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(FileUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

                request.getSession().setAttribute("msg_tipo", "notice");
                request.getSession().setAttribute("msg", "Foto Salva com Sucesso!!!");
                request.getSession().setAttribute("msg_show", "1");
                
                response.setContentType("text/html;charset=windows-1252");
                response.getWriter().print("<script>window.location='ContatoCRUDServlet?action=read&option=list';</script>");

                LOGGER.log(Level.INFO, "File {0} being uploaded to {1}",
                        new Object[]{fileName, path});
            } catch (FileNotFoundException fne) {
                LOGGER.log(Level.SEVERE, "Problems during file upload. Error: {0}",
                        new Object[]{fne.getMessage()});
            } finally {
                if (out != null) {
                    out.close();
                }
                if (fileContent != null) {
                    fileContent.close();
                }
            }
        }
    }
    
    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        LOGGER.log(Level.INFO, "Part Header = {0}", partHeader);
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }

        return null;
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
