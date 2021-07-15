package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOUsuarioRepository;
import model.ModelLogin;

@WebServlet("/ServletUsuarioController")
public class ServletUsuarioController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();

    public ServletUsuarioController() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
    	try {	
    		
    		 String acao  = request.getParameter("acao");
    		 
    		 if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {
    			 
    			 String idUser = request.getParameter("id");
    			 
    			 daoUsuarioRepository.deletarUser(idUser);
    			 
    			 request.setAttribute("msg", "Excluido com sucesso!");
    			 request.getRequestDispatcher("usuario.jsp").forward(request, response);
    		 }
    		 else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarajax")) {
    				 
    				 String idUser = request.getParameter("id");
    				 
    				 daoUsuarioRepository.deletarUser(idUser);
    				 
    				 response.getWriter().write("Excluido com sucesso!");
    				 
    		 }
    		 
    		 else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjax")) {
    			 
    			 String nomeBusca = request.getParameter("nomeBusca");
    			 
    			 List<ModelLogin> dadosJsonUser =  daoUsuarioRepository.consultaUsuarioList(nomeBusca);
    			 
    			// ObjectMapper mapper = new ObjectMapper();
    			 
    			 //String json = mapper.writeValueAsString(dadosJsonUser);
    			 
    			// response.getWriter().write(json);
    			 
    		 }
    		 else {
    			 request.getRequestDispatcher("usuario.jsp").forward(request, response);
    		 }
    		 
    		
    		 
    		 
    		}catch (Exception e) {
    			e.printStackTrace();
    			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
    			request.setAttribute("msg", e.getMessage());
    			redirecionar.forward(request, response);
    		}

    	}

    	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    		
    		try {
    			
    		String msg = "Operação realizada com sucesso!";	
    		
    		String id = request.getParameter("id");
    		String nome = request.getParameter("nome");
    		String email = request.getParameter("email");
    		String login = request.getParameter("login");
    		String senha = request.getParameter("senha");
    		
    		ModelLogin modelLogin = new ModelLogin();
    		
    		modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
    		modelLogin.setNome(nome);
    		modelLogin.setEmail(email);
    		modelLogin.setLogin(login);
    		modelLogin.setSenha(senha);
    		
    		
    		if (daoUsuarioRepository.validarLogin(modelLogin.getLogin()) && modelLogin.getId() == null) {
    			msg = "Já existe usuário com o mesmo login, informe outro login;";
    		}else {
    			if (modelLogin.isNovo()) {
    				msg = "Gravado com sucesso!";
    			}else {
    				msg= "Atualizado com sucesso!";
    			}
    			
    		    modelLogin = daoUsuarioRepository.gravarUsuario(modelLogin);
    		}
    		
    		
    		request.setAttribute("msg", msg);
    		request.setAttribute("modolLogin", modelLogin);
    		request.getRequestDispatcher("usuario.jsp").forward(request, response);
    		
    		} catch (Exception e) {
    			e.printStackTrace();
    			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
    			request.setAttribute("msg", e.getMessage());
    			redirecionar.forward(request, response);
    		}
    		
    	}

    }
