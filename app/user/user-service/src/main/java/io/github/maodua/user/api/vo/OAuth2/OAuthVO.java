package io.github.maodua.user.api.vo.OAuth2;

import io.github.maodua.wrench.common.exception.MessageException;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class OAuthVO {
    /**
     * code 表示要求返回授权码，token 表示直接返回令牌
     */
    private String response_type;
    /**
     * 客户端身份标识
     */
    private String client_id;
    /**
     * 客户端密钥
     */
    private String client_secret;
    /**
     * 重定向地址
     */
    private String redirect_uri;
    /**
     * 表示授权的范围，read只读权限，all读写权限
     */
    private String scope;
    /**
     * 表示授权的方式：<br/>
     *  1. authorization_code（授权码）<br/>
     *  2. password（密码）<br/>
     *  3. client_credentials（凭证式）<br/>
     *  4. refresh_token（更新令牌）<br/>
     */
    private String grant_type;
    /**
     * 应用程序传递的一个随机数，用来防止CSRF攻击。
     */
    private String state;

    /*----------密码模式使用------------*/
    /**
     * 密码模式的账号
     */
    private String username;
    /**
     * 密码模式的密码
     */
    private String password;
    /*----------Refresh-Token，模式使用------------*/
    /**
     * 复制刷新的token
     */
    private String refresh_token;

    public static OAuth2Model get(OAuthVO oAuth){
        String response_type = oAuth.getResponse_type();
        String client_id = oAuth.getClient_id();
        String client_secret = oAuth.getClient_secret();
        String redirect_uri = oAuth.getRedirect_uri();
        String scope = oAuth.getScope();
        String grant_type = oAuth.getGrant_type();
        String state = oAuth.getState();
        String username = oAuth.getUsername();
        String password = oAuth.getPassword();
        String refresh_token = oAuth.getRefresh_token();


        if (StringUtils.equals("password",grant_type)){
            // 密码式（Password）
            if (StringUtils.isBlank(grant_type) || StringUtils.isBlank(client_id) || StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
                throw new MessageException("OAuth2 PASSWORD模式参数不全");
            }
            return OAuth2Model.PASSWORD;
        }else if (StringUtils.equals("refresh_token", grant_type)){
            // 更新令牌
            if (StringUtils.isBlank(grant_type) || StringUtils.isBlank(client_id) || StringUtils.isBlank(client_secret) || StringUtils.isBlank(refresh_token)) {
                throw new MessageException("OAuth2 REFRESH_TOKEN模式参数不全");
            }
            return OAuth2Model.REFRESH_TOKEN;
        }else {
            throw new MessageException("OAuth2 暂时不支持的模式");
        }

    }
}
