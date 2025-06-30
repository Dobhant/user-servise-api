package org.example.userserviceapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.userserviceapi.controller.UserController;
import org.example.userserviceapi.dto.UserDto;
import org.example.userserviceapi.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.ReplaceWithMock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @ReplaceWithMock private UserService userService;

    @Test
    void testGetAllUsers() throws Exception {
        List<UserDto> users = List.of(
                createUserDto(1L, "Alice", "alice@example.com", 25),
                createUserDto(2L, "Bob", "bob@example.com", 30)
        );

        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Alice")))
                .andExpect(jsonPath("$[1].email", is("bob@example.com")));
    }

    @Test
    void testCreateUser() throws Exception {
        UserDto dto = createUserDto(null, "Charlie", "charlie@example.com", 22);
        UserDto savedDto = createUserDto(3L, "Charlie", "charlie@example.com", 22);

        when(userService.createUser(Mockito.any())).thenReturn(savedDto);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.name", is("Charlie")));
    }

    private UserDto createUserDto(Long id, String name, String email, int age) {
        UserDto dto = new UserDto();
        dto.setId(id);
        dto.setName(name);
        dto.setEmail(email);
        dto.setAge(age);
        return dto;
    }
}
