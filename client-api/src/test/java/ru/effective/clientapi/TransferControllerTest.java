package ru.effective.clientapi;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class TransferControllerTest {


    @Autowired
    MockMvc mockMvc;

    @Test
    @Sql("/sql/users-test.sql")
    void transferMoneyFromUsername_ReturnsHttpStatusOk() throws Exception{

        var requetBuilder = MockMvcRequestBuilders.post("/transfer/test1/money-transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "username" : "test2",
                            "amount": 20.0
                        }
                        """)
                .with(SecurityMockMvcRequestPostProcessors.user("test1"));;


        mockMvc.perform(requetBuilder)
                .andDo(print())
                .andExpectAll(
                        status().isOk()
                );
    }

    @Test
    @Sql("/sql/users-test.sql")
    void transferMoneyUsername_ReturnsProblemDetails() throws Exception{

        var requetBuilder = MockMvcRequestBuilders.post("/transfer/test1/money-transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "username" : "test2",
                            "amount": 600
                        }
                        """)
                .with(SecurityMockMvcRequestPostProcessors.user("test2"));


        mockMvc.perform(requetBuilder)
                .andDo(print())
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                        {
                            "message": "Не достаточно средств"
                        }""")
                );
    }
}
