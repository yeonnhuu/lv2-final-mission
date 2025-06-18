package finalmission.helper;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

import java.util.List;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;

public class RestDocsFieldSnippets {

    public static class Auth {

        public static final List<FieldDescriptor> MEMBER_LOGIN_REQUEST_FIELDS = List.of(
                fieldWithPath("email").description("로그인할 멤버의 이메일"),
                fieldWithPath("password").description("로그인할 멤버의 비밀번호")
        );

        public static final List<FieldDescriptor> MEMBER_LOGIN_CHECK_RESPONSE_FIELDS = List.of(
                fieldWithPath("name").description("현재 로그인된 멤버의 이름")
        );
    }

    public static class Booking {

        public static final List<FieldDescriptor> BOOKING_CREATE_REQUEST_FIELDS = List.of(
                fieldWithPath("lectureId").description("예약하려는 예약의 ID"),
                fieldWithPath("reserveCount").description("예약하려는 예약 인원")
        );

        public static final List<ParameterDescriptor> BOOKING_UPDATE_PATH_PARAMETERS = List.of(
                parameterWithName("id").description("수정하려는 예약의 ID")
        );

        public static final List<FieldDescriptor> BOOKING_UPDATE_REQUEST_FIELDS = List.of(
                fieldWithPath("reserveCount").description("수정하려는 예약 인원")
        );

        public static final List<ParameterDescriptor> BOOKING_DELETE_PATH_PARAMETERS = List.of(
                parameterWithName("id").description("삭제하려는 예약의 ID")
        );

        public static final List<FieldDescriptor> BOOKING_MINE_RESPONSE_LIST_FIELDS = List.of(
                fieldWithPath("[].id").description("예약의 ID"),
                fieldWithPath("[].reservedAt").description("예약한 날짜 (yyyy-MM-dd)"),
                fieldWithPath("[].reserveCount").description("예약한 강의의 예약 인원"),
                fieldWithPath("[].lecture.sport").description("예약한 강의의 운동 종목"),
                fieldWithPath("[].lecture.date").description("예약한 강의의 수업 날짜 (yyyy-MM-dd)")
        );

        public static final List<FieldDescriptor> BOOKING_RESPONSE_FIELDS = List.of(
                fieldWithPath("id").description("예약의 ID"),
                fieldWithPath("lecture.sport").description("예약한 강의의 운동 종목"),
                fieldWithPath("lecture.date").description("예약한 강의의 수업 날짜 (yyyy-MM-dd)"),
                fieldWithPath("member.name").description("예약한 멤버의 이름"),
                fieldWithPath("member.email").description("예약한 멤버의 이메일")
        );

        public static final List<FieldDescriptor> BOOKING_RESPONSE_LIST_FIELDS = List.of(
                fieldWithPath("[].id").description("예약의 ID"),
                fieldWithPath("[].lecture.sport").description("예약한 강의의 운동 종목"),
                fieldWithPath("[].lecture.date").description("예약한 강의의 수업 날짜 (yyyy-MM-dd)"),
                fieldWithPath("[].member.name").description("예약한 멤버의 이름"),
                fieldWithPath("[].member.email").description("예약한 멤버의 이메일")
        );
    }
}
