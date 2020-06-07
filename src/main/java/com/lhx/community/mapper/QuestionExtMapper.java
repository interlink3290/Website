package com.lhx.community.mapper;

import com.lhx.community.dto.QuestionQueryDto;
import com.lhx.community.model.Question;

import java.util.List;

public interface QuestionExtMapper {
    void addViewCount(Question question);

    void addCommentCount(Question question);

    List<Question> findByTagsREGEXP(Question question);

    int countByCondition(QuestionQueryDto questionQuery);

    List<Question> findByCondition(QuestionQueryDto questionQuery);
}