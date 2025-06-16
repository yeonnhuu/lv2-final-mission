package finalmission.helper;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import java.util.List;
import org.springframework.restdocs.payload.FieldDescriptor;

public class RestDocsFieldSnippets {

    public static class Auth {

        public static final List<FieldDescriptor> MEMBER_LOGIN_REQUEST_FIELDS = List.of(
                fieldWithPath("email").description("로그인할 멤버의 이메일 주소"),
                fieldWithPath("password").description("로그인할 멤버의 비밀번호 (평문)")
        );

        public static final List<FieldDescriptor> MEMBER_LOGIN_CHECK_RESPONSE_FIELDS = List.of(
                fieldWithPath("name").description("현재 로그인된 멤버의 이름")
        );
    }

    public static class Reservation {

        public static final List<FieldDescriptor> RESERVATION_REQUEST_FIELDS = List.of(
                fieldWithPath("lectureId").description("예약하려는 강의의 ID")
        );

        public static final List<FieldDescriptor> RESERVATION_RESPONSE_FIELDS = List.of(
                fieldWithPath("id").description("생성된 예약의 고유 ID"),
                fieldWithPath("lecture.sport").description("예약된 강의의 운동 종목"),
                fieldWithPath("lecture.date").description("예약된 강의의 날짜 (yyyy-MM-dd)"),
                fieldWithPath("member.name").description("예약한 멤버의 이름")
        );

        public static final List<FieldDescriptor> RESERVATION_RESPONSE_LIST_FIELDS = List.of(
                fieldWithPath("[].id").description("예약의 고유 ID"),
                fieldWithPath("[].lecture.sport").description("강의의 운동 종목"),
                fieldWithPath("[].lecture.date").description("강의 날짜 (yyyy-MM-dd)"),
                fieldWithPath("[].member.name").description("예약자의 이름")
        );

        public static final List<FieldDescriptor> RESERVATION_MINE_RESPONSE_LIST_FIELDS = List.of(
                fieldWithPath("[].id").description("내 예약의 고유 ID"),
                fieldWithPath("[].reservedAt").description("예약이 생성된 날짜 (yyyy-MM-dd)"),
                fieldWithPath("[].lecture.sport").description("예약된 운동 종목"),
                fieldWithPath("[].lecture.date").description("예약된 강의 날짜 (yyyy-MM-dd)")
        );
    }
}
