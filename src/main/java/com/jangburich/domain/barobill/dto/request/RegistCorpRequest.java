package com.jangburich.domain.barobill.dto.request;

public record RegistCorpRequest(
        String corpNum,
        String corpName,
        String ceoName,
        String bizType,
        String bizClass,
        String postNum,
        String addr1,
        String addr2,
        String memberName,
        String juminNum,
        String id,
        String pwd,
        String grade,
        String tel,
        String hp,
        String email
) {
}
