package io.github.maodua.order.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.io.Serializable;


/**
 * JWT 访问令牌转换器 ：  对token的一系列操作
 */

public class TokenUtil implements Serializable {



    /**
     * 签发人
     */
    private static String iss ="sch";
    /**
     * 密钥
     */
    private static String secret = "secret";


    /**
     * 获取BODY中的ID
     */
    private static String USER_ID = "USER_ID";

    /**
     * 创建token
     *
     * @param userId 用户id
     * @return token
     */
    public static String create(String userId) {
        Algorithm algorithm = Algorithm.HMAC256(TokenUtil.secret);
        // Token中存储的信息
        return JWT.create()
                .withIssuer(iss)
                .withClaim(USER_ID, userId)
                .sign(algorithm);
    }

    /**
     * 从token中的用户id
     *
     * @param token 令牌
     */
    public static String getUserId(String token) {
        return getJWT(token).getClaim(USER_ID).asString();
    }

    /**
     * 获取token的verifier
     */
    private static DecodedJWT getJWT(String token) {
        Algorithm algorithm = Algorithm.HMAC256(TokenUtil.secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(iss)
                .build();
        return verifier.verify(token);
    }

    /*
    public static void main(String[] args) {
        String token = "eyJ0eXAiOiJKV1QiLCJJRCI6IjExMSIsImFsZyI6IkhTMjU2IiwiS0VZIjoiMjIyIn0.eyJpc3MiOiI0NDQifQ.5_klraPlRjpL-38YY1fmvZX2YRtPt_EyjeZzme4Kgzg";
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("444")
                .build(); //Reusable verifier instance
        DecodedJWT jwt = verifier.verify(token);
        var key = jwt.getClaim(KEY).asString();
        var id = jwt.getClaim(ID).asString();
        System.out.println();
    }
    */


}
