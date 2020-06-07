package com.lhx.community.dto;

import lombok.Data;

/**
 * 前端传递的评论数据
 */
@Data
public class CommentCreateDto {
    private Long parentId;
    private String content;
    private Integer type;
}
