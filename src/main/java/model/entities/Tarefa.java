package model.entities;

public class Tarefa {
	private Integer id;
	private String descricao;
	private boolean concluido ;
	private Integer prioridade;
	
	public Tarefa() {}
	
	public Tarefa(Integer id, String descricao, boolean concluido, Integer prioridade) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.concluido = concluido;
		this.prioridade = prioridade;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isConcluido() {
		return concluido;
	}

	public void setConcluido(boolean estado) {
		this.concluido = estado;
	}



	public Integer getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(Integer prioridade) {
		this.prioridade = prioridade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tarefa other = (Tarefa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	protected String prioridades() {
		if (prioridade == 1) {
			return "Muito Importante";
		} else if(prioridade == 2) {
			return "Importante";
		} else {
			return "Normal";
		}
	}
	@Override
	public String toString() {
		return "Id:" + id + ", descricao: " + descricao + ", concluida: " + concluido + ", prioridade: " + prioridades();
	}
	
	
}
