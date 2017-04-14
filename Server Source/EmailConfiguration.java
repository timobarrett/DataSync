package fivetwentysix.ware.com.dataSync;

/**
 * Created by tim on 4/11/2017.
 */
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource("classpath:application.properties")
public class EmailConfiguration {
    @Value("${mail.host}")
    private String host;
    @Value("${mail.username}")
    private String username;
    @Value("${mail.password}")
    private String password;
    @Value("${mail.smtp.auth}")
    private String auth;
    @Value("${mail.smtp.port}")
    private int port;
    @Value("${mail.smtp.starttls.enable}")
    private String fallback;
//    @Value("${mail.smtp.ssl.enable}")
//    private boolean ssl;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl msender = new JavaMailSenderImpl();
        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.auth", auth);
        //mailProperties.put("mail.smtp.ssl.enable",ssl);
        //mailProperties.put("spring.mail.properties.mail.smtp.socketFactory.class",socketclass);
        mailProperties.put("mail.smtp.starttls.enable", fallback);
        msender.setJavaMailProperties(mailProperties);
        msender.setHost(host);
        msender.setPort(port);
        msender.setUsername(username);
        msender.setPassword(password);
        return msender;
    }
}
