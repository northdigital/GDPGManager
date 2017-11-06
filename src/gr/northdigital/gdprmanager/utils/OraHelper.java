package gr.northdigital.gdprmanager.utils;

import gr.logismos.orasqlworker.command.Command;
import gr.logismos.orasqlworker.param.VarcharInParam;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class OraHelper {
  public static List<String> getOraUsers(Connection connection) throws Exception {
    String sql = "SELECT * FROM DBA_USERS";
    Command command = new Command(connection, sql);
    ArrayList<String> retVal = command.executeSimpleList(String.class);
    command.close();

    return retVal;
  }

  public static List<String> getUserTables(Connection connection, String user) throws Exception {
    String sql = "SELECT TABLE_NAME FROM dba_tables WHERE OWNER = ?";
    Command command = new Command(connection, sql, new VarcharInParam(user));
    ArrayList<String> retVal = command.executeSimpleList(String.class);
    command.close();

    return retVal;
  }
}
