package com.doran.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doran.jwt.JwtProvider;
import com.doran.parent.type.Provider;
import com.doran.redis.invite.service.InviteService;
import com.doran.user.dto.req.InviteReqDto;
import com.doran.user.dto.req.UserJoinDto;
import com.doran.user.dto.req.UserTokenBaseDto;
import com.doran.user.service.OauthService;
import com.doran.user.service.UserService;
import com.doran.utils.auth.Auth;
import com.doran.utils.common.UserInfo;
import com.doran.utils.response.CommonResponseEntity;
import com.doran.utils.response.SuccessCode;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/user")
public class UserController {
    private final OauthService oauthService;
    private final JwtProvider jwtProvider;
    private final UserService userService;
    private final InviteService inviteService;

    //자체 회원가입 - 로컬 테스트용
    //부모 아이 상관없는 그냥 깡 유저 - admin 생성용
    @PostMapping("")
    public ResponseEntity join(@RequestBody UserJoinDto userJoinDto, HttpServletResponse response) {
        UserTokenBaseDto findDto = oauthService.getFindDto(userJoinDto.getEmail(), userJoinDto.getName(),
            Provider.local,
            userJoinDto.getUserRole());

        String accessToken = jwtProvider.createAccessToken(findDto);

        response.setHeader("AccessToken", accessToken);

        return CommonResponseEntity
            .getResponseEntity(SuccessCode.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PARENT')")
    @PostMapping("/message")
    public ResponseEntity invite(@RequestBody InviteReqDto dto) {
        UserInfo info = Auth.getInfo();
        String code = inviteService.findCode(info.getUserId()).getCode();
        userService.sendMessage(code, dto.getTel());

        return CommonResponseEntity.getResponseEntity(SuccessCode.OK, "초대코드 전송 완료");
    }
}
