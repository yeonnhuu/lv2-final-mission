package finalmission.dto.info;

import finalmission.domain.Member;

public record MemberLoginInfo(Long id, String name, String email) {

    public MemberLoginInfo(Member member) {
        this(member.id(), member.name(), member.email());
    }
}
