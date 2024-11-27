package com.shop.bhpt.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.shop.bhpt.repositories.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
    private final UserRepository userRepository = null;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        
        try {
            // Lấy JWT từ request
            String jwt = getJwtFromRequest(request);

            // Kiểm tra JWT có hợp lệ không
            if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
                // Lấy username từ JWT
                String username = jwtTokenProvider.getUsernameFromToken(jwt);

                // Lấy thông tin user từ database
                var user = userRepository.findByUsername(username)
                        .orElseThrow(() -> new RuntimeException("User not found"));

                // Tạo authentication object
                var authentication = new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
                );

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set authentication vào Security Context
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
} 