package finalmission.helper;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import java.util.List;
import org.springframework.restdocs.payload.FieldDescriptor;

public class RestDocsFieldSnippets {

    public static class Auth {

        public static final List<FieldDescriptor> MEMBER_LOGIN_REQUEST_FIELDS = List.of(
                fieldWithPath("email").description("멤버 이메일"),
                fieldWithPath("password").description("멤버 비밀번호")
        );

        public static final List<FieldDescriptor> MEMBER_LOGIN_CHECK_RESPONSE_FIELDS = List.of(
                fieldWithPath("name").description("멤버 이름")
        );
    }

    public static class Reservation {

        public static final List<FieldDescriptor> RESERVATION_REQUEST_FIELDS = List.of(
                fieldWithPath("lectureId").description("운동 종목")
        );

        public static final List<FieldDescriptor> RESERVATION_RESPONSE_FIELDS = List.of(
                fieldWithPath("id").description("예약 ID"),
                fieldWithPath("lecture.sport").description("운동 종목"),
                fieldWithPath("lecture.date").description("수업 날짜"),
                fieldWithPath("member.name").description("예약자 이름")
        );

        public static final List<FieldDescriptor> RESERVATION_RESPONSE_LIST_FIELDS = List.of(
                fieldWithPath("[].id").description("예약 ID"),
                fieldWithPath("[].lecture.sport").description("운동 종목"),
                fieldWithPath("[].lecture.date").description("수업 날짜"),
                fieldWithPath("[].member.name").description("예약자 이름")
        );
    }
}
