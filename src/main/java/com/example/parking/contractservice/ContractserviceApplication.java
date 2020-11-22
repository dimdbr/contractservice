package com.example.parking.contractservice;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import java.io.IOException;


@Configuration
@SpringBootApplication
@EnableAsync
@EnableCaching
@EnableScheduling
@EnableTransactionManagement
@EnableJpaRepositories
public class ContractserviceApplication extends Thread implements Runnable{


    public static void main(String[] args) throws IOException, InterruptedException {
        SpringApplication.run(ContractserviceApplication.class, args);
       // sleep(3000);
    }




 /*
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
        dataSource.addDataSourceProperty("url", "jdbc:postgresql://localhost:5432/Parking");
        dataSource.setSchema("contract_schema");
        dataSource.addDataSourceProperty("user", "test");
        dataSource.addDataSourceProperty("password", "test");
        return dataSource;
    }

*/
}
/*class GrpcServer {
    public static void main(String[] args) throws InterruptedException, IOException {
        Server server = ServerBuilder
                .forPort(8080)
                .addService(new GRPCClientService()).build();

        server.start();
        server.awaitTermination();
    }
}*/


