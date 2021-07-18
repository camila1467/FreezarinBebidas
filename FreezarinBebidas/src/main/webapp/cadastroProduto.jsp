<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    
<!DOCTYPE html>
<html lang="en">


<jsp:include page="head.jsp"></jsp:include>

  <body>
  <!-- Pre-loader start -->
  
  <jsp:include page="theme-loader.jsp"></jsp:include>
  
  <!-- Pre-loader end -->
  <div id="pcoded" class="pcoded">
      <div class="pcoded-overlay-box"></div>
      <div class="pcoded-container navbar-wrapper">
          
          <jsp:include page="navbar.jsp"></jsp:include>

          <div class="pcoded-main-container">
              <div class="pcoded-wrapper">
                  
                  <jsp:include page="navbarmainmenu.jsp"></jsp:include>
                  
                  <div class="pcoded-content">
                      <!-- Page-header start -->
                      
                      <jsp:include page="page-header.jsp"></jsp:include>
                      
                      <!-- Page-header end -->
                        <div class="pcoded-inner-content">
                            <!-- Main-body start -->
                            <div class="main-body">
                                <div class="page-wrapper">
                                    <!-- Page-body start -->
                                    <div class="page-body">
                                              
                                           <div class="row">
                                            <div class="col-sm-12">
                                                <!-- Basic Form Inputs card start -->
                                                <div class="card">
                                                    <div class="card-block">
                                                        <h4 class="sub-title">Cad.produto</h4>
		                                              
          												 <form class="form-material" action="<%= request.getContextPath() %>/salvarProduto" method="post" id="formUser" >
          												    
          												    <input type="hidden" name="acao" id="acao" value="">
          												 
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="id" id="id" class="form-control"  readonly="readonly" value="${produto.id}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">ID:</label>
                                                            </div>
                                                            
                                                              <div class="form-group form-default form-static-label">
                                                                <input type="text" name="nome" id="nome" class="form-control" required="required" value="${produto.nome}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Nome:</label>
                                                            </div>
                                                            
                                                             <div class="form-group form-default form-static-label">
                                                                <input type="text" name="quantidade" id="quantidade" class="form-control" required="required" value="${produto.quantidade}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Quantidade:</label>
                                                            </div>
                                                            
                                                           <div class="form-group form-default form-static-label">
                                                                <input type="text" id="valor" name="valor"  class="form-control" required="required"  value="${produto.valor}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Valor R$:</label>
                                                            </div>
                                                            <div class="input-group mb-3">
  <div class="input-group-prepend">
    <label class="input-group-text" for="inputGroupSelect01">Opção</label>
  </div>
  <select class="custom-select" id="inputGroupSelect01" name="escolha">
    <option selected>escolher...</option>
    <option value="1">Bebida alcoólica</option>
    <option value="2">Bebida não alcoólica</option>
  </select>
</div>
                                                            
                                                            
                                                            <button type="button" class="btn btn-primary waves-effect waves-light" onclick="limparForm();" >Novo</button>
												            <button  class="btn btn-success waves-effect waves-light" >Salvar</button>
												            <button type="button"  class="btn btn-info waves-effect waves-light" onclick="criarDelete();" >Excluir</button>
       
                                                     </form> 
                                                   
                                                </div>
                                                </div>
                                                </div>
                                                </div>
                                                <span>${msg}</span>
                                                
                                    </div>
                                    <!-- Page-body end -->
                                </div>
                                <div id="styleSelector"> </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
   

<jsp:include page="javascripfile.jsp"></jsp:include>


<!-- Modal -->
<div class="modal fade" id="exampleModalUsuario" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Pesquisa de usuário</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">

	  <input type="text" class="form-control" placeholder="Nome" aria-label="nome" id="nomeBusca" aria-describedby="basic-addon2">
	  <div class="input-group-append">
	    <button class="btn btn-success" type="button" onclick="buscarUsuario();">Buscar</button>
	  </div>
	</div>
	
<table class="table" id="tabelaresultados">
  <thead>
    <tr>
      <th scope="col">ID</th>
      <th scope="col">Nome</th>
      <th scope="col">Ver</th>
     
    </tr>
  </thead>
  
</table>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Fechar</button>
      </div>
    </div>
  </div>
</div>
	<script type="text/javascript">
		function validarCampos() {
			if(document.getElementById("nome").value == '') {
				alert("Informe o Nome!");
				return false;
			} else if(document.getElementById("quantidade").value == '') {
				alert("Informe a Quantidade!");
				return false;
			} else if(document.getElementById("valor").value == '') {
				alert("Informe o Valor!");
				return false;
			}
			return true;
		}

		function criarDelete() {
		    
		    if(confirm('Deseja realmente excluir os dados?')) {
			
			    document.getElementById("formUser").method = 'get';
			    document.getElementById("acao").value = 'deletar';
			    document.getElementById("formUser").submit();
			    
		    }
		    
		}
		function limparForm() {
		    
		    var elementos = document.getElementById("formUser").elements; /*Retorna os elementos html dentro do form*/
		    
		    for (p = 0; p < elementos.length; p ++){
			    elementos[p].value = '';
		    }
		}
		
		
		function criarDeleteComAjax() {
		    
		    if (confirm('Deseja realmente excluir os dados?')){
			
			 var urlAction = document.getElementById('formUser').action;
			 var idUser = document.getElementById('nome1').value;
			 
			 $.ajax({
			     
			     method: "get",
			     url : urlAction,
			     data : "nome=" + idUser + '&acao=deletarajax',
			     success: function (response) {
				 
				  limparForm();
				  document.getElementById('msg').textContent = response;
			     }
			     
			 }).fail(function(xhr, status, errorThrown){
			    alert('Erro ao deletar usuário por id: ' + xhr.responseText);
			 });
			 
			  
		    }
		    
		}

	</script><script type="text/javascript">

$(function() {
    $('#valor').maskMoney();
 });
 
 
 $(document).ready(function() {
  $("#quantidade").keyup(function() {
      $("#quantidade").val(this.value.match(/[0-9]*/));
  });
});

</script>
</body>




</html>