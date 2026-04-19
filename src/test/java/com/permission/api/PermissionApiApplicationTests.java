package com.permission.api;

import com.permission.api.domain.entity.*;
import com.permission.api.domain.repository.*;
import com.permission.api.dto.PermissionQueryResponseDTO;
import com.permission.api.service.PermissionService;
import com.permission.api.service.ResourceNotFoundException;
import com.permission.api.service.UserAccountService;
import com.permission.api.dto.UserRegistrationEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
class PermissionApiApplicationTests {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private SystemRepository systemRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private ProfilePermissionRepository profilePermissionRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserAccountSystemRepository userAccountSystemRepository;

    @Autowired
    private ProfilePermissionAccountRepository profilePermissionAccountRepository;

    private SystemEntity system;
    private Permission permission;
    private ProfilePermission profilePermission;
    private UserAccount userAccount;
    private UserAccountSystem userAccountSystem;

    @BeforeEach
    void setUp() {
        profilePermissionAccountRepository.deleteAll();
        userAccountSystemRepository.deleteAll();
        profilePermissionRepository.deleteAll();
        permissionRepository.deleteAll();
        userAccountRepository.deleteAll();
        systemRepository.deleteAll();

        system = systemRepository.save(SystemEntity.builder()
                .idtOwner(1L)
                .name("Test System")
                .status("ACTIVE")
                .build());

        permission = permissionRepository.save(Permission.builder()
                .system(system)
                .code("READ_DATA")
                .description("Read data permission")
                .status("ACTIVE")
                .build());

        profilePermission = profilePermissionRepository.save(ProfilePermission.builder()
                .permission(permission)
                .idProfile(1L)
                .system(system)
                .status("ACTIVE")
                .build());

        userAccount = userAccountRepository.save(UserAccount.builder()
                .idUser(100L)
                .idAccount(200L)
                .status("ACTIVE")
                .build());

        userAccountSystem = userAccountSystemRepository.save(UserAccountSystem.builder()
                .userAccount(userAccount)
                .system(system)
                .status("ACTIVE")
                .build());

        profilePermissionAccountRepository.save(ProfilePermissionAccount.builder()
                .profilePermission(profilePermission)
                .userAccountSystem(userAccountSystem)
                .status("ACTIVE")
                .build());
    }

    @Test
    void contextLoads() {
    }

    @Test
    void shouldReturnPermissionsForUserAccountSystem() {
        PermissionQueryResponseDTO result = permissionService.getPermissions(100L, 200L, system.getIdSystem());

        assertThat(result).isNotNull();
        assertThat(result.getIdUser()).isEqualTo(100L);
        assertThat(result.getIdAccount()).isEqualTo(200L);
        assertThat(result.getIdSystem()).isEqualTo(system.getIdSystem());
        assertThat(result.getSystemName()).isEqualTo("Test System");
        assertThat(result.getPermissions()).hasSize(1);
        assertThat(result.getPermissions().get(0).getCode()).isEqualTo("READ_DATA");
    }

    @Test
    void shouldThrowNotFoundWhenUserAccountDoesNotExist() {
        assertThatThrownBy(() -> permissionService.getPermissions(999L, 999L, system.getIdSystem()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void shouldProcessUserRegistrationEvent() {
        UserRegistrationEvent event = new UserRegistrationEvent(300L, 400L, "ACTIVE");
        userAccountService.processUserRegistration(event);

        assertThat(userAccountRepository.findByIdUserAndIdAccount(300L, 400L)).isPresent();
    }

    @Test
    void shouldNotDuplicateUserAccountOnReprocessing() {
        UserRegistrationEvent event = new UserRegistrationEvent(100L, 200L, "ACTIVE");
        userAccountService.processUserRegistration(event);

        long count = userAccountRepository.findAll().stream()
                .filter(ua -> ua.getIdUser().equals(100L) && ua.getIdAccount().equals(200L))
                .count();
        assertThat(count).isEqualTo(1);
    }
}
