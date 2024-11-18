package com.jangburich.domain.payment.application;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.jangburich.domain.payment.domain.PaymentChargeStatus;
import com.jangburich.domain.payment.domain.TeamChargeHistory;
import com.jangburich.domain.payment.domain.repository.TeamChargeHistoryRepository;
import com.jangburich.domain.payment.dto.request.PayRequest;
import com.jangburich.domain.payment.dto.response.ApproveResponse;
import com.jangburich.domain.payment.dto.response.ReadyResponse;
import com.jangburich.domain.payment.exception.TeamNotFoundException;
import com.jangburich.domain.team.domain.Team;
import com.jangburich.domain.team.domain.repository.TeamRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KakaopayService implements PaymentService {

	private final TeamChargeHistoryRepository teamChargeHistoryRepository;
	@Value("${kakaopay.secretKey}")
	private String secretKey;

	@Value("${kakaopay.approve-url}")
	private String approvalUrl;

	@Value("${kakaopay.cancel-url}")
	private String cancelUrl;

	@Value("${kakaopay.fail-url}")
	private String failUrl;

	private final TeamRepository teamRepository;

	private ResponseEntity<ReadyResponse> readyResponseResponseEntity;
	private String userId;

	@Override
	public String getType() {
		return "kakao";
	}

	@Transactional
	@Override
	public ReadyResponse payReady(Long userId, PayRequest payRequest) {
		this.userId = String.valueOf(userId);

		Map<String, String> parameters = new HashMap<>();

		parameters.put("cid", "TC0ONETIME");                                    // 가맹점 코드(테스트용)
		parameters.put("partner_order_id", "1234567890");                       // 주문번호
		parameters.put("partner_user_id", String.valueOf(userId));              // 회원 아이디
		parameters.put("item_name", "ITEM_NAME");                               // 상품명
		parameters.put("quantity", "1");                                        // 상품 수량
		parameters.put("total_amount", payRequest.totalAmount());               // 상품 총액
		parameters.put("tax_free_amount", "0");                                 // 상품 비과세 금액
		parameters.put("approval_url", approvalUrl);                            // 결제 성공 시 URL
		parameters.put("cancel_url", cancelUrl);                                // 결제 취소 시 URL
		parameters.put("fail_url", failUrl);                                    // 결제 실패 시 URL

		HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

		RestTemplate template = new RestTemplate();
		String url = "https://open-api.kakaopay.com/online/v1/payment/ready";

		readyResponseResponseEntity = template.postForEntity(url, requestEntity,
			ReadyResponse.class);

		Team team = teamRepository.findById(payRequest.teamId())
			.orElseThrow(() -> new TeamNotFoundException());

		TeamChargeHistory teamChargeHistory = TeamChargeHistory.builder()
			.transactionId(readyResponseResponseEntity.getBody().tid())
			.paymentAmount(Integer.valueOf(payRequest.totalAmount()))
			.paymentChargeStatus(PaymentChargeStatus.PENDING)
			.team(team)
			.build();

		teamChargeHistoryRepository.save(teamChargeHistory);

		return readyResponseResponseEntity.getBody();
	}

	@Transactional
	@Override
	public ApproveResponse payApprove(String pgToken) {
		Map<String, String> parameters = new HashMap<>();
		parameters.put("cid", "TC0ONETIME");              // 가맹점 코드(테스트용)
		parameters.put("tid", readyResponseResponseEntity.getBody().tid());                       // 결제 고유번호
		parameters.put("partner_order_id", "1234567890"); // 주문번호
		parameters.put("partner_user_id", String.valueOf(userId));    // 회원 아이디
		parameters.put("pg_token", pgToken);              // 결제승인 요청을 인증하는 토큰

		HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

		RestTemplate template = new RestTemplate();

		String url = "https://open-api.kakaopay.com/online/v1/payment/approve";
		ApproveResponse approveResponse = template.postForObject(url, requestEntity, ApproveResponse.class);

		TeamChargeHistory teamChargeHistory = teamChargeHistoryRepository.findByTransactionId(
				readyResponseResponseEntity.getBody().tid())
			.orElseThrow(() -> new NullPointerException());

		teamChargeHistory.completePaymentChargeStatus();

		Team team = teamRepository.findById(teamChargeHistory.getTeam().getId())
			.orElseThrow(() -> new TeamNotFoundException());

		team.updatePoint(teamChargeHistory.getPaymentAmount());

		return approveResponse;
	}

	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		String auth = "SECRET_KEY " + secretKey;
		headers.set("Authorization", auth);
		headers.set("Content-type", "application/json");

		return headers;
	}
}