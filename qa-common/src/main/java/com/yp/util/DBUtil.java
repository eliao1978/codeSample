package com.yp.util;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DBUtil extends LogUtil {

    /**
     *
     * @param sql
     * @return
     */
    public static List<Map<String, Object>> queryForList(String sql) {
        logger.debug("Executed SQL query [" + sql + "]");
        return getJdbcTemplate().queryForList(sql);
    }

    /**
     *
     * @param sql
     * @param objects
     * @param types
     * @return
     */
    public static List<Map<String, Object>> queryForList(String sql, Object[] objects, int[] types) {
        logger.debug("Executed SQL query [" + sql + "]");
        return getJdbcTemplate().queryForList(sql, objects, types);
    }

    /**
     *
     * @param sql
     * @return
     */
    public static int update(String sql) {
        logger.debug("Executed SQL query [" + sql + "]");
        return getJdbcTemplate().update(sql);
    }

    /**
     *
     * @param sql
     * @param objects
     * @param types
     * @return
     */
    public static int update(String sql, Object[] objects, int[] types) {
        logger.debug("Executed SQL query [" + sql + "]");
        return getJdbcTemplate().update(sql, objects, types);
    }

    /**
     *
     * @return
     */
    private static JdbcTemplate getJdbcTemplate() {
        JdbcTemplate template = new JdbcTemplate();

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(System.getProperty("db.url"));
        dataSource.setUsername(System.getProperty("db.username"));
        dataSource.setPassword(System.getProperty("db.password"));
        template.setDataSource(dataSource);

        return template;
    }

    /**
     *
     * @param sql
     * @return
     * @throws SQLException
     * TODO: old method to keep for backward compatible. Shall be deleted later
     */
    public static List<Map<String, String>> runSQL(String sql) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            logger.debug("Established DB connection with [" + System.getProperty("db.url") + "]");
            connection = DriverManager.getConnection(System.getProperty("db.url"), System.getProperty("db.username"), System.getProperty("db.password"));
            logger.debug("Executed SQL query [" + sql + "]");
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            List<Map<String, String>> dataList = new ArrayList<>();
            ResultSetMetaData metaData = resultSet.getMetaData();
            while (resultSet.next()) {
                Map<String, String> map = new HashMap<>();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String k = metaData.getColumnName(i);
                    String v = resultSet.getString(k);
                    map.put(k, v);
                }
                dataList.add(map);
            }
            logger.debug("Returned [" + dataList.size() + "] rows.");
            return dataList;
        } finally {
            if (!(resultSet == null)) {
                resultSet.close();
            }

            if (!(statement == null)) {
                statement.close();
            }

            if (!(connection == null)) {
                connection.close();
            }
        }
    }
}