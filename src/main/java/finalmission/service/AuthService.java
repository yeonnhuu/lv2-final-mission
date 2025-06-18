package finalmission.service;

import finalmission.auth.CookieExtractor;
import finalmission.auth.TokenProvider;
import finalmission.common.exception.AuthException;
import finalmission.domain.Member;
import finalmission.domain.MemberRepository;
import finalmission.dto.info.MemberLoginInfo;
import finalmission.dto.request.MemberLoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final CookieExtractor cookieExtractor;

    public ResponseCookie loginMemberWithCookie(MemberLoginRequest request) {
        Member member = findMemberByEmailAndPassword(request.email(), request.password());
        long memberId = member.id();
        String token = tokenProvider.createToken(String.valueOf(memberId));
        return cookieExtractor.createCookieByToken(token);
    }

    public MemberLoginInfo findMemberLoginInfoByToken(String token) {
        long memberId = Long.parseLong(tokenProvider.parsePayload(token));
        Member loginMember = findMemberById(memberId);
        return new MemberLoginInfo(loginMember);
    }

    private Member findMemberByEmailAndPassword(String email, String password) {
        return memberRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new AuthException("존재하지 않는 회원 이메일 혹은 비밀번호입니다."));
    }

    private Member findMemberById(final long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new AuthException("존재하지 않는 회원 정보입니다."));
    }
}

