package org.example.jmsapplication.config;

    import com.fasterxml.jackson.databind.ObjectMapper;
    import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
    import org.apache.activemq.ActiveMQConnectionFactory;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
    import org.springframework.jms.core.JmsTemplate;
    import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
    import org.springframework.jms.support.converter.MessageType;

    /**
     * JMS Configuration class for setting up ActiveMQ connection,
     * message converters, and listener container factories.
     */
    @Configuration
    public class JmsConfig {

        /**
         * Configures the Jackson message converter for converting Java objects
         * to JSON text messages and vice versa. Also supports Java 8 date/time types.
         *
         */
        @Bean
        public MappingJackson2MessageConverter jacksonJmsMessageConverter() {
            MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
            converter.setTargetType(MessageType.TEXT);  // Send JSON as TextMessage
            converter.setTypeIdPropertyName("_type");   // Property to identify the class type on deserialization

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());  // Support for LocalDateTime, etc.
            converter.setObjectMapper(objectMapper);

            return converter;
        }

        /**
         * Creates an in-memory ActiveMQ connection factory.
         * Suitable for testing or development environments.
         *
         * @return the configured {@link ActiveMQConnectionFactory}
         */
        @Bean
        public ActiveMQConnectionFactory connectionFactory() {
            return new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false");
        }

        /**
         * Creates a JmsTemplate with the configured connection factory
         * and message converter to simplify sending JMS messages.
         *
         * @param connectionFactory the ActiveMQ connection factory
         * @return the configured {@link JmsTemplate}
         */
        @Bean
        public JmsTemplate jmsTemplate(ActiveMQConnectionFactory connectionFactory) {
            JmsTemplate template = new JmsTemplate(connectionFactory);
            template.setMessageConverter(jacksonJmsMessageConverter());
            return template;
        }

        /**
         * Configures a JMS listener container factory for consuming messages
         * with support for concurrent listeners, transactions, and error handling.
         *
         * @param connectionFactory the ActiveMQ connection factory
         * @return the configured {@link DefaultJmsListenerContainerFactory}
         */
        @Bean(name = "jmsListenerContainerFactory")
        public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ActiveMQConnectionFactory connectionFactory) {
            DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
            factory.setConnectionFactory(connectionFactory);
            factory.setConcurrency("2-4");  // Allow 2 to 4 concurrent consumers
            factory.setErrorHandler(t -> System.err.println("Listener error: " + t.getMessage()));
            factory.setMessageConverter(jacksonJmsMessageConverter());
            factory.setSessionTransacted(true);
            return factory;
        }
    }
