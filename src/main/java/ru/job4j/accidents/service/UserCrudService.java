package ru.job4j.accidents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.AuthorityRepository;
import ru.job4j.accidents.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

/**
 * Сервис пользователей
 */
@Service
@RequiredArgsConstructor
public class UserCrudService {
    private final PasswordEncoder encoder;
    private final UserRepository users;
    private final AuthorityRepository authorities;

    /**
     * Регистрация пользователя
     * @param user тип {@link ru.job4j.accidents.model.User} новый пользователь
     * @return тип {@link java.util.Optional<ru.job4j.accidents.model.User>}
     */
    public Optional<User> registration(User user) {
        user.setEnabled(true);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAuthority(authorities.findByAuthority("ROLE_USER"));
        try {
            user = users.save(user);
        } catch (Exception e) {
            user = null;
        }
        return Optional.ofNullable(user);
    }
}
