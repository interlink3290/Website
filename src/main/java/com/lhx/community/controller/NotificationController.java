package com.lhx.community.controller;

import com.lhx.community.enums.NotificationTypeEnum;
import com.lhx.community.exception.CustomizeErrorCode;
import com.lhx.community.exception.CustomizeException;
import com.lhx.community.model.Notification;
import com.lhx.community.model.User;
import com.lhx.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;


@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String readNotify(@PathVariable("id") Long id, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new CustomizeException(CustomizeErrorCode.USER_NOT_LOGIN);
        }
        if (id == 0) {
            this.notificationService.readAll(user.getId());
            return "redirect:/profile/replies";
        }
        Notification notification = this.notificationService.read(id, user);
        if (NotificationTypeEnum.REPLY_COMMENT.getType().equals(notification.getType()) ||
                NotificationTypeEnum.REPLY_QUESTION.getType().equals(notification.getType())) {
            return "redirect:/question/" + notification.getTargetId();
        } else {
            throw new CustomizeException(CustomizeErrorCode.IS_NOT_LEGAL);
        }
    }
}
