package com.ruddi.logiweb.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.ruddi.logiweb.telegram.LogiwebBot;
import com.ruddi.logiweb.telegram.MessageSender;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
@EnableWebMvc
@ComponentScan("com.ruddi.logiweb")
@Slf4j
public class WebAppConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/pages/**").addResourceLocations("/pages/");
        registry.addResourceHandler("/images/**").addResourceLocations("/images/");
    }

    @Bean
    public InternalResourceViewResolver setupViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/pages/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);

        return resolver;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean("mqChannel")
    public Channel channel() {
        ConnectionFactory factory = new ConnectionFactory();

        String uri = "amqp://sxtswmgm:dh1N5aBEUnam53urt2VMrF9HSi7IDWAf@stingray.rmq.cloudamqp.com/sxtswmgm";

        try {
            factory.setUri(uri);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        Channel channel = null;

        try {
            Connection connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare("logiweb", false, false, false, null);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return channel;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JsonMessageConverter();
    }

    @Bean
    public LogiwebBot logiwebBot() {
        LogiwebBot bot = new LogiwebBot();
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        return bot;
    }

    @Bean
    public MessageSender messageSender() {
        return new MessageSender();
    }
}
