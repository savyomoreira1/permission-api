package com.permission.api.controller;

import com.permission.api.dto.PermissionQueryResponseDTO;
import com.permission.api.service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/permissions")
@RequiredArgsConstructor
@Tag(name = "Permissions", description = "Endpoints for querying user permissions")
public class PermissionController {

    private final PermissionService permissionService;

    @GetMapping
    @Operation(
            summary = "Query permissions for a user-account-system relationship",
            description = "Returns all active permissions associated with a given user, account and system combination"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permissions found successfully"),
            @ApiResponse(responseCode = "404", description = "User-account or user-account-system relationship not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    })
    public ResponseEntity<PermissionQueryResponseDTO> getPermissions(
            @Parameter(description = "User identifier", required = true)
            @RequestParam("id_user") Long idUser,
            @Parameter(description = "Account identifier", required = true)
            @RequestParam("id_account") Long idAccount,
            @Parameter(description = "System identifier", required = true)
            @RequestParam("id_system") Long idSystem) {

        PermissionQueryResponseDTO response = permissionService.getPermissions(idUser, idAccount, idSystem);
        return ResponseEntity.ok(response);
    }
}
