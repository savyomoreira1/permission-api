package com.permission.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionQueryResponseDTO {

    @JsonProperty("id_user")
    private Long idUser;

    @JsonProperty("id_account")
    private Long idAccount;

    @JsonProperty("id_system")
    private Long idSystem;

    @JsonProperty("system_name")
    private String systemName;

    @JsonProperty("permissions")
    private List<PermissionDetailDTO> permissions;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PermissionDetailDTO {

        @JsonProperty("id_permission")
        private Long idPermission;

        @JsonProperty("code")
        private String code;

        @JsonProperty("description")
        private String description;

        @JsonProperty("status")
        private String status;

        @JsonProperty("create_at")
        private LocalDateTime createAt;
    }
}
