package com.example.Authentication;

import com.example.Authentication.models.ChatRoom;
import com.example.Authentication.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class AuthenticationApplication implements CommandLineRunner {
	@Autowired
	ChatService service;

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		ChatRoom generalRoom = null;
		String generalRoomName = "General";
		generalRoom = service.findChatRoomByChatName(generalRoomName);

		if(generalRoom == null){
			generalRoom = new ChatRoom();
			generalRoom.setChatName(generalRoomName);
			service.createRoom(generalRoom);
		}

	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedMethods("*")
						.allowedOrigins("http://localhost:4200");
			}
		};
	}

	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		return multipartResolver;
	}
}