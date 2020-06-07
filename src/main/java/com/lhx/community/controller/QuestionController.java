package com.lhx.community.controller;

import com.lhx.community.dto.CommentDto;
import com.lhx.community.dto.QuestionDto;
import com.lhx.community.enums.CommentTypeEnum;
import com.lhx.community.enums.TopEnum;
import com.lhx.community.exception.CustomizeException;
import com.lhx.community.model.User;
import com.lhx.community.service.CommentService;
import com.lhx.community.service.QuestionService;
import com.lhx.community.exception.CustomizeErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    /**
     * 根据问题id跳转到详情页面
     *
     * @param id
     * @return
     */
    @GetMapping("/question/{id}")
    public String showQuestion(@PathVariable("id") Long id, Model model) {
        //根据问题id查询详情
        QuestionDto questionDto = questionService.findById(id);
        //根据问题id查询评论详情
        List<CommentDto> commentDtos = commentService.findByQuestionId(id, CommentTypeEnum.TYPE_QUESTION);
        //根据问题tags查询相关问题
        List<QuestionDto> questionByTags = questionService.findByTags(questionDto);

        model.addAttribute("questionDto", questionDto);
        model.addAttribute("commentDtos", commentDtos);
        model.addAttribute("questionByTags", questionByTags);

        //增加浏览数
        questionService.addViewCount(id);
        return "question";
    }

    /**
     * 顶置问题
     *
     * @param oper
     * @param id
     * @return
     */
    @GetMapping("question/{oper}/{id}")
    public String setTop(@PathVariable("oper") Integer oper, @PathVariable("id") Long id) {
        if (!TopEnum.isNotExist(oper)) {
            questionService.setTopQuestion(oper, id);
            return "redirect:/";
        }
        throw new CustomizeException(CustomizeErrorCode.IS_NOT_LEGAL);
    }

    /**
     * 删除问题
     *
     * @param id
     * @return
     */
    @GetMapping("question/delete/{id}")
    public String deleteQuestion(@PathVariable("id") Long id, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            questionService.deleteQuestion(id, user.getId());
        }
        return "redirect:/";
    }
}
