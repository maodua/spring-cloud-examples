package io.github.maodua.user.api.vo.OAuth2;

/**
 * OAuth2 模式
 */
public enum OAuth2Model {
    /**
     * 授权码
     */
    AUTHORIZATION_CODE,
    /**
     * 密码
     */
    PASSWORD,
    /**
     * 凭证式
     */
    CLIENT_CREDENTIALS,
    /**
     * 更新令牌
     */
    REFRESH_TOKEN;
}
