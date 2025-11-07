package com.emoji.mymoji.dto.userDto;

// 3. 사용자 특성치 조회 시 반환할 데이터
public record AttributeResponse(
        double attribute1, // 정서안정성
        double attribute2, // 외향성
        double attribute3, // 친화성
        double attribute4, // 성실성
        double attribute5  // 개방성
) {}
