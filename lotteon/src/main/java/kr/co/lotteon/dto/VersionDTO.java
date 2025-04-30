package kr.co.lotteon.dto;

import kr.co.lotteon.entity.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VersionDTO {
    private int versionId;
    private String versionName;
    private String uid;
    private LocalDateTime rdate;
    private String changeHistory;

    public static VersionDTO from(Version version) {
        return new VersionDTO(
                version.getVersionId(),
                version.getVersionName(),
                version.getUser() != null ? version.getUser().getUid() : "알수없음",
                version.getRdate(),
                version.getChangeHistory()
        );
    }
}
