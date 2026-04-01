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

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }

        UserRole role = roleRepository.findByRoleName("CLIENT")
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
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
}
