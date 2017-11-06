package gr.northdigital.gdprmanager;

import gr.logismos.orasqlworker.SqlWorker;
import gr.logismos.orasqlworker.utils.JdbcConBuilder;
import gr.northdigital.gdprmanager.fxml.MainController;
import gr.northdigital.gdprmanager.utils.OraHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
  private JdbcConBuilder jdbcConBuilder;
  SqlWorker sqlWorker;

  public Main() throws SQLException {
    //jdbcConBuilder = new JdbcConBuilder("localhost", "casino", "system", "sporades");
    jdbcConBuilder = new JdbcConBuilder("192.168.1.201", "casino", "system", "sporades");
    sqlWorker = new SqlWorker(jdbcConBuilder);
  }

  @Override
  public void start(Stage primaryStage) {

    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Main.fxml"));
      fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
      Parent root = fxmlLoader.load();
      //MainController mainController = fxmlLoader.getController();

      primaryStage.setTitle("GDPR Manager");
      primaryStage.setScene(new Scene(root, 600, 800));
      primaryStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws SQLException {
    launch(args);
  }
}
