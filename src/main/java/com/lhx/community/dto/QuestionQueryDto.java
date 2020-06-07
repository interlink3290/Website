package com.lhx.community.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionQueryDto {
    private Integer offerIndex;
    private Integer pageNum;
    private Integer pageSize;
    private Long creatorId;
    private String search;
    private String tag;
    private String sort;
    private Long time;
}
