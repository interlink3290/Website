package com.lhx.community.controller;

import com.lhx.community.cache.TagCache;
import com.lhx.community.dto.QuestionDto;
import com.lhx.community.dto.TagDto;
import com.lhx.community.exception.CustomizeErrorCode;
import com.lhx.community.exception.CustomizeException;
import com.lhx.community.model.Question;
import com.lhx.community.model.User;
import com.lhx.community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class PublishController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish")
    public String publish(Model model) {
        //获取所有标签
        List<TagDto> tagDtos = TagCache.get();
        model.addAttribute("tagDtos", tagDtos);
        return "publish";
    }

    /**
     * 发布问题
     *
     * @param id
     * @param title
     * @param description
     * @param tags
     * @param model
     * @param request
     * @return
     */
    @PostMapping("/publish")
    public String doPublish(@RequestParam(value = "id") Long id,
                            @RequestParam(value = "title") String title,
                            @RequestParam(value = "description") String description,
                            @RequestParam(value = "tags") String tags,
                            Model model, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");

        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tags", tags);

        //获取所有标签
        List<TagDto> tagDtos = TagCache.get();
        model.addAttribute("tagDtos", tagDtos);

        //错误信息
        if (user == null) {
            model.addAttribute("error", "用户未登陆");
            return "publish";
        }
        //判断是否填入信息
        if (StringUtils.isBlank(title)) {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (StringUtils.isBlank(description)) {
            model.addAttribute("error", "描述信息不能为空");
            return "publish";
        }
        if (StringUtils.isBlank(tags)) {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }

        Question question = new Question();
        question.setId(id);
        question.setTitle(title.trim());
        question.setDescription(description);
        question.setTags(tags);
        question.setCreator(user.getId());
        questionService.createOrUpdateQuestion(question, user);

        //发布成功，返回主页面
        return "redirect:/";
    }


    /**
     * 修改问题
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
        //通过问题id查找问题，存入作用域
        QuestionDto question = questionService.findById(id);
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || !question.getCreator().equals(user.getId())) {
            throw new CustomizeException(CustomizeErrorCode.IS_NOT_LEGAL);
        }
        model.addAttribute("id", question.getId());
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tags", question.getTags());
        //获取所有标签
        List<TagDto> tagDtos = TagCache.get();
        model.addAttribute("tagDtos", tagDtos);
        return "publish";
    }
}
