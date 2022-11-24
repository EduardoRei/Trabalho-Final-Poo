package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.BaseDAO;
import model.entities.Tarefa;

public class TarefaDAO extends BaseDAO{
	
	public void inserir(Tarefa tarefa) {
        String sql = "insert into tarefas(descricao, concluido, prioridade) values(? ,?,?)";
        try(Connection c = obterConexao();
            PreparedStatement p = c.prepareStatement(sql)){
            p.setString(1,tarefa.getDescricao());
            p.setBoolean(2,tarefa.isConcluido());
            p.setInt(3, tarefa.getPrioridade());
            p.execute();
            p.close();
        }catch (SQLException e){
            System.out.println("Erro ao inserir tarefa ");
            e.printStackTrace();
        }
    }
	
	public void update(Tarefa tarefa) {
		String sql ="UPDATE tarefas SET descricao = ?, concluido = ?, prioridade = ? WHERE id = ?;";
        try(Connection c = obterConexao();
                PreparedStatement p = c.prepareStatement(sql)){
        	p.setString(1, tarefa.getDescricao());
        	p.setBoolean(2, tarefa.isConcluido());
            p.setInt(3, tarefa.getPrioridade());
            p.setInt(4, tarefa.getId());
            p.execute();
            p.close();
        	
        }catch (SQLException e){
            System.out.println("Erro ao atualizar a tarefa ");
            e.printStackTrace();
        }
	}
	
	public Tarefa findById(int idTarefa) {
		String sql = "SELECT * FROM tarefas WHERE id = ?;";
        try(Connection c = obterConexao();
                PreparedStatement p = c.prepareStatement(sql)){
			p.setInt(1, idTarefa);
			ResultSet rs = p.executeQuery();
			
			Tarefa tarefa = new Tarefa();
			tarefa.setId(rs.getInt("id"));
			tarefa.setDescricao(rs.getString("descricao"));
			tarefa.setConcluido(rs.getBoolean("concluido"));
			tarefa.setPrioridade(rs.getInt("prioridade"));
			rs.close();
			p.close();
			return tarefa;
			
		} catch (SQLException e){
            System.out.println("Erro ao localizar tarefa: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
	}
	
	public void deletar(int idTarefa) {
		String sql = "DELETE FROM tarefas WHERE id = ?;";
        try(Connection c = obterConexao();
                PreparedStatement p = c.prepareStatement(sql)){
                	p.setInt(1, idTarefa);
                	p.execute();
                	p.close();
        }
                catch (SQLException e){
                    System.out.println("Erro ao apagar tarefa: " + e.getMessage());
                    throw new RuntimeException(e.getMessage());
                }
	}
	
	public List<Tarefa> ListarConcluidos() {
		String sql = "SELECT * From tarefas WHERE concluido = 1 ORDER BY prioridade ASC;";
		 try(Connection c = obterConexao();
	                PreparedStatement p = c.prepareStatement(sql)){
			 List<Tarefa> tarefas = new ArrayList<>();
			 ResultSet rs = p.executeQuery();
			 while(rs.next()) {
			 Tarefa tarefa = new Tarefa();
				tarefa.setId(rs.getInt("id"));
				tarefa.setDescricao(rs.getString("descricao"));
				tarefa.setConcluido(rs.getBoolean("concluido"));
				tarefa.setPrioridade(rs.getInt("prioridade"));
				tarefas.add(tarefa);
			 }
			 //tarefas.stream().forEach(System.out::println);
			 rs.close();
			 p.close();
			 return tarefas;
		 }catch (SQLException e){
             System.out.println("Erro ao localizar tarefas: " + e.getMessage());
             throw new RuntimeException(e.getMessage());
         }
	}
	public List<Tarefa> ListarNaoConcluidos() {
		String sql = "SELECT * From tarefas WHERE concluido = 0 ORDER BY prioridade ASC;";
		 try(Connection c = obterConexao();
	                PreparedStatement p = c.prepareStatement(sql)){
			 List<Tarefa> tarefas = new ArrayList<>();
			 ResultSet rs = p.executeQuery();
			 while(rs.next()) {
			 Tarefa tarefa = new Tarefa();
				tarefa.setId(rs.getInt("id"));
				tarefa.setDescricao(rs.getString("descricao"));
				tarefa.setConcluido(rs.getBoolean("concluido"));
				tarefa.setPrioridade(rs.getInt("prioridade"));
				tarefas.add(tarefa);
			 }
			 rs.close();
			 p.close();
			 return tarefas;
			 
		 }catch (SQLException e){
             System.out.println("Erro ao localizar tarefas: " + e.getMessage());
             throw new RuntimeException(e.getMessage());
         }
	}
	
	public List<Tarefa> findAll() {
		String sql = "SELECT * From tarefas ORDER BY id;";
		try(Connection c = obterConexao();
				PreparedStatement p = c.prepareStatement(sql)){
			List<Tarefa> tarefas = new ArrayList<>();
			ResultSet rs = p.executeQuery();
			while(rs.next()) {
				Tarefa tarefa = new Tarefa();
				tarefa.setId(rs.getInt("id"));
				tarefa.setDescricao(rs.getString("descricao"));
				tarefa.setConcluido(rs.getBoolean("concluido"));
				tarefa.setPrioridade(rs.getInt("prioridade"));
				tarefas.add(tarefa);
			}
			rs.close();
			p.close();
			return tarefas;
		}catch (SQLException e){
			System.out.println("Erro ao localizar tarefas: " + e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}
	
}
