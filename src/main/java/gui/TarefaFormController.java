package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.entities.Tarefa;
import model.exception.ValidationException;
import model.services.TarefaService;

public class TarefaFormController implements Initializable {
	
	private Tarefa entity;
	
	private TarefaService service = new TarefaService();
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField txtId;
	
	@FXML
	private TextArea txtDescricao;
	
	@FXML
	private ComboBox<String> comboBoxPrioridade;
	
	@FXML
	private CheckBox checkBoxConcluido;
	
	@FXML
	private Label labelErrorDescricao;
	
	@FXML
	private Button btnCancelar;
	
	@FXML
	private Button btnSalvar;
	
	@FXML
	public void onBtSalvarAction(ActionEvent event) {
		if(entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		if (service == null) {
			throw new IllegalStateException("Service was null"); 
		}
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		}catch (ValidationException e) {
				setErrorMessages(e.getErrors());
			}			
		catch (Exception e) {
			Alerts.showAlert("Error Saving Object", null, e.getMessage(), AlertType.ERROR);
		} 
	}
	
	private void notifyDataChangeListeners() {
		for(DataChangeListener listener : dataChangeListeners) {
			listener.onDataChange();
		}
	}

	private Tarefa getFormData() {

		Tarefa obj = new Tarefa();
		ValidationException exception = new ValidationException("Validation error");
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));

		if(txtDescricao.getText() == null || txtDescricao.getText().trim().equals("")) {
			exception.addError("descricao", "O campo não pode ficar vazio");
		}
		obj.setDescricao(txtDescricao.getText());
		
		int aux;
		if(comboBoxPrioridade.getValue() == "Muito Importante") {
			aux = 1;
		} else if (comboBoxPrioridade.getValue() == "Importante") {
			aux = 2;
		} else {
			aux = 3;
		}
		obj.setPrioridade(aux);
		obj.setConcluido(checkBoxConcluido.isSelected());
		
		if(exception.getErrors().size() > 0) {
			throw exception;
		}
		return obj;
	}
	
	public void updateFormData() {
		if(entity == null) {
			throw new IllegalStateException("entity was null");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtDescricao.setText(entity.getDescricao());
		String aux;
		if(entity.getPrioridade() == null) {
			aux = "Muito Importante";
		}else {
			if(entity.getPrioridade() == 1) {
				aux = "Muito Importante";
			} else if (entity.getPrioridade() == 2) {
				aux = "Importante";
			} else {
				aux = "Normal";
			}
		}
		comboBoxPrioridade.setValue(aux);
		checkBoxConcluido.setSelected(entity.isConcluido());
	}

	@FXML
	public void onBtCancelarAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	public void setTarefa(Tarefa entity) {
		this.entity = entity;
	}
	
	public void setTarefaService(TarefaService service) {
		this.service = service;
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextAreaMaxLength(txtDescricao, 500);
		comboBoxPrioridade.getItems().addAll("Muito Importante", "Importante", "Normal");
		comboBoxPrioridade.getSelectionModel().selectFirst();
	}
	
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		
		if (fields.contains("descricao")) {
			labelErrorDescricao.setText(errors.get("descricao"));
		}
	}

}
