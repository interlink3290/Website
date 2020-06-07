package com.lhx.community.dto;

import lombok.Data;


@Data
public class NotificationDto {
    private Long id;
    private Long notifierId;
    private String notifyName;
    private Long receiverId;
    private Long targetId;
    private String targetTitle;
    private Integer type;
    private String typeDescription;
    private Integer status;
    private Long gmtCreate;
}
