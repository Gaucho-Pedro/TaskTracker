package org.example.TaskTracker.configuration;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
public class MainConfiguration {
    @Bean
    public DataSource dataSource(){
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerNames(new String[]{"localhost"});
        dataSource.setPortNumbers(new int[]{5433});
        dataSource.setDatabaseName("postgres");
        dataSource.setUser("user");
        dataSource.setPassword("pass");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) throws IOException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        DatabaseAutoConfiguration(jdbcTemplate);
        return jdbcTemplate;
    }

    public void DatabaseAutoConfiguration(JdbcTemplate jdbcTemplate) throws IOException {
        ResourceLoader resourceLoader = new DefaultResourceLoader();

        jdbcTemplate.execute("DROP TABLE IF EXISTS tasks CASCADE");
        jdbcTemplate.execute("DROP TABLE IF EXISTS users CASCADE");
        jdbcTemplate.execute("DROP TABLE IF EXISTS projects CASCADE");
        jdbcTemplate.execute(
                Files.readString(
                        Paths.get(resourceLoader.getResource("/postgres_scripts/schema/project.sql").getURI())));
        jdbcTemplate.execute(
                Files.readString(
                        Paths.get(resourceLoader.getResource("/postgres_scripts/schema/user.sql").getURI())));
        jdbcTemplate.execute(
                Files.readString(
                        Paths.get(resourceLoader.getResource("/postgres_scripts/schema/task.sql").getURI())));
        jdbcTemplate.execute(
                Files.readString(
                        Paths.get(resourceLoader.getResource("/postgres_scripts/data/project.sql").getURI())));
        jdbcTemplate.execute(
                Files.readString(
                        Paths.get(resourceLoader.getResource("/postgres_scripts/data/user.sql").getURI())));
        jdbcTemplate.execute(
                Files.readString(
                        Paths.get(resourceLoader.getResource("/postgres_scripts/data/task.sql").getURI())));
    }
}
