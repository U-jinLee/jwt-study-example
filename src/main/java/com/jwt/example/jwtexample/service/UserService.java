package com.jwt.example.jwtexample.service;

        import com.jwt.example.jwtexample.entity.User;
        import com.jwt.example.jwtexample.entity.UserRepository;
        import com.jwt.example.jwtexample.entity.UserRequestDto;
        import lombok.RequiredArgsConstructor;
        import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
        import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long join(UserRequestDto userRequestDto) {
        return userRepository.save(
                User.builder()
                        .username(userRequestDto.getUsername())
                        .password(bCryptPasswordEncoder.encode(userRequestDto.getPassword()))
                        .roles("ROLE_USER")
                        .build()
        ).getId();
    }

}
