package nl.tudelft.oopp.demo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.given;

import nl.tudelft.oopp.demo.controllers.QuestionController;
import nl.tudelft.oopp.demo.repositories.QuestionRepository;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import nl.tudelft.oopp.demo.services.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class QuestionControllerTest {


    @Autowired
    private MockMvc mockMvc;

    private final long roomId = 5L;
    private final long questionId = 1L;

    @Mock
    private QuestionService questionService;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(
                new QuestionController(questionService)).build();
    }

    @Test
    public void testGetAllQuestions() throws Exception {
        mockMvc.perform(get("/questions")).andExpect(status().isOk());
        verify(questionService).getQuestions();
    }

    @Test
    public void testGetQuestionsByRoom() throws Exception {
        mockMvc.perform(get("/questions/{roomId}", roomId)).andExpect(status().isOk());
        verify(questionService).getQuestionsByRoom(roomId);
    }

    @Test
    public void testAddNewQuestion() throws Exception {
        String payload = "2& Sandra& When is lab assignment 3 due?";

        mockMvc.perform(post("/questions").content(payload)).
                andExpect(status().isOk());
        verify(questionService).addNewQuestion(payload);
    }

    @Test
    public void testGetAnsweredQuestions() throws Exception {
        mockMvc.perform(get("/questions/answered/{roomId}", roomId)).
                andExpect(status().isOk());
        verify(questionService).getAnsweredQuestions(roomId);
    }

    @Test
    public void testMarkAnswered() throws Exception {
        mockMvc.perform(put("/questions/markAnswered/{questionId}", questionId))
                .andExpect(status().isOk());
        verify(questionService).markQuestionAsAnswered(questionId);
    }

    @Test
    public void testDeleteQuestion() throws Exception {

        mockMvc.perform(delete("/questions/{questionId}", questionId))
                .andExpect(status().isOk());
        verify(questionService).deleteQuestion(questionId);
    }

    @Test
    public void testUpvote() throws Exception {
        mockMvc.perform(put("/questions/upvote/{questionId}", questionId))
                .andExpect(status().isOk());
        verify(questionService).upvote(questionId);
    }

    @Test
    public void testDeUpvote() throws Exception {
        mockMvc.perform(put("/questions/deupvote/{questionId}", questionId))
                .andExpect(status().isOk());
        verify(questionService).deUpvote(questionId);
    }

    @Test
    public void testUpdateQuestion() throws Exception {
        mockMvc.perform(put("/questions/{questionId}", questionId)
                .content("Test question"))
                .andExpect(status().isOk());
        verify(questionService).updateQuestion(questionId, "Test question");
    }


    @Test
    public void testSetAnswer() throws Exception {
        mockMvc.perform(put("/questions/setanswer/{questionId}", questionId)
                .content("Test answer"))
                .andExpect(status().isOk());
        verify(questionService).setAnswer(questionId, "Test answer");
    }

}
