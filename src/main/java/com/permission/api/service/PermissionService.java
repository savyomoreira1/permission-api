package com.permission.api.service;

import com.permission.api.domain.entity.*;
import com.permission.api.domain.repository.*;
import com.permission.api.dto.PermissionQueryResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionService {

    private final UserAccountRepository userAccountRepository;
    private final UserAccountSystemRepository userAccountSystemRepository;
    private final ProfilePermissionAccountRepository profilePermissionAccountRepository;

    @Transactional(readOnly = true)
    public PermissionQueryResponseDTO getPermissions(Long idUser, Long idAccount, Long idSystem) {
        log.info("Querying permissions for idUser={}, idAccount={}, idSystem={}", idUser, idAccount, idSystem);

        UserAccount userAccount = userAccountRepository
                .findByIdUserAndIdAccount(idUser, idAccount)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "UserAccount not found for idUser=" + idUser + " and idAccount=" + idAccount));

        UserAccountSystem userAccountSystem = userAccountSystemRepository
                .findByUserAccount_IdUserAccountAndSystem_IdSystem(userAccount.getIdUserAccount(), idSystem)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "UserAccountSystem not found for idUserAccount=" + userAccount.getIdUserAccount()
                                + " and idSystem=" + idSystem));

        List<ProfilePermissionAccount> profilePermissionAccounts =
                profilePermissionAccountRepository.findActivePermissionsByUserAccountSystem(
                        userAccountSystem.getIdUserAccountSystem());

        List<PermissionQueryResponseDTO.PermissionDetailDTO> permissionDetails = profilePermissionAccounts.stream()
                .map(ppa -> {
                    Permission permission = ppa.getProfilePermission().getPermission();
                    return PermissionQueryResponseDTO.PermissionDetailDTO.builder()
                            .idPermission(permission.getIdPermission())
                            .code(permission.getCode())
                            .description(permission.getDescription())
                            .status(permission.getStatus())
                            .createAt(permission.getCreateAt())
                            .build();
                })
                .toList();

        return PermissionQueryResponseDTO.builder()
                .idUser(idUser)
                .idAccount(idAccount)
                .idSystem(idSystem)
                .systemName(userAccountSystem.getSystem().getName())
                .permissions(permissionDetails)
                .build();
    }
}
