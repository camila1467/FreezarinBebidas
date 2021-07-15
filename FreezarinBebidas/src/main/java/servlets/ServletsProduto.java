package servlets;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import dao.DaoProduto;
import model.BeanProduto;
import model.ModelLogin;

@WebServlet("/salvarProduto")
public class ServletsProduto extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DaoProduto daoProduto = new DaoProduto();

	public ServletsProduto() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			String produto = request.getParameter("produto");
   		 String acao  = request.getParameter("acao");

 /*if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {
    			 
    			 String idUser = request.getParameter("id");
    			 
    			 daoProduto.deletarUser(idUser);
    			 
    			 request.setAttribute("msg", "Excluido com sucesso!");
    			 request.getRequestDispatcher("/cadastroProduto.jsp").forward(request, response);
    		 }*/
    		 if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarajax")) {
    				 
    				 String idUser = request.getParameter("id");
    				 
    				 daoProduto.deletarUser(idUser);
    				 
    				 response.getWriter().write("Excluido com sucesso!");

    					RequestDispatcher view = request.getRequestDispatcher("/cadastroProduto.jsp");
    				 view.forward(request, response);
    		 }
    		 
			
		/*	else if (acao.equalsIgnoreCase("editar")) {
				BeanProduto beanCursoJsp = daoProduto.consultar(produto);
				RequestDispatcher view = request
						.getRequestDispatcher("/cadastroProduto.jsp");
				request.setAttribute("produto", beanCursoJsp);
				view.forward(request, response);
			} else if (acao.equalsIgnoreCase("listartodos")) {

				RequestDispatcher view = request
						.getRequestDispatcher("/cadastroProduto.jsp");
				request.setAttribute("produtos", daoProduto.listar());
				view.forward(request, response);
			}*/

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		BeanProduto BeanProduto =  new BeanProduto();

		String acao = request.getParameter("acao");

		if (acao != null && acao.equalsIgnoreCase("reset")) {
			try {
				RequestDispatcher view = request.getRequestDispatcher("/cadastroProduto.jsp");
				request.setAttribute("produtos", daoProduto.listar());
				view.forward(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {

			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String quantidade = request.getParameter("quantidade");
			String valor = request.getParameter("valor");

			
	BeanProduto produto = new BeanProduto();
    		
	produto.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
	produto.setNome(nome);

			try {

				String msg = null;
				boolean podeInserir = true;

				if (valor == null || valor.isEmpty()) {
					msg = "Valor R$ deve ser informado";
					podeInserir = false;

				} else if (nome == null || nome.isEmpty()) {
					msg = "Nome deve ser informado";
					podeInserir = false;

				} else if (id == null || id.isEmpty()
						&& !daoProduto.validarNome(nome)) {// QUANDO
															// FDOR
															// PRODUTO
															// NOVO
					msg = "Produto já existe com o mesmo nome!";
					podeInserir = false;

				}

				BeanProduto produto1= new BeanProduto();
				produto1.setNome(nome);
				produto1.setId(!id.isEmpty() ? Long.parseLong(id) : null);

				if (quantidade != null && !quantidade.isEmpty()) {
					produto.setQuantidade(Double.parseDouble(quantidade));
				}

				if (valor != null && !valor.isEmpty()){
					String valorParse = valor.replaceAll("\\.", "");// 10500,20
					valorParse = valorParse.replaceAll("\\,", ".");//10500.20
					produto.setValor(Double.parseDouble(valorParse)); 
				}
				
				if (msg != null) {
					request.setAttribute("msg", msg);
				} else if (id == null || id.isEmpty()
						&& daoProduto.validarNome(nome) && podeInserir) {

					daoProduto.salvar(produto);

				} else if (id != null && !id.isEmpty() && podeInserir) {
					daoProduto.atualizar(produto);
				}

				if (!podeInserir) {
					request.setAttribute("produto", produto);
				}

				RequestDispatcher view = request.getRequestDispatcher("/cadastroProduto.jsp");
				
				request.setAttribute("produto", produto);
				request.setAttribute("produtos", daoProduto.listar());
				view.forward(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}