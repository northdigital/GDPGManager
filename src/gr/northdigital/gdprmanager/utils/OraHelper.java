package gr.northdigital.gdprmanager.utils;

import gr.logismos.orasqlworker.command.Command;
import gr.logismos.orasqlworker.param.VarcharInParam;
import gr.northdigital.gdprmanager.model.ColumnDef;
import gr.northdigital.gdprmanager.model.TableDef;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class OraHelper {
  public static List<String> getOraUsers(Connection connection) throws Exception {
    String sql = "SELECT username FROM DBA_USERS " +
                 "WHERE username NOT IN (" +
                 "        'SYS', 'SYSTEM', 'OUTLN', 'DBSNMP', 'APPQOSSYS', 'XDB', 'GSMADMIN_INTERNAL', 'WMSYS', 'OJVMSYS', 'CTXSYS'," +
                 "        'ORDSYS', 'ORDDATA', 'MDSYS', 'LBACSYS', 'OLAPSYS', 'FLOWS_FILES', 'APEX_040200', 'DVSYS', 'HR', 'IX', 'AUDSYS', 'PM'," +
                 "        'OE', 'SCOTT', 'ORACLE_OCM', 'XS$NULL', 'MDDATA', 'SYSBACKUP', 'SH', 'DIP', 'SYSDG', 'APEX_PUBLIC_USER'," +
                 "        'SPATIAL_CSW_ADMIN_USR', 'SPATIAL_WFS_ADMIN_USR', 'SI_INFORMTN_SCHEMA', 'DVF', 'SYSKM', 'ANONYMOUS', 'ORDPLUGINS')";
    Command command = new Command(connection, sql);
    ArrayList<String> retVal = command.executeSimpleList(String.class);
    command.close();

    return retVal;
  }

  public static List<TableDef> getOraTables(Connection connection) throws Exception {
    String sql = "SELECT owner, table_name FROM dba_tables " +
                 "WHERE owner NOT IN (" +
                 "        'SYS', 'SYSTEM', 'OUTLN', 'DBSNMP', 'APPQOSSYS', 'XDB', 'GSMADMIN_INTERNAL', 'WMSYS', 'OJVMSYS', 'CTXSYS', 'ORDSYS'," +
                 "        'ORDDATA', 'MDSYS', 'LBACSYS', 'OLAPSYS', 'FLOWS_FILES', 'APEX_040200', 'DVSYS', 'HR', 'IX', 'AUDSYS', 'PM', 'OE')" ;
    Command command = new Command(connection, sql);
    ArrayList<TableDef> retVal = command.executeList(TableDef.class);
    command.close();

    return retVal;
  }

  public static List<ColumnDef> getOraColumns(Connection connection) throws Exception {
    String sql = "SELECT t.owner, t.table_name, t.column_name, t.data_type FROM all_tab_cols t " +
                 "WHERE t.owner NOT IN (" +
                 "        'SYS', 'SYSTEM', 'OUTLN', 'DBSNMP', 'APPQOSSYS', 'XDB', 'GSMADMIN_INTERNAL', 'WMSYS', 'OJVMSYS', 'CTXSYS'," +
                 "        'ORDSYS', 'ORDDATA', 'MDSYS', 'LBACSYS', 'OLAPSYS', 'FLOWS_FILES', 'APEX_040200', 'DVSYS', 'HR', 'IX', 'AUDSYS', 'PM'," +
                 "        'OE', 'SCOTT', 'ORACLE_OCM', 'XS$NULL', 'MDDATA', 'SYSBACKUP', 'SH', 'DIP', 'SYSDG', 'APEX_PUBLIC_USER'," +
                 "        'SPATIAL_CSW_ADMIN_USR', 'SPATIAL_WFS_ADMIN_USR', 'SI_INFORMTN_SCHEMA', 'DVF', 'SYSKM', 'ANONYMOUS', 'ORDPLUGINS')" +
                 "      AND NOT EXISTS (" +
                 "        SELECT 0 FROM all_views t2 WHERE t2.owner = t.owner AND t2.view_name = t.table_name)";
    Command command = new Command(connection, sql);
    ArrayList<ColumnDef> retVal = command.executeList(ColumnDef.class);
    command.close();

    return retVal;
  }
}
