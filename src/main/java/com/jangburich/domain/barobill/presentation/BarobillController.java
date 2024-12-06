package com.jangburich.domain.barobill.presentation;

import com.jangburich.domain.barobill.application.BarobillService;
import com.jangburich.domain.barobill.dto.request.RegistAndReverseIssueTaxInvoiceRequest;
import com.jangburich.domain.barobill.dto.request.RegistCorpRequest;
import com.jangburich.global.payload.Message;
import com.jangburich.global.payload.ResponseCustom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Barobill", description = "Barobill API")
@RestController
@RequestMapping("/barobill")
@RequiredArgsConstructor
public class BarobillController {

    private final BarobillService barobillService;

    @Operation(summary = "바로빌 가입 및 회원사 등록", description = "소상공인의 사업자로 바로빌 회원가입 + 장부리치의 회원사로 등록합니다.")
    @PostMapping
    public ResponseCustom<Message> registCorp(
            @RequestBody RegistCorpRequest registCorpRequest
    ) {
        barobillService.registCorp(registCorpRequest);
        return ResponseCustom.OK(Message.builder().message("success").build());
    }

    @Operation(summary = "세금계산서 역발행 요청", description = "세금 계산서를 소상공인이 역발행 요청합니다.")
    @PostMapping("/registAndReverseIssueTaxInvoice")
    public ResponseCustom<Message> registAndReverseIssueTaxInvoice() {
        barobillService.registAndReverseIssueTaxInvoice();
        return ResponseCustom.OK(Message.builder().message("success").build());
    }

    @Operation(summary = "세금계산서 역발행 취소", description = "세금 계산서 역발행 취소(test)")
    @PostMapping("/cancel")
    public ResponseCustom<Message> cancel() {
        barobillService.procTaxInvoice();
        return ResponseCustom.OK(Message.builder().message("success").build());
    }

    @Operation(summary = "세금계산서 상태조회", description = "세금계산서 상태를 조회합니다.")
    @GetMapping("/testget")
    public ResponseCustom<Message> get() {
        barobillService.getTaxInvoiceStateEX();
        return ResponseCustom.OK(Message.builder().message("success").build());
    }

    @Operation(summary = "세금계산서 삭제", description = "세금계산서를 삭제합니다.")
    @PostMapping("/delete")
    public ResponseCustom<Message> delete() {
        barobillService.deleteTaxInvoice();
        return ResponseCustom.OK(Message.builder().message("success").build());
    }
}
