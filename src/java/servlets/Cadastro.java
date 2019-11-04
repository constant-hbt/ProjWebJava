/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import controller.UsuariosControllerH;
import controller.UsuariosJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import servlets.utils.FormatacaoDatas;

/**
 *
 * @author henrique
 */
@WebServlet(name = "Cadastro", urlPatterns = {"/Cadastro"})
public class Cadastro extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet Cadastro</title>");            
        out.println("</head>");
        out.println("<body>");
        try{  
            
            String nome = request.getParameter("nome");
            String cpf = request.getParameter("cpf");
            String rg = request.getParameter("rg");
            String email = request.getParameter("email");
            String telefone = request.getParameter("telefone");
            String dataNascimento = request.getParameter("dataNasc");
            String rua = request.getParameter("rua");
            String numero = request.getParameter("numero");
            String bairro = request.getParameter("bairro");
            String cidade = request.getParameter("cidade");
            String cep = request.getParameter("cep");
            String estado = request.getParameter("estado");
            String senha = request.getParameter("senha");
            String confSenha = request.getParameter("confSenha");
            
            if(!senha.equals(confSenha)){
                
            } 
            
            Date dataNasc = FormatacaoDatas.formataData(dataNascimento);
            
            String endereco = "";
            endereco = rua + " | " + numero + " | " + bairro + " | " + cidade + " | " + cep  + " | " + estado;
            
            model.Usuarios user = new model.Usuarios(null, nome, cpf, rg, email, senha, telefone, dataNasc, endereco); 
            UsuariosControllerH DAO = new UsuariosControllerH();
            
            if(DAO.create(user)){
                out.println("<p>Salvou no banco!</p>");
            }else{
                out.println("<p>Erro ao salvar no banco!</p>");
            }
        }catch(Exception erro){
            System.out.println("Erro: " + erro.getMessage());
        }
        out.print("<h1> SERÁ? </h1>");
        out.println("</body>");
        out.println("</html>");
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
