package io.github.rothschil.common.utils;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class JdbcDocUtils {
    public static String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    public static String JDBC_URL = "jdbc:mysql://localhost:13306/au_app";
    public static String USER_NAME = "root";
    public static String PASSWORD = "123456";

    public static DataSource getDataSourceConfig() {
        //数据源
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(DRIVER_NAME);
        hikariConfig.setJdbcUrl(JDBC_URL);
        hikariConfig.setUsername(USER_NAME);
        hikariConfig.setPassword(PASSWORD);
        //设置可以获取tables remarks信息
        hikariConfig.addDataSourceProperty("useInformationSchema", "true");
        hikariConfig.setMinimumIdle(2);
        hikariConfig.setMaximumPoolSize(5);
        return new HikariDataSource(hikariConfig);
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(DRIVER_NAME).newInstance();
            conn = java.sql.DriverManager.getConnection(JDBC_URL, USER_NAME, PASSWORD);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static List<String> getTablesNames() {
        List<String> tables = new ArrayList<>();
        Connection connection = getConnection();
        ResultSet rs = null;
        Statement st = null;
        try {
            st = connection.createStatement();
            rs = st.executeQuery("show tables");
            while (rs.next()) {
                String string = rs.getString(1);
                tables.add(string);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                st.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return tables;
    }
}
