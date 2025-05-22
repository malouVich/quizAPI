package app.dtos;


import app.entities.Question;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;
    @JsonProperty("question")
    private String questionText;
    private Question.DifficultyType difficultyType;
    @JsonProperty("options")
    private List<AnswerDTO> answers;


    public QuestionDTO(Question question){
        this.id = question.getId();
        this.questionText = question.getQuestionText();
        this.difficultyType = question.getDifficultyType();
        if (question.getAnswers() != null) {
            this.answers = question.getAnswers()
                    .stream()
                    .map(AnswerDTO::new)
                    .collect(Collectors.toList());
        }
    }
}