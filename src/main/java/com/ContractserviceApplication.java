package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
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

public class ContractserviceApplication extends Thread implements Runnable {


    public static void main(String[] args) throws IOException, InterruptedException {
        SpringApplication.run(ContractserviceApplication.class, args);
        // sleep(3000);
    }


/*    public static final String QUEUE = "contract_service_queue";
    public static final String EXCHANGE = "contract_service_exchange";
    public static final String ROUTING_KEY = "contract_service_routing_key";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE);

    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }


    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);

    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory());
        return rabbitAdmin;
    }

    @Bean
    public RabbitTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
    }*/
}

//  @Bean
//    public DataSource dataSource() {
//
//        AbstractRoutingDataSource dataSource = new TenantAwareRoutingSource();
//
//        Map<Object,Object> targetDataSources = new HashMap<>();
//
//        targetDataSources.put("TenantOne", tenantOne());
//
//        dataSource.setTargetDataSources(targetDataSources);
//
//        dataSource.afterPropertiesSet();
//
//        return dataSource;
//    }
//
//    public DataSource tenantOne() {
//
//        HikariDataSource dataSource = new HikariDataSource();
//
//        dataSource.setInitializationFailTimeout(0);
//        dataSource.setMaximumPoolSize(5);
//        dataSource.setDataSourceClassName("org.postgresql.ds.PGSimpleDataSource");
//        dataSource.addDataSourceProperty("url", "jdbc:postgresql://localhost:5432/Parking");
//        dataSource.setSchema("contract_schema");
//        dataSource.addDataSourceProperty("user", "test");
//        dataSource.addDataSourceProperty("password", "test");
//        return dataSource;
//    }
//
//*/

/*class GrpcServer {
    public static void main(String[] args) throws InterruptedException, IOException {
        Server server = ServerBuilder
                .forPort(8080)
                .addService(new GRPCClientService()).build();

        server.start();
        server.awaitTermination();
    }
}*/


