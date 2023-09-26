package com.example.tily.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @DisplayName("이메일_중복확인_성공_test")
    @Test
    public void user_email_check_success_test() throws Exception {

        // given
        String email = "test1@nate.com";
        UserRequest.EmailCheckDTO requestDTO = new UserRequest.EmailCheckDTO();
        requestDTO.setEmail(email);

        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions result = mvc.perform(
                post("/email/check")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody)
        );

        String response = result.andReturn().getResponse().getContentAsString();
        System.out.println("response = " + response);

        // then
        result.andExpect(jsonPath("$.success").value("true"));
    }

    @DisplayName("사용자_회원가입_성공_test")
    @Test
    public void user_join_success_test() throws Exception {

        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("test@nate.com");
        requestDTO.setName("test");
        requestDTO.setPassword("test1234!");

        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions result = mvc.perform(
                post("/join")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody)
        );

        // then
        result.andExpect(jsonPath("$.success").value("true"));
        result.andExpect(jsonPath("$.message").value("ok"));
    }

    @DisplayName("사용자_회원가입_실패_test_1 : 잘못된 이메일 형식")
    @Test
    public void user_join_fail_test_1() throws Exception {

        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("testnate.com");
        requestDTO.setName("test");
        requestDTO.setPassword("test1234!");

        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions result = mvc.perform(
                post("/join")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody)
        );

        // then
        result.andExpect(jsonPath("$.success").value("false"));
        result.andExpect(jsonPath("$.message").value("올바른 이메일 형식을 입력해주세요."));
    }

    @DisplayName("사용자_회원가입_실패_test_2 : 잘못된 비밀번호 형식")
    @Test
    public void user_join_fail_test_2() throws Exception {

        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("test@nate.com");
        requestDTO.setName("test");
        requestDTO.setPassword("test1234");

        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions result = mvc.perform(
                post("/join")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody)
        );

        // then
        result.andExpect(jsonPath("$.success").value("false"));
        result.andExpect(jsonPath("$.message").value("올바른 비밀번호 형식을 입력해주세요."));
    }
}
