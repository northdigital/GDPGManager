package gr.northdigital.gdprmanager.fxml;

import gr.logismos.orasqlworker.SqlWorker;
import gr.logismos.orasqlworker.utils.JdbcConBuilder;
import gr.northdigital.gdprmanager.utils.OraHelper;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

import java.sql.SQLException;

public class MainController {
  private JdbcConBuilder jdbcConBuilder;
  SqlWorker sqlWorker;

  public MainController() throws SQLException {
    jdbcConBuilder = new JdbcConBuilder("192.168.1.201", "casino", "system", "sporades");
    // jdbcConBuilder = new JdbcConBuilder("192.168.1.202", "casino", "system", "sporades");
    //jdbcConBuilder = new JdbcConBuilder("localhost", "casino","system", "sporades");
    sqlWorker = new SqlWorker(jdbcConBuilder);
  }

  @FXML
  public void initialize() throws Exception {
    sqlWorker.run(connection -> {
      cbUsers.getItems().addAll(OraHelper.getOraUsers(connection));
    });

    cbUsers.getSelectionModel().select(0);
  }

  @FXML
  public ComboBox<String> cbUsers;

  @FXML
  public ListView<String> lstTables;

  @FXML
  public void onAction() throws Exception {
    String selectedUser = cbUsers.getValue();
    lstTables.getItems().clear();

    sqlWorker.run(connection -> {
      lstTables.getItems().addAll(OraHelper.getUserTables(connection, selectedUser));
    });
  }
}
