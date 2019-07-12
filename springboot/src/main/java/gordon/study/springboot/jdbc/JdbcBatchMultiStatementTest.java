package gordon.study.springboot.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

public class JdbcBatchMultiStatementTest {
    public static final String URL = "jdbc:oracle:thin:@10.203.104.131:1531:QA086";
    public static final String USER = "OWIMC";
    public static final String PASSWORD = "Be7s5w8X";

    public static void main(String[] args) throws Exception {
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

        String sql = "insert into T_WHSE_SKU_INV(ID, WHSE_SKU_INV_CODE, PRODUCT_SN) values (?, ?, ?)";
        String sql2 = "insert into T_WHSE_SKU_INV_ATTR(ID, WHSE_SKU_INV_ATTR_CODE, WHSE_SKU_INV_CODE) values (?,?,?)";
        try {
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql);
            PreparedStatement ps2 = conn.prepareStatement(sql2);

            for (int i = 101; i <= 110; i++) {
                ps.setString(1, "id" + i);
                ps.setString(2, "code" + i);
                ps.setString(3, "vin" + i);
                ps.addBatch();
                insertAttr(ps2, i);
            }

            int[] c = ps.executeBatch();
            int[] c2 = ps2.executeBatch();
            System.out.println(Arrays.toString(c));
            System.out.println(Arrays.toString(c2));
            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            conn.close();
        }
    }

    private static void insertAttr(PreparedStatement ps2, int i) throws SQLException {
        for (int j = 0; j < 10; j++) {
            ps2.setString(1, "id" + i + j);
            ps2.setString(2, "id" + i + j);
            ps2.setString(3,"attr" + j);
            ps2.addBatch();
        }
    }
}
