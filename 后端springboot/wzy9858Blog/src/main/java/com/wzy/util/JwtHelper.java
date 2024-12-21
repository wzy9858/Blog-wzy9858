package com.wzy.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtHelper {
    @Value("${jwtHelper.key}")
    private String key;
    //创建jwt令牌解析jwt令牌
    @Value("${jwtHelper.validTime}")
    private Integer time;//单位小时


    //根据id创建 jwt令牌
    public String createJwt(String id) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
//        claims.put("value",value);
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, key)//签名算法
                .setClaims(claims)//自定义内容(载荷)
                .setExpiration(new Date(System.currentTimeMillis() + time * 3600 * 1000))//设置有效期为1*time h
                .compact();
        return jwt;
    }

//    传过来jwt令牌解析
    public Boolean parseJwt(String jwt) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(jwt)
                    .getBody();
//            Integer o = (Integer) claims.get("id"); //可以拿到自定义的载荷
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 根据jwt令牌 解析出来账号
     * @param jwt
     * @return
     */
    public  String praseAccount(String jwt){
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(jwt)
                    .getBody();
            String account = (String) claims.get("id"); //可以拿到自定义的载荷
            return account;
        } catch (Exception e) {
            return null;
        }
    }
}