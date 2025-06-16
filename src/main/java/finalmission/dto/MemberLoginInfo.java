package finalmission.dto;

import finalmission.domain.Member;

public record MemberLoginInfo(long id, String name, String email) {

    public MemberLoginInfo(Member member) {
        this(member.id(), member.name(), member.email());
    }
}
