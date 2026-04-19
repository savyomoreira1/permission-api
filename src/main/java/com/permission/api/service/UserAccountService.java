package com.permission.api.service;

import com.permission.api.domain.entity.UserAccount;
import com.permission.api.domain.entity.UserAccountSystem;
import com.permission.api.domain.repository.SystemRepository;
import com.permission.api.domain.repository.UserAccountRepository;
import com.permission.api.domain.repository.UserAccountSystemRepository;
import com.permission.api.dto.UserRegistrationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final UserAccountSystemRepository userAccountSystemRepository;
    private final SystemRepository systemRepository;

    @Transactional
    public void processUserRegistration(UserRegistrationEvent event) {
        log.info("Processing user registration event: idUser={}, idAccount={}",
                event.getIdUser(), event.getIdAccount());

        userAccountRepository.findByIdUserAndIdAccount(event.getIdUser(), event.getIdAccount())
                .ifPresentOrElse(
                        existing -> log.info("UserAccount already exists: {}", existing.getIdUserAccount()),
                        () -> {
                            UserAccount userAccount = UserAccount.builder()
                                    .idUser(event.getIdUser())
                                    .idAccount(event.getIdAccount())
                                    .status(event.getStatus() != null ? event.getStatus() : "ACTIVE")
                                    .build();
                            userAccountRepository.save(userAccount);
                            log.info("UserAccount created: {}", userAccount.getIdUserAccount());
                        }
                );
    }

    @Transactional
    public void processAccountSystemRegistration(Long idUser, Long idAccount, Long idSystem) {
        log.info("Processing account-system registration: idUser={}, idAccount={}, idSystem={}",
                idUser, idAccount, idSystem);

        UserAccount userAccount = userAccountRepository
                .findByIdUserAndIdAccount(idUser, idAccount)
                .orElseGet(() -> {
                    UserAccount ua = UserAccount.builder()
                            .idUser(idUser)
                            .idAccount(idAccount)
                            .status("ACTIVE")
                            .build();
                    return userAccountRepository.save(ua);
                });

        userAccountSystemRepository.findByUserAccount_IdUserAccountAndSystem_IdSystem(
                        userAccount.getIdUserAccount(), idSystem)
                .ifPresentOrElse(
                        existing -> log.info("UserAccountSystem already exists: {}", existing.getIdUserAccountSystem()),
                        () -> systemRepository.findById(idSystem).ifPresent(system -> {
                            UserAccountSystem uas = UserAccountSystem.builder()
                                    .userAccount(userAccount)
                                    .system(system)
                                    .status("ACTIVE")
                                    .build();
                            userAccountSystemRepository.save(uas);
                            log.info("UserAccountSystem created for idSystem={}", idSystem);
                        })
                );
    }
}
