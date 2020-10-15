package com.example.parking.contractservice;

import com.example.parking.contractservice.routing.TenantAwareRoutingSource;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@EnableTransactionManagement

public class ContractserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContractserviceApplication.class, args);
    }
    @Bean
    public DataSource dataSource() {

        AbstractRoutingDataSource dataSource = new TenantAwareRoutingSource();

        Map<Object,Object> targetDataSources = new HashMap<>();

        targetDataSources.put("TenantOne", tenantOne());

        dataSource.setTargetDataSources(targetDataSources);

        dataSource.afterPropertiesSet();

        return dataSource;
    }

    public DataSource tenantOne() {

        HikariDataSource dataSource = new HikariDataSource();

        dataSource.setInitializationFailTimeout(0);
        dataSource.setMaximumPoolSize(5);
        dataSource.setDataSourceClassName("org.postgresql.ds.PGSimpleDataSource");
        dataSource.addDataSourceProperty("url", "jdbc:postgresql://192.168.99.101:31102/Parking");
        dataSource.setSchema("contract_schema");
        dataSource.addDataSourceProperty("user", "test");
        dataSource.addDataSourceProperty("password", "test");
        return dataSource;
    }

}
