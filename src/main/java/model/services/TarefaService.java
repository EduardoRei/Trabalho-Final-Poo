package model.services;

import java.util.List;

import dao.TarefaDAO;
import model.entities.Tarefa;

public class TarefaService {
	
	private TarefaDAO dao = new TarefaDAO();

	public List<Tarefa> findAll(){
		return dao.findAll();
	}
	
	public void saveOrUpdate(Tarefa obj) {
		if(obj.getId() == null) {
			dao.inserir(obj);
		}
		else {
			dao.update(obj);
		}
	}
	
	public void remove(Tarefa obj) {
		dao.deletar(obj.getId());
	}
}
