package com.tcps.common.core.domain.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 登录响应
 *
 * @author Tao Guang
 */
@Data
public class LoginResponse {

    /**
     * 授权令牌
     */
    @JsonProperty("access_token")
    private String accessToken;

    /**
     * token有效期
     */
    private Long expireIn;

    /**
     * 刷新令牌
     */
    @JsonProperty("refresh_token")
    private String refreshToken;
}
