package com.cf.community.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Component
@ConfigurationProperties(prefix = "jwt.config")
public class JwtUtil {

    private String secret; //秘钥

    private Long time; //过期时间


    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    //创建JWT
    public String createJWT(String id,String subject,String roles){
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder().setId(id)
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, secret)
                .claim("roles", roles);
        if (time>0) {
            builder.setExpiration(new Date(nowMillis + time * 1000));
        }
        return builder.compact();
    }


    //解析JWT
    public Claims parseJWT(String jwtStr){
        return (Claims) Jwts.parser().setSigningKey(secret).parse(jwtStr).getBody();
    }

    /**
     * 生成token
     * @param userDetails
     * @return
     */
    public String generatorToken(UserDetails userDetails){
        Map<String,Object> claims = new HashMap<>();
        claims.put("sub",userDetails.getUsername());//存入用户名
        claims.put("created",new Date());//生成时间
        //生成角色
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        List<String> roles = new ArrayList<>();
        for (GrantedAuthority authority : authorities) {
            roles.add(authority.getAuthority());
        }
        claims.put("roles",roles); //存入角色

        //生成token
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis()+time * 1000))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public String getUsernameFromRequest(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        return null == token ? null:getUsernameByToken(token);
    }

    /**
     * 从token中获取用户名
     * @param token
     * @return
     */
    public String getUsernameByToken(String token) {
        String username ;
        try{
            Claims claims = getClaimsByToken(token);
            username = claims.getSubject();
        } catch (Exception e){
            return null;
        }
        return username;
    }

    /**
     * 从token中获取角色名
     * @param token
     * @return
     */
    public List<String> getRolesByToken(String token) {
        List<String> roles;
        try{
            Claims claims = getClaimsByToken(token);
            roles = (List<String>) claims.get("roles");
        } catch (Exception e){
            return null;
        }
        return roles;
    }

    private Claims getClaimsByToken(String token) {
        Claims claims;
        try{
            claims = Jwts.parser().setSigningKey(secret)
                    .parseClaimsJws(token).getBody();
        }catch (Exception e){
            claims = null;
        }
        return claims;
    }
}
