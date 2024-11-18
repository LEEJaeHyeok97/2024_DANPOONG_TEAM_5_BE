package com.jangburich.domain.oauth.domain;

public interface OAuth2Response {
    String getProvider();

    String getProviderId();

    String getNickname();

    String getImage();
}