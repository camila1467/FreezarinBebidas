package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBanco;
import model.BeanProduto;
import model.ModelLogin;


public class DaoProduto1 {
	private Connection connection;

	public DaoProduto1() {
		connection = SingleConnectionBanco.getConnection();
	}
	
	
	public BeanProduto salvar(BeanProduto produto) throws Exception {
		
			String sql = "INSERT INTO produto(nome, quantidade, valor) VALUES(?, ?, ?)";
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, produto.getNome());
			insert.setInt(2, produto.getQuantidade());
			insert.setFloat(3, produto.getValor());
			insert.execute();
			connection.commit();
		 
		return this.consultarUsuario(produto.getNome());

	}
		
	
	
	
	
public BeanProduto consultarUsuario(String  nome) throws Exception  {
		
	BeanProduto produto1 = new BeanProduto();
	
	String sql = "select * from produto where upper(nome) = upper('"+nome+"')";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultSet =  statement.executeQuery();
		
		while (resultSet.next()) /*Se tem resultado*/ {
			produto1.setId(resultSet.getLong("id"));
			produto1.setNome(resultSet.getString("nome"));
			produto1.setQuantidade(resultSet.getInt("quantidade"));
			produto1.setValor(resultSet.getFloat("valor"));
			
		}
		
		
		return produto1;
		
	}
	
	public boolean validarNome(String nome) throws Exception {
		String sql = "SELECT COUNT(1) as qtde FROM produto WHERE nome = '"+ nome +"'";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				return resultSet.getInt("qtde") <= 0;
			}
		return false;
	}
	public List<BeanProduto> listar() throws Exception {
		List<BeanProduto> listar = new ArrayList<BeanProduto>();
		String sql = "SELECT * FROM produto";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				BeanProduto produto = new BeanProduto();
				produto.setId(resultSet.getLong("id"));
				produto.setNome(resultSet.getString("nome"));
				produto.setQuantidade(resultSet.getInt("quantidade"));
				produto.setValor(resultSet.getFloat("valor"));
				listar.add(produto);
			}
			return listar;
	}
	

	/*public List<BeanProduto> listar() throws Exception {
		List<BeanProduto> listar = new ArrayList<BeanProduto>();
		String sql = "SELECT * FROM produto";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				BeanProduto produto = new BeanProduto();
				produto.setId(resultSet.getLong("id"));
				produto.setNome(resultSet.getString("nome"));
				produto.setQuantidade(resultSet.getInt("quantidade"));
				produto.setValor(resultSet.getFloat("valor"));
				listar.add(produto);
			}
			return listar;
	}*/
	
	public void deletarUser(String nome1) throws Exception {
		String sql = "DELETE FROM produto WHERE nome = ?;";
		
		PreparedStatement prepareSql = connection.prepareStatement(sql);
		
		prepareSql.setString(1,nome1);
		
		prepareSql.executeUpdate();
		
		connection.commit();
		
	}
	public void atualizar(BeanProduto produto) {
		try {
			String sql = "UPDATE produto SET nome = ?, quantidade = ?, valor = ? WHERE id = "+ produto.getId();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, produto.getNome());
			preparedStatement.setInt(2, produto.getQuantidade());
			preparedStatement.setFloat(3, produto.getValor()); 
			preparedStatement.executeUpdate();
			connection.commit();
		} catch(Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}


