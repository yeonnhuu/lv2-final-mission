package finalmission.infrastructure.email.sendgrid.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record SendGridEmailRequest(
        @NotNull(message = "Personalization 필드는 필수입니다.") List<Personalization> personalization,
        @NotNull(message = "From 필드는 필수입니다.") From from,
        @NotNull(message = "Content 필드는 필수입니다.") List<Content> content
) {
    public record Personalization(List<To> to, String subject) {
        public record To(String email) {}
    }

    public record From(String email) {}

    public record Content(String type, String value) {}
}
