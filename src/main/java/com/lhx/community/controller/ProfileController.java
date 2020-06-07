package com.lhx.community.controller;

import com.lhx.community.dto.NotificationDto;
import com.lhx.community.dto.PaginationDto;
import com.lhx.community.dto.QuestionDto;
import com.lhx.community.dto.QuestionQueryDto;
import com.lhx.community.model.User;
import com.lhx.community.service.NotificationService;
import com.lhx.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;


@Controller
public class ProfileController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotificationService notificationService;
    private static final String QUESTIONS = "questions";
    private static final String REPLIES = "replies";

    @GetMapping("/profile/{section}")
    public String profile(@PathVariable("section") String section,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "8", required = false) Integer pageSize) {
        //从session中获取user
        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            return "redirect:/";
        }

        if (QUESTIONS.equals(section)) {
            model.addAttribute("section", section);
            model.addAttribute("sectionName", "我的提问");
            QuestionQueryDto queryDto = QuestionQueryDto.builder()
                    .pageNum(pageNum)
                    .pageSize(pageSize)
                    .creatorId(user.getId())
                    .build();
            //查询当前用户的问题列表
            PaginationDto<QuestionDto> pageInfo = questionService.findByCondition(queryDto);
            model.addAttribute("pageInfo", pageInfo);
        } else if (REPLIES.equals(section)) {
            model.addAttribute("section", section);
            model.addAttribute("sectionName", "最新回复");
            //查询当前用户的消息列表
            PaginationDto<NotificationDto> pageInfo = notificationService.findByReceiverId(pageNum, pageSize, user.getId());
            model.addAttribute("pageInfo", pageInfo);
        }
        return "profile";
    }
}
