package gordon.study.springboot.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class JdbcTest {

    public static final String URL = "jdbc:oracle:thin:@10.203.104.131:1531:QA086";
    public static final String USER = "OWIMC";
    public static final String PASSWORD = "Be7s5w8X";

    public static void main(String[] args) throws Exception {
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

        String sql = "insert into T_WHSE_SKU_INV(ID, WHSE_SKU_INV_CODE, PRODUCT_SN) values (?, ?, ?)";
        try {
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, "id" + 9999);
            ps.setString(2, "code" + 9999);
            ps.setString(3, "vin" + 9999);

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