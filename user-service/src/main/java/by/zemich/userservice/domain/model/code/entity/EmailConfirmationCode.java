package by.zemich.userservice.domain.model.code.entity;

import by.zemich.userservice.domain.model.code.vo.CodeId;
import by.zemich.userservice.domain.model.user.vo.UserId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailConfirmationCode {
    private CodeId codeId;
    private UserId userId;
    private String email;
    private String code;
    private LocalDateTime expiresAt;
    private boolean confirmed;
}
