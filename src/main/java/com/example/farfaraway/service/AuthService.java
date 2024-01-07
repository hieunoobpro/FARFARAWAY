package com.example.farfaraway.service;

import com.example.farfaraway.entity.Role;
import com.example.farfaraway.entity.TokenConfirm;
import com.example.farfaraway.entity.User;
import com.example.farfaraway.exception.BadRequestException;
import com.example.farfaraway.exception.NotFoundException;
import com.example.farfaraway.model.request.LoginRequest;
import com.example.farfaraway.model.request.RegisterRequest;
import com.example.farfaraway.repository.RoleRepository;
import com.example.farfaraway.repository.TokenConfirmRepository;
import com.example.farfaraway.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final TokenConfirmRepository tokenConfirmRepository;
    private final PasswordEncoder passwordEncoder;
    private final HttpSession httpSession;
    private final RoleRepository roleRepository;

    public AuthService(UserRepository userRepository, TokenConfirmRepository tokenConfirmRepository, PasswordEncoder passwordEncoder, HttpSession httpSession,
                       RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.tokenConfirmRepository = tokenConfirmRepository;
        this.passwordEncoder = passwordEncoder;
        this.httpSession = httpSession;
        this.roleRepository = roleRepository;
    }

    public String register(RegisterRequest request) {
        // check email exist
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email đã tồn tại");
        }

        // get role user
        Role roleUser = roleRepository.findByName("USER")
                .orElseThrow(() -> new NotFoundException("Không tìm thấy role"));

        // create user
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhone());
        user.setAddress(request.getAddress());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatus(true);
        user.setRoles(List.of(roleUser));
        userRepository.save(user);

        // create token
        TokenConfirm tokenConfirm = new TokenConfirm();
        tokenConfirm.setToken(UUID.randomUUID().toString());
        tokenConfirm.setCreatedAt(LocalDateTime.now());
        tokenConfirm.setExpiredAt(LocalDateTime.now().plusMinutes(20));
        tokenConfirm.setUser(user);
        tokenConfirmRepository.save(tokenConfirm);

        return "http://localhost:8080/confirm?token=" + tokenConfirm.getToken();
    }

    // isValid : true/false, message : thông báo lỗi
    public Map<String, Object> confirm(String token) {
        Map<String, Object> data = new HashMap<>();
        // Token có tồn tại không
        Optional<TokenConfirm> tokenConfirmOptional = tokenConfirmRepository.findByToken(token);
        if (tokenConfirmOptional.isEmpty()) {
            data.put("isValid", false);
            data.put("message", "Token không tồn tại");
            return data;
        }

        TokenConfirm tokenConfirm = tokenConfirmOptional.get();
        // Token đã được xác nhận chưa (nếu trường confirmedAt = null là chưa xác nhận)
        if (tokenConfirm.getConfirmedAt() != null) {
            data.put("isValid", false);
            data.put("message", "Token đã được xác nhận");
            return data;
        }

        // Token đã hết hạn hay chưa
        if (tokenConfirm.getExpiredAt().isBefore(LocalDateTime.now())) {
            data.put("isValid", false);
            data.put("message", "Token đã hết hạn");
            return data;
        }

        // set lại trường confirmedAt = thời điểm thực hiện
        tokenConfirm.setConfirmedAt(LocalDateTime.now());

        // set lại trường isEnabled của user = true
        tokenConfirm.getUser().setStatus(true);

        // lưu lại tokenConfirm và user
        tokenConfirmRepository.save(tokenConfirm);
        userRepository.save(tokenConfirm.getUser());

        data.put("isValid", true);
        data.put("message", "Xác nhận thành công");
        return data;
    }

}
