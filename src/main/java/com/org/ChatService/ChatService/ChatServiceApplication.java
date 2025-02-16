package com.org.ChatService.ChatService;

import com.org.ChatService.ChatService.model.RecentMessages;
import com.org.ChatService.ChatService.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.jms.annotation.EnableJms;

import java.util.List;

@SpringBootApplication
@EnableJms
public class ChatServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatServiceApplication.class, args);

	}


}
