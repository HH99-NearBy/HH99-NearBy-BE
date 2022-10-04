package com.hh99.nearby.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    CHALLENGE_NOTFOUND(HttpStatus.BAD_REQUEST,"E_001","찾을수 없는 챌린지 입니다."),
    CHALLENGE_MODIFY_FORBIDDEN(HttpStatus.BAD_REQUEST,"E_002","수정권한이 없는 사용자 입니다."),
    CHALLENGE_DELETE_FORBIDDEN(HttpStatus.BAD_REQUEST,"E_003","삭제권한이 없는 사용자 입니다."),

    DETAIL_CHALLENGE_NOTFOUND(HttpStatus.BAD_REQUEST,"E_004","잘못된 챌린지 번호입니다."),
    DETAIL_LIMITED_CHALLENGE(HttpStatus.BAD_REQUEST,"E_005","더이상 신청할 수 없는 챌린지 입니다."),
    DETAIL_ALREADY_JOINED(HttpStatus.BAD_REQUEST,"E_006","이미 참여한 챌린지입니다."),
    DETAIL_NOT_JOINED(HttpStatus.BAD_REQUEST,"E_007","참여하지 않은 챌린지 입니다."),

    LOGIN_NOTFOUND_MEMBER(HttpStatus.BAD_REQUEST,"E_008","사용자를 찾을수 없습니다."),
    LOGIN_NOT_CERTIFICATION(HttpStatus.BAD_REQUEST,"E_009","이메일을 인증해 주십시오."),
    LOGIN_WRONG_INPUT(HttpStatus.BAD_REQUEST,"E_010","잘못된 입력입니다."),

    TOKEN_REISSUE(HttpStatus.BAD_REQUEST,"E_011","재로그인이 필요합니다."),
    REFRESHTOKEN_NOT_VALID(HttpStatus.BAD_REQUEST,"E_012","유효하지 않은 토큰입니다."),

    SIGNUP_ALREADY_EMAIL(HttpStatus.BAD_REQUEST,"E_013","이미 존재하는 이메일 입니다."),
    SIGNUP_ALREADY_NICKNAME(HttpStatus.BAD_REQUEST,"E_014","이미 존재하는 닉네임 입니다."),
    SIGNUP_EMPTY_EMAIL(HttpStatus.BAD_REQUEST,"E_015","이메일을 입력해 주세요."),
    SIGNUP_EMPTY_PASSWORD(HttpStatus.BAD_REQUEST,"E_016","비밀번호를 입력해 주세요."),
    SIGNUP_EMAIL_CHECK(HttpStatus.BAD_REQUEST,"E_017","중복된 이메일 입니다."),
    SIGNUP_NICKNAME_CHECK(HttpStatus.BAD_REQUEST,"E_018","중복된 닉네임 입니다."),

    WISH_CHALLENGE_NOTFOUND(HttpStatus.BAD_REQUEST,"E_019","잘못된 챌린지 번호입니다."),
    WISH_MEMBER_NOTFOUND(HttpStatus.BAD_REQUEST,"E_020","찾을수 없는 회원입니다."),
    WISH_ALREADY_SELECT(HttpStatus.BAD_REQUEST,"E_021","이미 찜 해놓은 챌린지 입니다."),
    WISH_NOT_SELECT(HttpStatus.BAD_REQUEST,"E_022","찜 해놓지 않은 챌린지 입니다."),
    WISH_DELETE_FORBIDDEN(HttpStatus.BAD_REQUEST,"E_023","취소 권한이 없는 사용자 입니다.");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String errorMsg;

}