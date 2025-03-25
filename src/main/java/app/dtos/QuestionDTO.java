package app.dtos;


import app.entities.Question;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionDTO {

    private Integer id;
    private String questionText;
    private Question.DifficultyType difficultyType;
    private Set<AnswerDTO> answers = new HashSet<>();


    public QuestionDTO(Question question){
        this.id = question.getId();
        this.questionText = question.getQuestionText();
        this.difficultyType = question.getDifficultyType();
        if (question.getAnswers() !=null){
            question.getAnswers().forEach(answer -> answers.add(new AnswerDTO(answer)));
        }
    }
}
