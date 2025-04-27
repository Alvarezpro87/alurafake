package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.task.dto.NewOpenTextTaskDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;   // <- de Spring
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;  // <- MUDOU
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional                                             // rollback após o teste
@Sql(scripts = { "/clean.sshouldReturn201WhenOpenTextIsCreatedql", "/insert-course.sql" },                       // popula antes do teste
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class TaskControllerTest {

    @Autowired
    MockMvc mvc;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    void shouldReturn201WhenOpenTextIsCreated() throws Exception {

        NewOpenTextTaskDTO dto = new NewOpenTextTaskDTO(
                1L, "What did we learn?", 1);

        mvc.perform(
                        post("/task/new/opentext")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }
}
