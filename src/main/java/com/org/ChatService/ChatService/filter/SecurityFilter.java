package com.org.ChatService.ChatService.filter;

import com.org.ChatService.ChatService.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.LOWEST_PRECEDENCE - 1)
public class SecurityFilter extends OncePerRequestFilter
{
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        System.out.println("Security fielter in action!!!!!!!!!!!!");
        String token = request.getHeader("Authorization");
        System.out.println("Access token is******-----------" + token);

        if(token!=null)
        {
            String username = jwtUtils.getUserName(token);

            if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
            {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                boolean isValid = jwtUtils.validateToken(token , userDetails.getUsername());

                if(isValid)
                {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username , userDetails.getPassword(), userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                }

            }

        }
        filterChain.doFilter(request, response);


    }
}
