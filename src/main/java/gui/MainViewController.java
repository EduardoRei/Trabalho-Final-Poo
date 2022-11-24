package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Tarefa;
import model.services.TarefaService;

public class MainViewController implements Initializable, DataChangeListener {

	private TarefaService service = new TarefaService();

	@FXML
	private TableView<Tarefa> tableViewTarefa;

	@FXML
	private TableColumn<Tarefa, Integer> tableColumnId;

	@FXML
	private TableColumn<Tarefa, String> tableColumnDescricao;

	@FXML
	private TableColumn<Tarefa, Boolean> tableColumnConcluido;

	@FXML
	private TableColumn<Tarefa, Integer> tableColumnPrioridade;

	@FXML
	private TableColumn<Tarefa, Tarefa> tableColumnAtualizar;

	@FXML
	private TableColumn<Tarefa, Tarefa> tableColumnApagar;

	@FXML
	private Button btCriar;

	private ObservableList<Tarefa> obsList;

	@FXML
	public void onBtCriarAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Tarefa obj = new Tarefa();
		createDialogForm(obj, "/gui/TarefaForm.fxml", parentStage);
	}

	public void setTarefaService(TarefaService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {

		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
		tableColumnConcluido.setCellValueFactory(new PropertyValueFactory<>("concluido"));
		tableColumnPrioridade.setCellValueFactory(new PropertyValueFactory<>("prioridade"));
		updateTableView();

	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Tarefa> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewTarefa.setItems(obsList);
		initEditButtons();
		initRemoveButtons();
	}

	private void createDialogForm(Tarefa obj, String absoluteName, Stage parentstage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			TarefaFormController controller = loader.getController();
			controller.setTarefa(obj);
			controller.setTarefaService(new TarefaService());
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Controle de Tarefa");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentstage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();

		} catch (IOException e) {
			Alerts.showAlert("IO EXCEPTION", "ERRORR LOADING VIEW", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void onDataChange() {
		updateTableView();
	}

	private void initEditButtons() {
		tableColumnAtualizar.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnAtualizar.setCellFactory(param -> new TableCell<Tarefa, Tarefa>() {
			private final Button button = new Button("Atualizar");

			@Override
			protected void updateItem(Tarefa obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> createDialogForm(obj,"/gui/TarefaForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnApagar.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnApagar.setCellFactory(param -> new TableCell<Tarefa, Tarefa>() {
			private final Button button = new Button("Remover");

			@Override
			protected void updateItem(Tarefa obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}

	private void removeEntity(Tarefa obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confimacao", "Voce esta certo que quer deletar?");
		
		if(result.get() == ButtonType.OK) {
			if(service == null) {
				throw new IllegalStateException("Servico estava nulo");
			}
			try {
				service.remove(obj);
				updateTableView();
			} catch (RuntimeException e) {
				Alerts.showAlert("Erro ao deletar", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}

}
