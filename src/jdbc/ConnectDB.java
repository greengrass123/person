package jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import configurations.*;

public class ConnectDB {
    private String dbDriver = Configuration.DBDriver;
    private String url = Configuration.URL;
    private String userName = Configuration.USERName;
    private String password = Configuration.PASSWORD;
    private Connection con = null;
   
    // 建立连接
    public ConnectDB() {
        try {
            Class.forName(dbDriver).newInstance();
            con = DriverManager.getConnection(url, userName, password);
            con.setAutoCommit(true);
        } catch (Exception ex) {
            System.out
                    .println("false in :src/configurations/ConnectDB/ConnectDB()");
        }

    }

    // 执行更新语句，更新了返回true,未更新返回false
    public boolean executeUpdate(String sql) {
        // System.out.println("执行src/jdbc/ConnectDB/executeUpdate()");
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            System.out.println("false in :src/jdbc/ConnectDB/executeUpdate()");
            System.out.println("   错误信息：" + e);
            return false;
        }
    }

    // 执行查询语句，返回查询结果，如果没有查询到任何结果返回null
    public ResultSet executeQuery(String sql) {
        // System.out.println("执行src/jdbc/ConnectDB/executeQuery()");

        ResultSet rs;
        try {

            Statement stmt = con.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("false in :src/jdbc/ConnectDB/executeQuery()");
            System.out.println("   错误信息：" + e);
            return null;
        }
        return rs;
    }

    // 执行查询语句，删除成功则返回true
    public boolean executeBatch(String sql, int[] param) {
        PreparedStatement statement = null;

        boolean flag = false;
        try {
            con.setAutoCommit(false);
            statement = con.prepareStatement(sql);
            for (int i = 0; i < param.length; i++) {
                statement.setInt(1, param[i]);
                System.out.println(param[i]);
                statement.addBatch();
            }
            statement.executeBatch();
            con.commit();
            con.setAutoCommit(true);
            flag = true;
        } catch (SQLException e) {
            try {
                con.rollback(); // 进行事务回滚
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return flag;
    }

    public boolean executeBatch(List<String> listStrings, int[] param)//用于删除多条sql语句，sql语句需要赋值，也是多条
            throws SQLException {
        boolean flag = false;
       PreparedStatement statement = null;

        try {
            con.setAutoCommit(false);
            for (Iterator iterator = listStrings.iterator(); iterator.hasNext();) {
                String string = (String) iterator.next();
                statement = con.prepareStatement(string);
                for (int i = 0; i < param.length; i++) {
                    statement.setInt(1, param[i]);
                    statement.addBatch();
                }
            }
            statement.executeBatch();
            con.commit();
            con.setAutoCommit(true);
            flag = true;
        } catch (SQLException e) {
            con.rollback(); // 进行事务回滚
        } finally {
            statement.close();
        }

        return flag;
    }

    // 根据一个ID数组返回一个ResultSet 类型的list
    public List<ResultSet> executeQueryBatch(String sql, int[] param) {
        List<ResultSet> resultSets = new ArrayList<ResultSet>();
        PreparedStatement statement = null;

        ResultSet rs = null;
        try {
            con.setAutoCommit(false);
            statement = con.prepareStatement(sql);
            for (int i = 0; i < param.length; i++) {
                statement.setInt(1, param[i]);
                System.out.println(param[i]);
                rs = statement.executeQuery();     
                
                resultSets.add(i, rs);
            }
            con.commit();
            con.setAutoCommit(true);          
          
        } catch (SQLException e) {
            System.out.println("false in :src/jdbc/ConnectDB/executeQuery()");
            System.out.println("   错误信息：" + e);
            try {
                con.rollback();
            } catch (SQLException e1) {

                e1.printStackTrace();
            }
            return null;
        }
        return resultSets;
    }

    // 关闭连接
    public void close() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("false in :src/jdbc/ConnectDB/close()");
            } finally {
                con = null;
            }
        }
    }

}
