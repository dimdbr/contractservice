package com.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hibernate.internal.build.AllowSysOut;
import org.springframework.amqp.core.*;


import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.RabbitResourceHolder;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@Configuration
@EnableRabbit
public class MessagingConfig {

    public static final String createPP = "queue_createPP";
    public static final String updatePP = "queue_updatePP";

    public static final String createCLIENT = "queue_createClient";
    public static final String deleteCLIENT = "queue_deleteClient";

    public static final String createCONTRACT = "queue_createContract";
    public static final String deleteCONTRACT = "queue_deleteContract";
    public static final String updateCONTRACT = "queue_updateContract";

    public static final String EXCHANGECreatePP ="exchange_createPP";
    public static final String EXCHANGEUpdatePP ="exchange_updatePP";
    public static final String EXCHANGECreateClient ="exchange_createClient";
    public static final String EXCHANGEDeleteClient ="exchange_deleteClient";
    public static final String EXCHANGECreateContract ="exchange_createContract";
    public static final String EXCHANGEDeleteContract ="exchange_deleteContract";
    public static final String EXCHANGEUpdateContract="exchange_updateContract";

    public static final String ROUTING_KEY="contract_service_routing_key";

    //_______________BEANS______________
    @Bean
    public Queue queueCreatePP()
    {
        return new Queue(createPP,false);

    }
    @Bean
    public Queue queueCreateClient()
    {
        return new Queue(createCLIENT,false);
    }
    @Bean
    public Queue queueUpdatePP()
    {
        return new Queue(updatePP,false);
    }
    @Bean
    public Queue queueDeleteClient()
    {
        return new Queue(deleteCLIENT,false);
    }

    @Bean
    public Queue queueCreateContract()
    {
        return new Queue(createCONTRACT,false);

    }
    @Bean
    public Queue queueDeleteContract()
    {
        return new Queue(deleteCONTRACT,false);
    }
    @Bean
    public Queue queueAddParkPlace()
    {
        return new Queue(updateCONTRACT,false);
    }


    //_________________________EXCHANGES________________


    @Bean
    public TopicExchange exchangeCreatePP()
    {
        return new TopicExchange(EXCHANGECreatePP);
    }
    @Bean
    public TopicExchange exchangeUpdatePP()
    {
        return new TopicExchange(EXCHANGEUpdatePP);
    }
    @Bean
    public TopicExchange exchangeCreateClient()
    {
        return new TopicExchange(EXCHANGECreateClient);
    }
    @Bean
    public TopicExchange exchangeDeleteClient()
    {
        return new TopicExchange(EXCHANGEDeleteClient);
    }


    @Bean
    public TopicExchange exchangeCreateContract()
    {
        return new TopicExchange(EXCHANGECreateContract);
    }
    @Bean
    public  TopicExchange exchangeDeleteContract()
    {
        return new TopicExchange(EXCHANGEDeleteContract);
    }
    @Bean
    public  TopicExchange exchangeUpdateContract()
    {
        return new TopicExchange(EXCHANGEUpdateContract);
    }
    //__________BINDING________________
    @Bean
    public Binding bindingCreatePP(Queue queueCreatePP,TopicExchange exchangeCreatePP)
    {
        return BindingBuilder.bind(queueCreatePP).to(exchangeCreatePP).with(ROUTING_KEY);

    }

    @Bean
    public Binding bindingUpdatePP(Queue queueUpdatePP,TopicExchange exchangeUpdatePP)
    {
        return BindingBuilder.bind(queueUpdatePP).to(exchangeUpdatePP).with(ROUTING_KEY);

    }
    @Bean
    public Binding bindingCreateClient(Queue queueCreateClient,TopicExchange exchangeCreateClient)
    {
        return BindingBuilder.bind(queueCreateClient).to(exchangeCreateClient).with(ROUTING_KEY);

    }
    @Bean
    public Binding bindingDeleteClient(Queue queueDeleteClient,TopicExchange exchangeDeleteClient)
    {
        return BindingBuilder.bind(queueDeleteClient).to(exchangeDeleteClient).with(ROUTING_KEY);

    }

    @Bean
    public Binding bindingCreateContract(Queue queueCreateContract,TopicExchange exchangeCreateContract)
    {
        return BindingBuilder.bind(queueCreateContract).to(exchangeCreateContract).with(ROUTING_KEY);

    }
    @Bean
    public Binding bindingDeleteContract(Queue queueDeleteContract,TopicExchange exchangeDeleteContract)
    {
        return BindingBuilder.bind(queueDeleteContract).to(exchangeDeleteContract).with(ROUTING_KEY);

    }
    @Bean
    public Binding bindingUpdateContract(Queue queueAddParkPlace,TopicExchange exchangeUpdateContract )
    {
        return BindingBuilder.bind(queueAddParkPlace).to(exchangeUpdateContract).with(ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("rabbitmq",5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate template(ConnectionFactory connectionFactory)
    {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
    }



}
