package gr.northdigital.gdprmanager;

import gr.logismos.orasqlworker.SqlWorker;
import gr.logismos.orasqlworker.utils.JdbcConBuilder;
import gr.northdigital.gdprmanager.fxml.MainController;
import gr.northdigital.gdprmanager.utils.OraHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application {
  private JdbcConBuilder jdbcConBuilder;
  SqlWorker sqlWorker;

  public Main() throws SQLException {
    jdbcConBuilder = new JdbcConBuilder("localhost", "casino", "system", "sporades");
    sqlWorker = new SqlWorker(jdbcConBuilder);
  }

  @Override
  public void start(Stage primaryStage) {

    try {
      Parent root = FXMLLoader.load(getClass().getResource("fxml\\Main.fxml"));

      primaryStage.setTitle("GDPR Manager");
      primaryStage.setScene(new Scene(root, 600, 800));
      primaryStage.show();

      sqlWorker.run(connection -> {
        for(String user : OraHelper.getOraUsers(connection)) {
          MainController.mainController.cbUsers.getItems().add(user);
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws SQLException {
    launch(args);
  }
}
