package cat.itacademy.s04.t01.userapi.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserController userController;

    @BeforeEach
    void setUp() {
        userController.clearUsers();
    }


    @Test
    void getUsersReturnsEmptyListInitially() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void createUserReturnsWithId() throws Exception {
        String body = """
                {"name": "Ada Lovelace", "email": "ada@example.com"}
                """;

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Ada Lovelace"))
                .andExpect(jsonPath("$.email").value("ada@example.com"));
    }

    @Test
    void createUserReturnsErrorWhenNameIsBlank() throws Exception {
        String body = """
                {"name": "", "email": "ada@example.com"}
                """;

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getUserByIdReturnsCorrectUser() throws Exception {
        String body = """
                {"name": "Ada Perez", "email": "ada@example.com"}
                """;

        String response = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String id = objectMapper.readTree(response).get("id").asString();

        mockMvc.perform(get("/users/"+id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Ada Perez"))
                .andExpect(jsonPath("$.email").value("ada@example.com"));
    }

    @Test
    void getUserByIdReturnsNotFoundIfIMissing() throws Exception {
        String fakeId = "0a000aa0-0000-0aa0-0a00-0000aaa000a0";

        mockMvc.perform(get("/users/"+fakeId))
                .andExpect(status().isNotFound());
    }

    @Test
    void getUsersParamNameReturnsFilteredUsers() throws Exception {
        String body1 = """
                {"name": "Ada Perez", "email": "ada@example.com"}
                """;

        String body2 = """
                {"name": "John Marquez", "email": "john@example.com"}
                """;

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Ada Perez"))
                .andExpect(jsonPath("$.email").value("ada@example.com"));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("John Marquez"))
                .andExpect(jsonPath("$.email").value("john@example.com"));

        mockMvc.perform(get("/users?name=j"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("John Marquez"));
    }
}
