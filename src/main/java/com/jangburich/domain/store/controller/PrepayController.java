package com.jangburich.domain.store.controller;

import com.jangburich.domain.store.dto.request.PrepayRequest;
import com.jangburich.domain.store.service.PrepayService;
import com.jangburich.global.payload.Message;
import com.jangburich.global.payload.ResponseCustom;
import com.jangburich.utils.parser.AuthenticationParser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Prepay", description = "Prepay API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/prepay")
public class PrepayController {

    private final PrepayService prepayService;

    @Operation(summary = "선결제", description = "팀과 매장 선결제를 진행합니다.")
    @PostMapping
    public ResponseCustom<Message> prepay(Authentication authentication,
                                          @RequestBody PrepayRequest prepayRequest) {
        return ResponseCustom.OK(prepayService.prepay(AuthenticationParser.parseUserId(authentication), prepayRequest));
    }
}
