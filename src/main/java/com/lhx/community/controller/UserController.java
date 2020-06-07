package com.lhx.community.controller;

import com.lhx.community.dto.PaginationDto;
import com.lhx.community.dto.QuestionDto;
import com.lhx.community.dto.QuestionQueryDto;
import com.lhx.community.model.User;
import com.lhx.community.service.NotificationService;
import com.lhx.community.service.QuestionService;
import com.lhx.community.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/user/{id}")
    public String getInformationById(@PathVariable Long id, Model model,
                                     @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                     @RequestParam(value = "pageSize", defaultValue = "8", required = false) Integer pageSize) {
        User user = userService.findById(id);
        QuestionQueryDto queryDto = QuestionQueryDto.builder()
                .pageNum(pageNum)
                .pageSize(pageSize)
                .creatorId(user.getId())
                .build();
        //查询当前用户的问题列表
        PaginationDto<QuestionDto> pageInfo = questionService.findByCondition(queryDto);
        model.addAttribute("userInfo", user);
        model.addAttribute("pageInfo", pageInfo);
        return "user";
    }

    @PostMapping("/updateuserinfo")
    @ResponseBody
    public String updateUserinfoById(@RequestParam(value = "id") Long id,
                                     @RequestParam(value = "name") String name,
                                     @RequestParam(value = "bio") String bio,
                                     @RequestParam(value = "avatar_url") String avatar_url,
                                     Model model, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");

        model.addAttribute("name", name);
        model.addAttribute("bio", bio);
        model.addAttribute("avatar_url", avatar_url);
        //未登录
        if (user == null) {
            model.addAttribute("error", "用户未登陆");
            return "user";
        }
        //判断是否填入信息
        if (StringUtils.isBlank(name)) {
            model.addAttribute("error", "用户名不能为空");
            return "user";
        }
        if (StringUtils.isBlank(bio)) {
            model.addAttribute("error", "简介不能为空");
            return "user";
        }
        if (StringUtils.isBlank(name)) {
            model.addAttribute("avatar_url", "头像url不能为空");
            return "user";
        }
        userService.updateUserinfo(id, name, bio, avatar_url);
        model.addAttribute("updateUserInfoInfo", "用户信息修改成功");
        return "user";
    }

    @PostMapping("/user/userUnspeakable")
    @ResponseBody
    public String userUnspeakable(@RequestParam(value = "id") Long id,
                                     Model model, HttpServletRequest request){

        userService.setUserUnspeakable(id);
        model.addAttribute("setUserUnspeakable", "用户禁言成功");
        return "user";
    }

    @PostMapping("/user/userSpeakable")
    @ResponseBody
    public String userSpeakable(@RequestParam(value = "id") Long id,
                                  Model model, HttpServletRequest request){

        userService.setUserSpeakable(id);
        model.addAttribute("setUserSpeakable", "用户禁言成功");
        return "user";
    }

    /**
     * 删除问题
     * @return
     */
    @GetMapping("/user/signIn/{id}")
    public String signIn(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            userService.userSignIn(user.getId());
        }
        return "redirect:/user/{id}";
    }
}
