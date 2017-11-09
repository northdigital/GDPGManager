package gr.northdigital.gdprmanager.utils;

import gr.logismos.orasqlworker.command.Command;
import gr.logismos.orasqlworker.param.VarcharInParam;
import gr.northdigital.gdprmanager.model.TableDef;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class OraHelper {
  public static List<String> getOraUsers(Connection connection) throws Exception {
    String sql = "SELECT * FROM DBA_USERS\n" +
                 "WHERE USERNAME NOT IN (\n" +
                 "'SYS', 'SYSTEM', 'OUTLN', 'DBSNMP', 'APPQOSSYS', 'XDB', 'GSMADMIN_INTERNAL', 'WMSYS', 'OJVMSYS', 'CTXSYS',\n" +
                 "'ORDSYS', 'ORDDATA', 'MDSYS', 'LBACSYS', 'OLAPSYS', 'FLOWS_FILES', 'APEX_040200', 'DVSYS', 'HR', 'IX', 'AUDSYS', 'PM',\n" +
                 "'OE', 'SCOTT', 'ORACLE_OCM', 'XS$NULL', 'MDDATA', 'SYSBACKUP', 'SH', 'DIP', 'SYSDG', 'APEX_PUBLIC_USER',\n" +
                 "'SPATIAL_CSW_ADMIN_USR', 'SPATIAL_WFS_ADMIN_USR', 'SI_INFORMTN_SCHEMA', 'DVF', 'SYSKM', 'ANONYMOUS', 'ORDPLUGINS'\n" +
                 ")";
    Command command = new Command(connection, sql);
    ArrayList<String> retVal = command.executeSimpleList(String.class);
    command.close();

    return retVal;
  }

  public static List<TableDef> getOraTables(Connection connection) throws Exception {
    String sql = "SELECT OWNER, TABLE_NAME FROM dba_tables\n" +
                 "WHERE OWNER NOT IN (\n" +
                 "'SYS', 'SYSTEM', 'OUTLN', 'DBSNMP', 'APPQOSSYS', 'XDB', 'GSMADMIN_INTERNAL', 'WMSYS', 'OJVMSYS', " +
                 "'CTXSYS', 'ORDSYS', 'ORDDATA', 'MDSYS', 'LBACSYS', 'OLAPSYS', 'FLOWS_FILES', 'APEX_040200', 'DVSYS', " +
                 "'HR', 'IX', 'AUDSYS', 'PM', 'OE'\n" +
                 ")";
    Command command = new Command(connection, sql);
    ArrayList<TableDef> retVal = command.executeList(TableDef.class);
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
