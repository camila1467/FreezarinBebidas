package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DaoProduto1;
import model.BeanProduto;

/**
 * Servlet implementation class ServletProdutoController
 */
@WebServlet("/salvarProduto")
public class ServletProdutoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DaoProduto1 daoProduto = new DaoProduto1();

    
	 public ServletProdutoController() {
	    }

	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
	    	try {	
	    		String acao = request.getParameter("acao") != null ? request.getParameter("acao") : "listartodos";
				String produto = request.getParameter("produto");

				if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {
	    			 
	    			 String nome1 = request.getParameter("nome");
	    			 
	    			 daoProduto.deletarUser(nome1);
	    			 
	    			 request.setAttribute("msg", "Excluido com sucesso!");
	    			 request.getRequestDispatcher("/cadastroProduto.jsp").forward(request, response);
	    		 }
	  
	  
				else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarajax")) {
			BeanProduto beanProduto = daoProduto.consultarUsuario(produto);

    				 String idUser = request.getParameter("nome");
    				 daoProduto.deletarUser(idUser);
    				 response.getWriter().write("Excluido com sucesso!");
    					RequestDispatcher view = request
    							.getRequestDispatcher("/cadastroProduto.jsp");
    					request.setAttribute("produto", beanProduto);
    					view.forward(request, response);
    		 } else if (acao.equalsIgnoreCase("editar")) {
					BeanProduto beanProduto = daoProduto.consultarUsuario(produto);
					RequestDispatcher view = request
							.getRequestDispatcher("/cadastroProduto.jsp");
					request.setAttribute("produto", beanProduto);
					view.forward(request, response);
				} else if (acao.equalsIgnoreCase("listartodos")) {

					RequestDispatcher view = request
							.getRequestDispatcher("/tabelaDeProdutos.jsp");
					request.setAttribute("produtos", daoProduto);
					view.forward(request, response);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		protected void doPost(HttpServletRequest request,
				HttpServletResponse response) throws ServletException, IOException {
				String id = request.getParameter("id");
				String nome = request.getParameter("nome");
				String quantidade = request.getParameter("quantidade");
				String valor = request.getParameter("valor");
/*
				BeanProduto beanProduto= new BeanProduto();
beanProduto.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
beanProduto.setNome(nome);
beanProduto.setValor(Float.parseFloat(valor));
beanProduto.setQuantidade(Integer.parseInt(quantidade));

*/
				try {

					String msg = null;
					boolean podeInserir = true;

					
					
					if (quantidade == null || quantidade.isEmpty()) {
						msg = "Quantidade deve ser informado";
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

					BeanProduto produto = new BeanProduto();
					produto.setNome(nome);
					produto.setId((id != null && !id.isEmpty()) ? Long.parseLong(id) : null);

					
					if (quantidade != null && !quantidade.isEmpty()) {
						produto.setQuantidade(Integer.parseInt(quantidade));
					}

					if (valor != null && !valor.isEmpty()){
						String valorParse = valor.replaceAll("\\.", "");// 10500,20
						valorParse = valorParse.replaceAll("\\,", ".");//10500.20
						produto.setValor(Float.parseFloat(valorParse)); 
					}
					
					if (msg != null) {
						request.setAttribute("msg", msg);
					}
					else if (id == null || id.isEmpty()
							&& daoProduto.validarNome(nome) && podeInserir) {

					produto =	daoProduto.salvar(produto);
					}
					if (msg != null) {
						request.setAttribute("msg", msg);
					}  else if (id != null && !id.isEmpty() && podeInserir) {
						daoProduto.atualizar(produto);
					}

					if (!podeInserir) {
						request.setAttribute("produto", produto);

					}
					//beanProduto=daoProduto.salvar(beanProduto);
		    		request.setAttribute("msg", msg);
		    		request.setAttribute("produto", produto);
		    		request.getRequestDispatcher("/cadastroProduto.jsp").forward(request, response);
		    		//request.setAttribute("produtos", daoProduto.listar());
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}

	