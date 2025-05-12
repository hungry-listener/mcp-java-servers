package com.ekagra.mcpstarter.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

@Service
public class DatabaseService {

    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    private String schemaName;

     public DatabaseService(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
    }

    @PostConstruct
    public void init() {
        try (Connection connection = dataSource.getConnection()) {
            this.schemaName = connection.getCatalog();  // Get the current database name
            System.out.println("Connected to schema: " + schemaName);  // Or use logger
        } catch (SQLException e) {
            throw new RuntimeException("Failed to detect database schema", e);
        }
    }

    public List<Map<String, Object>> runQuery(String sql) {
        return jdbcTemplate.queryForList(sql);
    }

    // Method to get full database schema
    public List<String> getAllTableNames() {
        String sql = "SELECT TABLE_NAME " +
                     "FROM INFORMATION_SCHEMA.TABLES " +
                     "WHERE TABLE_SCHEMA = ?";

        return jdbcTemplate.queryForList(sql, String.class, schemaName);
    }

    //Get schema for a single table
    public List<Map<String, Object>> getTableSchema(String tableName) {
        String sql = "SELECT COLUMN_NAME, DATA_TYPE " +
                     "FROM INFORMATION_SCHEMA.COLUMNS " +
                     "WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ? " +
                     "ORDER BY ORDINAL_POSITION";

        return jdbcTemplate.queryForList(sql, schemaName, tableName);
    }
}
