package by.zemich.parser.infrastructure.clients.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeedbackResponse {
    private boolean isMine;
    private List<Feedback> feedbacks;
    private Map<Double, Integer> countByScores = new HashMap<>();

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Feedback {
        private String id;
        private String senderId;
        private String segment;
        private String score;
        private String textReview;
        private List<String> images;
        @JsonProperty("created_at")
        private ZonedDateTime createdAt;
        private String reply;
        private List<String> checkboxes;
        private Extra extra;
        private boolean complaintAllowed;
        private boolean isSeller;

        @Data
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Extra {
            private String senderName;
            private String senderProfileImage;
            private String senderAvatarUrl;
        }
    }

}
