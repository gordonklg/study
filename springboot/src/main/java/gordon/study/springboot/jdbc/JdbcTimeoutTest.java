package gordon.study.springboot.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;

public class JdbcTimeoutTest {

    public static final String URL = "jdbc:oracle:thin:@10.203.104.131:1531:QA086";

    public static void main(String[] args) throws Exception {

        Properties props = new Properties();
        props.put("user", "OWIMC");
        props.put("password", "Be7s5w8X");
        props.put("oracle.net.CONNECT_TIMEOUT", "10000");
        props.put("oracle.jdbc.ReadTimeout", "2000");
        Connection conn = DriverManager.getConnection(URL, props);

        String sql = "insert into T_WHSE_SKU_INV(ID, WHSE_SKU_INV_CODE, PRODUCT_SN) values (?, ?, ?)";
        try {
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setQueryTimeout(1000);

            ps.setString(1, "id" + 30);
            ps.setString(2, "code" + 30);
            ps.setString(3, "vin" + 30);

            int c = ps.executeUpdate();
            System.out.println(c);
            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            conn.close();
        }
    }
}