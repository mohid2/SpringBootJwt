package com.example.springconjwt.config.security.Jwt;

import com.example.springconjwt.service.IJwtService;
import com.example.springconjwt.service.impl.JwtServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private  final UserDetailsService userDetailsService;
    private  final IJwtService jwtService;
    private static final String TOKEN_PREFIX= "Bearer";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String token = getTokenFromRequest(request);
        final String userEmail;
        if(token==null){
            filterChain.doFilter(request,response);
            return;
        }
        userEmail = jwtService.getUserEmailForToken(token);
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
            if(jwtService.isTokenValid(token,userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        }
        filterChain.doFilter(request,response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader =request.getHeader(HttpHeaders.AUTHORIZATION);
        if(StringUtils.hasText(authHeader) && authHeader.startsWith(TOKEN_PREFIX)){
            return authHeader.substring(7);
        }
        return null;
    }
//    otra forma para implementar el doFilter

//    @Override
//    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,
//                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
//        final String authHeader =request.getHeader("Authorization");
//
//        final String jwt;
//        final String userEmail;
//
//        if ( authHeader==null || !authHeader.startsWith("Bearer")){
//            filterChain.doFilter(request,response);
//            return;
//        }
//        jwt = authHeader.substring(7);
//        userEmail = jwtServiceImpl.getUserName(jwt);
//
//        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() ==   null){
//            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
//            if(jwtServiceImpl.validateToken(jwt,userDetails)){
//                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//                        userDetails,
//                        null,
//                        userDetails.getAuthorities()
//                );
//                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//            }
//        }
//
//        filterChain.doFilter(request,response);
//
//    }


}
