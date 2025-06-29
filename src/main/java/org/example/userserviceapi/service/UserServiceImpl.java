package org.example.userserviceapi.service;

import lombok.RequiredArgsConstructor;
import org.example.userserviceapi.dto.UserDto;
import org.example.userserviceapi.entity.User;
import org.example.userserviceapi.mapper.UserMapper;
import org.example.userserviceapi.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapper.toDto(user);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = mapper.toEntity(userDto);
        user.setId(null); // new user
        User saved = userRepository.save(user);
        return mapper.toDto(saved);
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAge(userDto.getAge());
        User saved = userRepository.save(user);
        return mapper.toDto(saved);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
