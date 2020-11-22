package sqltest;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLtest {

    // ドライバが読み込まれているかの確認
    // public static void main(String[] args) throws InstantiationException,
    // IllegalAccessException {
    // String msg = "";
    // try {
    // Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
    // msg = "ドライバのロードに成功しました";
    // } catch (ClassNotFoundException e){
    // msg = "ドライバのロードに失敗しました";
    // }
    // System.out.println(msg);
    // }

    public static void main(String[] args) {
    	PreparedStatement st = null;
        Connection conn = null;
        ResultSet rs = null;

        try {

            String dbURL = "jdbc:sqlserver://localhost:1433; databaseName = Test";
            String user = "testuser";
            String pass = "PS12345";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(dbURL, user, pass);

            if (conn != null) {
                DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                System.out.println("Product version: " + dm.getDatabaseProductVersion());
            }

            String sql = "SELECT * FROM testTable";
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();

            while(rs.next()) {
            	System.out.println(rs.getString("test_name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}