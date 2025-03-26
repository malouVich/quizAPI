package app.config;


import app.daos.AnswerDAO;
import app.daos.QuestionDAO;
import app.dtos.AnswerDTO;
import app.dtos.QuestionDTO;
import app.entities.Answer;
import app.entities.Question;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Populate {
    private QuestionDAO questionDAO;
    private AnswerDAO answerDAO;
    static List<QuestionDTO> questions = new ArrayList<>();
    static List<AnswerDTO> answers = new ArrayList<>();


    public void populateQuestions() {
        this.questionDAO = QuestionDAO.getInstance(HibernateConfig.getEntityManagerFactory());
        this.answerDAO = AnswerDAO.getInstance(HibernateConfig.getEntityManagerFactory());
        ObjectMapper objectMapper = new ObjectMapper();


        try {
            // Læs JSON-filen og konverter den direkte til en liste af QuestionDTO objekter
            this.questions = objectMapper.readValue(new File("src/main/resources/http/questions.json"),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, QuestionDTO.class));

            // For hver QuestionDTO, opret og gem Question i databasen
            for (QuestionDTO questionDTO : questions) {
                // Opret og gem Question ved at sende DTO til DAO
                createQuestion(questionDTO);

                // Hvis der er tilknyttede svar i questionDTO's options (som answers), opret og gem disse
                if (questionDTO.getAnswers() != null) {
                    for (AnswerDTO answerDTO : questionDTO.getAnswers()) {
                        createAnswer(answerDTO, questionDTO);  // Opret og gem Answer
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        // Kun send DTO til DAO
        public void createQuestion (QuestionDTO questionDTO){
            this.questionDAO.create(questionDTO);  // send DTO til DAO
        }

        // Kun send DTO til DAO
        public void createAnswer (AnswerDTO answerDTO, QuestionDTO questionDTO){
            this.answerDAO.create(answerDTO);  // send DTO til DAO
        }
        public static void main (String[]args){
            Populate populate = new Populate();

            populate.populateQuestions();
        }
    }
