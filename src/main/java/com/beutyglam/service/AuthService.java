package com.beutyglam.service;


import com.beutyglam.dto.auth.LoginRequestDTO;
import com.beutyglam.dto.auth.LoginResponseDTO;
import com.beutyglam.dto.auth.RegisterRequestDTO;
import com.beutyglam.entity.User;
import com.beutyglam.entity.UserRole;
import com.beutyglam.repository.UserRepository;
import com.beutyglam.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String register(RegisterRequestDTO request) {

        String email = request.getEmail();

        if (email == null || email.isBlank()) {
            throw new RuntimeException("El correo es obligatorio");
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new RuntimeException("Formato de correo inválido");
        }

        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }

        if (request.getPassword() == null || request.getPassword().length() < 6) {
            throw new RuntimeException("La contraseña debe tener al menos 6 caracteres");
        }

        UserRole role = roleRepository.findByRoleName("CLIENT")
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);

        userRepository.save(user);

        return "Usuario registrado correctamente";
    }

    public LoginResponseDTO login(LoginRequestDTO request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        String token = jwtService.generateToken(user);

        LoginResponseDTO response = new LoginResponseDTO();
        response.setMessage("Login exitoso");
        response.setToken(token);

        return response;
    }

    public String refreshToken(String token) {
        return jwtService.refreshToken(token);
    }
}
