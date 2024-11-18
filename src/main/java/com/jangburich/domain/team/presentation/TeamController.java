package com.jangburich.domain.team.presentation;

import com.jangburich.domain.team.application.TeamService;
import com.jangburich.domain.team.dto.request.RegisterTeamRequest;
import com.jangburich.global.payload.Message;
import com.jangburich.global.payload.ResponseCustom;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Team", description = "Team API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    public ResponseCustom<Message> registerTeam(
            Authentication authentication,
            @RequestBody RegisterTeamRequest registerTeamRequest
    ) {
        return ResponseCustom.OK();
    }
}
