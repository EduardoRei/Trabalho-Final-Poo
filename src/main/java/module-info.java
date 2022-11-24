module app {
	requires javafx.base;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.controls;
	requires java.sql;
	
	opens gui to javafx.fxml;
	opens model.entities to javafx.base;
	exports application;
}