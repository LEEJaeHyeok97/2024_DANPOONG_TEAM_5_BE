package com.jangburich.domain.barobill.presentation;

import com.jangburich.domain.barobill.application.BarobillService;
import com.jangburich.domain.barobill.dto.request.GetCertificateRegistURLRequest;
import com.jangburich.domain.barobill.dto.request.RegistCorpRequest;
import com.jangburich.global.payload.Message;
import com.jangburich.global.payload.ResponseCustom;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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

    @PostMapping
    public ResponseCustom<Message> registCorp(
            @RequestBody RegistCorpRequest registCorpRequest
    ) {
        barobillService.registCorp(registCorpRequest);
        return ResponseCustom.OK(Message.builder().message("success").build());
    }

    @PostMapping("/registCertificate")
    public ResponseCustom<Message> getCertificateRegistURL(
            @RequestBody GetCertificateRegistURLRequest getCertificateRegistURLRequest
    ) {
        barobillService.getCertificateRegistURL(getCertificateRegistURLRequest);
        return ResponseCustom.OK(Message.builder().message("success").build());
    }
}
