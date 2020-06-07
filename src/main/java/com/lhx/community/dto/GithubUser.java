package com.lhx.community.dto;

import lombok.Data;

import java.io.Serializable;


@Data
public class GithubUser implements Serializable {
    private Long id;
    private String name;
    private String bio;
    private String avatarUrl;
}
