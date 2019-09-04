package com.cf.community.filter;

import com.cf.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if(token != null){
            UserDetails userDetails = userService.loadUserByToken(token);

            if(null != userDetails){
                if(SecurityContextHolder.getContext().getAuthentication() == null){
                    UsernamePasswordAuthenticationToken authentication= new UsernamePasswordAuthenticationToken(userDetails.getUsername(), "******", userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }else{
            //token校验失败,匿名登录
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ANNOY"));
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken("游客", null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);//赋予权限
        }
        filterChain.doFilter(request,response);
    }
}
