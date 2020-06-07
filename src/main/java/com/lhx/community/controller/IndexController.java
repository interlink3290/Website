package com.lhx.community.controller;

import com.lhx.community.cache.HotTagCache;
import com.lhx.community.dto.PaginationDto;
import com.lhx.community.dto.QuestionDto;
import com.lhx.community.dto.QuestionQueryDto;
import com.lhx.community.service.NotificationService;
import com.lhx.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private HotTagCache hotTagCache;

    @GetMapping("/")
    public String test(Model model,
                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "pageSize", defaultValue = "8") Integer pageSize,
                       @RequestParam(value = "search", required = false) String search,
                       @RequestParam(value = "tag", required = false) String tag,
                       @RequestParam(value = "sort", required = false) String sort) {
        QuestionQueryDto queryDto = QuestionQueryDto.builder()
                .pageNum(pageNum)
                .pageSize(pageSize)
                .search(search)
                .tag(tag)
                .sort(sort)
                .build();
        PaginationDto<QuestionDto> pageInfo = questionService.findByCondition(queryDto);
        List<String> topTags = hotTagCache.getTopTags();
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("search", search);
        model.addAttribute("tag", tag);
        model.addAttribute("sortType", queryDto.getSort());
        model.addAttribute("hotTags", topTags);
        return "index";
    }
}
