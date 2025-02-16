package com.org.ChatService.ChatService.config;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.org.ChatService.ChatService.service.UserPresenceService;
import com.org.ChatService.ChatService.utils.JwtUtils;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@EnableWebSocket
@Controller
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserPresenceService userPresenceService;



    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();

        registry.addEndpoint("/ws");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/chatroom", "/user");
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

                assert accessor != null;
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    System.out.println("NEW CONNECTION--------------------------" );

                    String authorizationHeader = accessor.getFirstNativeHeader("Authorization");
                    System.out.println(authorizationHeader);
                    assert authorizationHeader != null;
                    String token = authorizationHeader;
                    String username = jwtUtils.getUserName(token);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    accessor.setUser(usernamePasswordAuthenticationToken);

                    // Setting the user in Concurrent Hash Map:
                    String uName = accessor.getUser().getName();
                    userPresenceService.addUser(uName);

                } else if (StompCommand.DISCONNECT.equals(accessor.getCommand())) {
                    System.out.println("DISCONNECTION--------------------------" );
                    // Removing the user from Concurrent Hash Map:
                    String uName = accessor.getUser().getName();
                    userPresenceService.removeUser(uName);
                }

                System.out.println("Hash map details------------------START------------");
                System.out.println(userPresenceService.getHashMapDetails());
                System.out.println("Hash map details----------------------------------");

                    return message;
            }

        });
    }


}
