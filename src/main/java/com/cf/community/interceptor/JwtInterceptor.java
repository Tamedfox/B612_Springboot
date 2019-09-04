//package com.cf.community.interceptor;
//
//import com.cf.community.model.User;
//import com.cf.community.service.UserService;
//import com.cf.community.util.JwtUtil;
//import io.jsonwebtoken.Claims;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class JwtInterceptor extends HandlerInterceptorAdapter {
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @Autowired
//    private UserService userService;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//        String token = request.getHeader("Authorization");
//
//        if(token != null){
//            UserDetails userDetails = userService.loadUserByToken(token);
//
//            if(null != userDetails){
//                if(SecurityContextHolder.getContext().getAuthentication() == null){
//                    UsernamePasswordAuthenticationToken authentication= new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                }
//            }
//            return true;
//        }else{
//            //token校验失败
//            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//            authorities.add(new SimpleGrantedAuthority("NORMAL"));
//            UsernamePasswordAuthenticationToken authentication =
//                    new UsernamePasswordAuthenticationToken("游客", null, authorities);
//            SecurityContextHolder.getContext().setAuthentication(authentication);//赋予权限
//            return true;
//        }
//
////        if(token != null){
////            Claims claims = null;
////            try{
////                claims = jwtUtil.parseJWT(token);
////            }catch (Exception e){
////
////            }
////            if(claims != null){
////                request.setAttribute("role",claims);
////            }
////        }
//    }
//}
//
