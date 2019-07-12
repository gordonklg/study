package gordon.study.springboot.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Arrays;

/**
 * batch 也是有事务的
 */
public class JdbcBatchTest {

    public static final String URL = "jdbc:oracle:thin:@10.203.104.131:1531:QA086";
    public static final String USER = "OWIMC";
    public static final String PASSWORD = "Be7s5w8X";

    public static void main(String[] args) throws Exception {
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

        String sql = "insert into T_WHSE_SKU_INV(ID, WHSE_SKU_INV_CODE, PRODUCT_SN) values (?, ?, ?)";
        try {
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql);

            for (int i = 21; i <= 30; i++) {
                ps.setString(1, "id" + i);
                ps.setString(2, "code" + i);
                ps.setString(3, "vin" + i);
                ps.addBatch();
            }

            for (int i = 31; i <= 40; i++) {
                ps.setString(1, "id" + i);
                ps.setString(2, "code" + i);
                ps.setString(3, "vin" + i);
                ps.addBatch();
            }

            ps.setString(1, "id" + 1);
            ps.setString(2, "code" + 1);
            ps.setString(3, "vin" + 1);
            ps.addBatch();

            int[] c = ps.executeBatch();
            System.out.println(Arrays.toString(c));
            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            conn.close();
        }
    }
}