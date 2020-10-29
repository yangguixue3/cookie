package com.example.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JwtToken
 */
@Service
public class JwtToken {

    //密钥
    private static final String SECRET = "secret";

    //jackson
    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * header数据
     * @return
     */
    private static Map<String, Object> createHead() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("typ", "JWT");
        map.put("alg", "HS256");
        return map;
    }

    /**
     * 生成token
     *
     * @param obj    对象数据
     * @param maxAge 有效期
     * @param <T>
     * @return
     */
    public static <T> String createToken(T obj, long maxAge) throws UnsupportedEncodingException, JsonProcessingException {
        JWTCreator.Builder builder = JWT.create();

        builder.withHeader(createHead())//header
                .withSubject(mapper.writeValueAsString(obj));  //payload

        if (maxAge >= 0) {
            long expMillis = System.currentTimeMillis() + maxAge;
            Date exp = new Date(expMillis);
            builder.withExpiresAt(exp);
        }
        // Algorithm.HMAC256 //秘钥及加密算法
        return builder.sign(Algorithm.HMAC256(SECRET));
    }

    /**
     * 解密
     * @param token   token字符串
     * @param classT  解析后的类型
     * @param <T>
     * @return
     */
    @SneakyThrows
    public static <T> T unsign(String token, Class<T> classT) throws UnsupportedEncodingException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        try {
            DecodedJWT jwt = verifier.verify(token);
            Date exp = jwt.getExpiresAt();
            if(exp!=null&&exp.after(new Date())){
                String subject = jwt.getSubject();
                return mapper.readValue(subject, classT);
            }
        }catch (TokenExpiredException e){
//            throw new RuntimeException("4Token过期或不存在。。");
            //Token 过期,返回空 对象
            return classT.newInstance();
        }

        return null;
    }

//    public static void main(String[] args) throws UnsupportedEncodingException, JsonProcessingException {
//        String stingToken = sign("密码",600*20);
//        System.out.println(stingToken);
//        try {
//            String str1 = unsign(stingToken,String.class);
//            System.out.println(str1);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}
