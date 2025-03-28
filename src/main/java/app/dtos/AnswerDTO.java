package app.dtos;

import app.entities.Answer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnswerDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer answerId;
    @JsonProperty("answerText")
    private String answerText;
    @JsonProperty("correct")
    private Boolean isCorrect;


    public AnswerDTO(Answer answer){
        this.answerId = answer.getId();
        this.answerText = answer.getAnswerText();
        this.isCorrect = answer.getIsCorrect();
    }

    public static List<AnswerDTO> answerDTOList(List<Answer> answers){
        return List.of(answers.stream().map(AnswerDTO::new).toArray(AnswerDTO[]::new));
    }
}