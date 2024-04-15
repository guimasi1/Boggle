package masi.guido.Boggle;

import jdk.jshell.spi.ExecutionControlProvider;
import kong.unirest.ObjectMapper;
import masi.guido.Boggle.entities.User;
import masi.guido.Boggle.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@WebMvcTest(User.class)
public class UserTests {
    private static final String END_POINT_PATH = "/api/users";

    @Autowired
    private MockMvc mockMvc;

    @Value("${spring.jwt.user}")
    private String token;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldReturn400BadRequest() throws Exception {



    }

}
