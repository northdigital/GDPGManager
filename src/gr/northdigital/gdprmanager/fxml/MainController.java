package gr.northdigital.gdprmanager.fxml;

import gr.logismos.orasqlworker.SqlWorker;
import gr.logismos.orasqlworker.utils.JdbcConBuilder;
import gr.northdigital.gdprmanager.utils.OraHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainController implements Initializable {
  private JdbcConBuilder jdbcConBuilder;
  private SqlWorker sqlWorker;
  private ObservableList<String> users;
  private ObservableList<String> tables;

  @FXML
  public HBox hBoxMain;

  @FXML
  public VBox vBoxUsersTables;

  @FXML
  public ComboBox<String> cbUsers;

  @FXML
  public ListView<String> lstTables;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    try {
      jdbcConBuilder = new JdbcConBuilder("192.168.1.201", "casino", "system", "sporades");
      //jdbcConBuilder = new JdbcConBuilder("192.168.1.202", "casino", "system", "sporades");
      //jdbcConBuilder = new JdbcConBuilder("localhost", "casino","system", "sporades");
      sqlWorker = new SqlWorker(jdbcConBuilder);

      users = FXCollections.observableArrayList();
      tables = FXCollections.observableArrayList();

      cbUsers.setItems(users);
      lstTables.setItems(tables);

      sqlWorker.run(connection -> {
        users.clear();
        users.addAll(OraHelper.getOraUsers(connection));
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void onShown() throws Exception {
    cbUsers.getSelectionModel().selectFirst();
  }

  @FXML
  public void onAction(ActionEvent actionEvent) throws Exception {
    if (actionEvent.getTarget() == cbUsers) {
      String selectedUser = cbUsers.getValue();
      tables.clear();
      sqlWorker.run(connection -> {
        tables.addAll(OraHelper.getUserTables(connection, selectedUser));
      });
    }
  }
}
