package kiseleva.dev.SurveyManagementSystem.dtos.userDTO;

import lombok.Data;

@Data
public class RefreshRequestDTO {
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
