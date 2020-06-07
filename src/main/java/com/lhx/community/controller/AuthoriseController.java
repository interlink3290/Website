package com.lhx.community.controller;

import com.lhx.community.config.GithubParams;
import com.lhx.community.dto.AccessTokenDto;
import com.lhx.community.dto.GithubUser;
import com.lhx.community.exception.CustomizeErrorCode;
import com.lhx.community.exception.CustomizeException;
import com.lhx.community.mapper.UserMapper;
import com.lhx.community.model.User;
import com.lhx.community.provider.GithubProvider;
import com.lhx.community.service.UserService;
import com.lhx.community.utils.CookieUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


@Slf4j
@Controller
public class AuthoriseController {
    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private GithubParams githubParams;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;


    private AccessTokenDto accessTokenDto = new AccessTokenDto();

    private static final int COOKIE_EXPIRY = 60 * 60 * 24 * 7;

    @GetMapping("/githubCallback")
    public String githubCallback(@RequestParam("code") String code,
                                 @RequestParam("state") String state,
                                 HttpServletResponse response) {
        setAccessTokenDto(code, state, githubParams.getClient_id(), githubParams.getClient_secret(), githubParams.getRedirect_uri());
        //获取access_token
        String accessToken = githubProvider.getAccessToken(accessTokenDto);
        //根据accessToken获取用户信息
        GithubUser githubUser = githubProvider.getGithubUser(accessToken);

        if (githubUser != null && githubUser.getId() != null) {
            String token = UUID.randomUUID().toString();
            //设置user信息
            setUserInfo(token, githubUser.getName(), githubUser.getAvatarUrl(), "Github-" + githubUser.getId(), githubUser.getBio());
            CookieUtils.setCookie(response, "token", token, COOKIE_EXPIRY);
            return "redirect:/";
        } else {
            log.error("githubUser获取失败");
            throw new CustomizeException(CustomizeErrorCode.LOGIN_CONNECT_ERROR);
        }
    }


    private void setAccessTokenDto(String code, String state, String clientId, String clientSecret, String redirectUri) {
        accessTokenDto.setClient_id(clientId);
        accessTokenDto.setClient_secret(clientSecret);
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_uri(redirectUri);
        accessTokenDto.setState(state);
    }

    private void setUserInfo(String token, String name, String avatarUrl, String accountId, String bio) {
        User user = new User();
        user.setToken(token);
        user.setName(name);
        user.setAvatarUrl(avatarUrl);
        user.setAccountId(accountId);
        user.setBio(bio);
        userService.createOrUpdateUser(user);
    }
}
