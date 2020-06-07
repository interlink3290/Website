package com.lhx.community.dto;

import com.lhx.community.model.User;
import lombok.Data;

/**
 * 数据库的评论数据
 */
@Data
public class CommentDto {
    private Long id;
    private Long parentId;
    private Integer type;
    private String content;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private Long commentCount;
    private User user;
}
