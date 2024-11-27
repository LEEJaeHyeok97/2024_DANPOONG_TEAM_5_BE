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
import java.time.LocalDate;
import java.util.Optional;
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

        team.validateIsTeamLeader(team.getTeamLeader().getUser_id(), user.getUserId());

        user.validateHasPointWithPrepayAmount(prepayRequest.prepayAmount(), user.getPoint());

        user.usePoint(prepayRequest.prepayAmount());
        PointTransaction pointTransaction = PointTransaction
                .builder()
                .transactionType(TransactionType.PREPAY)
                .transactionedPoint(prepayRequest.prepayAmount())
                .user(user)
                .store(store)
                .build();

        pointTransactionRepository.save(pointTransaction);

        LocalDate expirationDate = LocalDate.now().plusDays(store.getPrepaymentDuration());

        StoreTeam buildedStoreTeam = StoreTeam
                .builder()
                .team(team)
                .store(store)
                .point(prepayRequest.prepayAmount())
                .personalAllocatedPoint(prepayRequest.personalAllocatedAmount())
                .remainPoint(prepayRequest.prepayAmount())
                .prepaidExpirationDate(expirationDate)
                .build();

        Optional<StoreTeam> storeAndTeam = storeTeamRepository.findByStoreAndTeam(store, team);

        if (storeAndTeam.isEmpty()) {
            storeAndTeam = Optional.of(storeTeamRepository.save(buildedStoreTeam));
        }

        StoreTeam storeTeam = storeAndTeam.get();
        storeTeam.recharge(prepayRequest.prepayAmount());

        return Message.builder()
                .message("매장 선결제가 완료되었습니다.")
                .build();
    }
}
