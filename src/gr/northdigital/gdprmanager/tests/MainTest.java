package gr.northdigital.gdprmanager.tests;

import gr.logismos.orasqlworker.SqlWorker;
import gr.logismos.orasqlworker.utils.JdbcConBuilder;
import gr.northdigital.gdprmanager.utils.OraHelper;
import org.junit.Before;

import java.util.List;

public class MainTest {
  private JdbcConBuilder jdbcConBuilder;
  SqlWorker sqlWorker;

  @Before
  public void setUp() throws Exception {
    jdbcConBuilder = new JdbcConBuilder("192.168.1.201", "casino", "system", "sporades");
    // jdbcConBuilder = new JdbcConBuilder("192.168.1.202", "casino", "system", "sporades");
    //jdbcConBuilder = new JdbcConBuilder("localhost", "casino","system", "sporades");
    sqlWorker = new SqlWorker(jdbcConBuilder);
  }

  @org.junit.Test
  public void getUsers() throws Exception {
    try {
      System.out.println("test1");

      sqlWorker.run(connection -> {
        List<String> users = OraHelper.getOraUsers(connection);

        for (String string : users) {
          System.out.println(string);
        }
      });
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}