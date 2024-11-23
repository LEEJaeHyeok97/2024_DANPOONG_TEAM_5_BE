package com.jangburich.domain.store.service;

import com.jangburich.domain.point.domain.PointTransaction;
import com.jangburich.domain.point.domain.TransactionType;
import com.jangburich.domain.point.domain.repository.PointTransactionRepository;
import com.jangburich.domain.store.domain.Store;
import com.jangburich.domain.store.domain.StoreTeam;
import com.jangburich.domain.store.dto.request.PrepayRequest;
import com.jangburich.domain.store.repository.StoreRepository;
import com.jangburich.domain.store.repository.StoreTeamRepository;
import com.jangburich.domain.team.domain.Team;
import com.jangburich.domain.team.domain.repository.TeamRepository;
import com.jangburich.domain.user.domain.User;
import com.jangburich.domain.user.repository.UserRepository;
import com.jangburich.global.error.DefaultNullPointerException;
import com.jangburich.global.payload.ErrorCode;
import com.jangburich.global.payload.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PrepayService {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final TeamRepository teamRepository;
    private final StoreTeamRepository storeTeamRepository;
    private final PointTransactionRepository pointTransactionRepository;

    @Transactional
    public Message prepay(String userId, PrepayRequest prepayRequest) {
        User user = userRepository.findByProviderId(userId)
                .orElseThrow(() -> new DefaultNullPointerException(ErrorCode.INVALID_AUTHENTICATION));

        Team team = teamRepository.findById(prepayRequest.teamId())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 팀 id 입니다."));

        Store store = storeRepository.findById(prepayRequest.storeId())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 가게 id 입니다."));

        if (!team.getTeamLeader().getUser_id().equals(user.getUserId())) {
            return Message.builder()
                    .message("팀의 리더가 아닌 사람은 선결제를 할 수 없습니다.")
                    .build();
        }

        if (prepayRequest.prepayAmount() > user.getPoint()) {
            return Message.builder()
                    .message("보유하고 있는 금액이 선결제 하려는 금액보다 적습니다.")
                    .build();
        }

        user.usePoint(prepayRequest.prepayAmount());
        PointTransaction pointTransaction = PointTransaction
                .builder()
                .transactionType(TransactionType.PURCHASE)
                .transactionedPoint(prepayRequest.prepayAmount())
                .user(user)
                .store(store)
                .build();

        pointTransactionRepository.save(pointTransaction);

        StoreTeam storeTeam = StoreTeam
                .builder()
                .team(team)
                .store(store)
                .point(prepayRequest.prepayAmount())
                .personalAllocatedPoint(prepayRequest.personalAllocatedAmount())
                .remainPoint(prepayRequest.prepayAmount())
                .build();

        storeTeamRepository.save(storeTeam);

        return Message.builder()
                .message("매장 선결제가 완료되었습니다.")
                .build();
    }
}