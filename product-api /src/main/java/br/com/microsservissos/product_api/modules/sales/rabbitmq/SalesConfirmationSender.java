package br.com.microsservissos.product_api.modules.sales.rabbitmq;

import br.com.microsservissos.product_api.modules.sales.dto.SalesConfirmationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class SalesConfirmationSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${app-config.rabbit.exchange.product}")
    private String produtTopicExchange;

    @Value("${app-config.rabbit.routingKey.sales-confirmation}")
    private String salesConfirmationKey;

    public void sendSalesConfirmationMesseg(SalesConfirmationDTO messege) {
        try {
            log.info("Sending messege: {}", new ObjectMapper().writeValueAsString(messege));
            rabbitTemplate.convertAndSend(produtTopicExchange, salesConfirmationKey, messege);
            log.info("Messege was send successfully!");
        } catch (Exception ex) {
            log.info("Error while trying to send sales confirmation messege: ", ex);
        }
    }
}
