package br.com.microsservissos.product_api.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${app-config.rabbit.exchange.product}")
    private String produtTopicExchange;

    @Value("${app-config.rabbit.routingKey.product-stock}")
    private String produtStockKey;

    @Value("${app-config.rabbit.routingKey.sales-confirmation}")
    private String salesConfirmationKey;

    @Value("${app-config.rabbit.queue.product-stock}")
    private String produtStockMq;

    @Value("${app-config.rabbit.queue.sales-confirmation}")
    private String salesConfirmationMq;

    @Bean
    public Queue productStockQueue() {
        return new Queue("product-stock-update.queue", true);
    }

    @Bean
    public TopicExchange produtTopicExchange() {
        return new TopicExchange(produtTopicExchange);
    }

    @Bean
    public Queue productStockMq() {
        return new Queue(produtStockKey, true);
    }

    @Bean
    public Queue salesConfirmationMq() {
        return new Queue(salesConfirmationMq, true);
    }

    @Bean
    public Binding productStockMqBinding(TopicExchange topicExchange) {
        return BindingBuilder
                .bind(productStockMq())
                .to(topicExchange)
                .with(produtStockKey);
    }

    @Bean
    public Binding salesConfirmationMqBinding(TopicExchange topicExchange) {
        return BindingBuilder
                .bind(salesConfirmationMq())
                .to(topicExchange)
                .with(salesConfirmationKey);
    }

//    @Bean
//    public MessageConverter jsonMessageConverter() {
//        return new Jackson2JsonMessageConverter();
//    }
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}