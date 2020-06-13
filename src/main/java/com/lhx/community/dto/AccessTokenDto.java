package com.lhx.community.dto;

import lombok.Data;

import java.io.Serializable;


@Data
public class AccessTokenDto implements Serializable {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
