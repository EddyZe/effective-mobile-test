package ru.effective.clientapi.services;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.effective.clientapi.repositories.UserRepository;
import ru.effective.clientapi.security.AuthenticationResponse;
import ru.effective.clientapi.security.JwtService;
import ru.effective.commons.DTO.UserSingInDTO;
import ru.effective.commons.exceptions.UserNotFoundException;
import ru.effective.commons.models.UserDetailsImpl;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse login(UserSingInDTO userSingInDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userSingInDTO.getUsername(), userSingInDTO.getPassword()));

        UserDetailsImpl user = userRepository.findByUsername(userSingInDTO.getUsername())
                .map(u -> new UserDetailsImpl(userSingInDTO))
                .orElseThrow(
                () -> new UserNotFoundException("Пользователь не найден"));

        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }
}
