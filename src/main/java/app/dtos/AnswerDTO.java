package app.dtos;

import app.entities.Answer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnswerDTO {
    private Integer answerId;
    private String answerText;
    private boolean isCorrect;


    public AnswerDTO(Answer answer){
        this.answerId = answer.getId();
        this.answerText = answer.getAnswerText();
        this.isCorrect = answer.isCorrect();
    }

    public static List<AnswerDTO> answerDTOList(List<Answer> answers){
        return List.of(answers.stream().map(AnswerDTO::new).toArray(AnswerDTO[]::new));
    }
}
