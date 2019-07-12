package gordon.study.springboot.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class JdbcBatchTest2 {

    public static final String URL = "jdbc:oracle:thin:@10.203.104.131:1531:QA086";
    public static final String USER = "OWIMC";
    public static final String PASSWORD = "Be7s5w8X";

    public static void main(String[] args) throws Exception {
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

        String sql = "insert into T_WHSE_SKU_INV(ID, WHSE_SKU_INV_CODE, PRODUCT_SN) select ?,?,? from dual where not exists (select * from T_WHSE_SKU_INV where PRODUCT_SN=?)";

        try {
//            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql);
            List<String> wrongVins = new ArrayList<>();

            for (int i = 11; i <= 20; i++) {
                ps.setString(1, "id" + i);
                ps.setString(2, "code" + i);
                ps.setString(3, "vin" + i);
                ps.setString(4, "vin" + i);
                ps.addBatch();
            }
            int[] c = ps.executeBatch();
            ps.executeBatch();
            ps.clearBatch();
            for (int i = 0; i < c.length; i++) {
                if (c[i] == 0) {
                    wrongVins.add("number" + (1 + i));
                }
            }

            Thread.sleep(10000);

            for (int i = 21; i <= 30; i++) {
                ps.setString(1, "id" + i);
                ps.setString(2, "code" + i);
                ps.setString(3, "vin" + i);
                ps.setString(4, "vin" + i);
                ps.addBatch();
            }
            c = ps.executeBatch();
            ps.clearBatch();
            for (int i = 0; i < c.length; i++) {
                if (c[i] == 0) {
                    wrongVins.add("number" + (11 + i));
                }
            }

            ps.setString(1, "id" + 21);
            ps.setString(2, "code" + 21);
            ps.setString(3, "vin" + 21);
            ps.setString(4, "vin" + 21);
            ps.addBatch();
            c = ps.executeBatch();
            for (int i = 0; i < c.length; i++) {
                if (c[i] == 0) {
                    wrongVins.add("number" + (21 + i));
                }
            }

            if (wrongVins.size() > 0) {
                System.out.println("wrongVins:" + wrongVins);
                conn.rollback();
            } else {
                conn.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            conn.close();
        }
    }

}