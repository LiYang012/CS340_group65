package com.cs340.groupProject.repo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class DiagnosticRepository {
    private final JdbcTemplate jdbc;

    public DiagnosticRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Map<String, Object>> runDiagnostics(String onid) {
        jdbc.execute("DROP TABLE IF EXISTS diagnostic");
        jdbc.execute("CREATE TABLE diagnostic(id INT AUTO_INCREMENT PRIMARY KEY, text VARCHAR(255) NOT NULL)");
        jdbc.update("INSERT INTO diagnostic (text) VALUES (?)", "MySQL and Spring Boot is working for " + onid + "!");
        return jdbc.queryForList("SELECT * FROM diagnostic");
    }
}
