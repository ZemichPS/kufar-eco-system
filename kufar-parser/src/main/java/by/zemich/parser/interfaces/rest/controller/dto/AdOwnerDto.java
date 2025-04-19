package by.zemich.parser.interfaces.rest.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AdOwnerDto {
    private String id;
    private Integer feedbackCount;
    private LocalDateTime firstFeedbackCreatedAt;
    private Float rate;
}
