package io.github.rothschil.oauth2.domain.entity;

import lombok.Data;

/**
 * @author WCNGS@QQ.COM
 * @date 20/12/2 16:19
 * @since 1.0.0
 */
@Data
public class OauthAccessToken {
    private String authenticationId;

    private String tokenId;

    private String userName;

    private String clientId;

    private String refreshToken;

    private byte[] token;

    private byte[] authentication;
}