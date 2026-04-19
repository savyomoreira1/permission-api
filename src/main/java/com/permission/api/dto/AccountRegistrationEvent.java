package com.permission.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRegistrationEvent {

    @JsonProperty("id_account")
    private Long idAccount;

    @JsonProperty("id_user")
    private Long idUser;

    @JsonProperty("id_system")
    private Long idSystem;

    @JsonProperty("status")
    private String status;
}
