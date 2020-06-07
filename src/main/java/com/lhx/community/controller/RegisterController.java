package com.lhx.community.controller;

import com.lhx.community.dto.ResultDto;
import com.lhx.community.enums.SendEmailEnum;
import com.lhx.community.exception.CustomizeErrorCode;
import com.lhx.community.service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@Slf4j
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @GetMapping("/register")
    public String toRegister() {
        return "register";
    }

    @GetMapping("/sendEmail")
    @ResponseBody
    public ResultDto<?> sendEmail(@RequestParam("email") String email,
                                  @RequestParam("type") int type) {
        if (!registerService.checkEmail(email)) {
            return ResultDto.errorOf(CustomizeErrorCode.SEND_EMAIL_FAIL);
        }
        if (registerService.registered(email)) {
            if (type == SendEmailEnum.REGISTER.getCode()) {
                return ResultDto.errorOf(CustomizeErrorCode.EMAIL_ALREADY_EXISTS);
            }
        } else {
            if (type == SendEmailEnum.UPDATE_PWD.getCode()) {
                return ResultDto.errorOf(CustomizeErrorCode.EMAIL_NOT_EXISTS);
            }
        }
        registerService.sendEmail(email, type);
        return ResultDto.okOf();
    }

    @PostMapping("/register")
    public String register(@RequestParam("email") String email,
                           @RequestParam("password") String password,
                           @RequestParam("code") Integer code,
                           Model model) {
        if (!registerService.checkInfo(email, password, code) || !registerService.checkCode(email, code)) {
            model.addAttribute("registerInfo", "请输入正确信息");
            return "register";
        }
        if (registerService.register(email, password)) {
            model.addAttribute("registerInfo", "注册成功，快去登录吧~");
        } else {
            model.addAttribute("registerInfo", "该邮箱已注册，换一个试试");
        }
        return "register";
    }
}
