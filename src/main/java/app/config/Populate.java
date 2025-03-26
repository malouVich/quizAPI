package app.config;


import app.daos.QuestionDAO;
import app.dtos.QuestionDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManagerFactory;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Populate {
    private QuestionDAO questionDAO;
    static List<QuestionDTO> questions = new ArrayList<>();



    public void populateQuestions() {
    this.questionDAO = QuestionDAO.getInstance(HibernateConfig.getEntityManagerFactory());
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Læs JSON-filen og konverter den direkte til en liste af Poem-objekter
            this.questions = objectMapper.readValue(new File("src/main/resources/http/questions.json"),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, QuestionDTO.class));

            for (QuestionDTO questionDTO : questions) {
                createQuestion(questionDTO);  // Kald metoden for at gemme digtet
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createQuestion(QuestionDTO questionDTO) {
        questionDAO.create(questionDTO); // Kalder save-metoden på DAO
    }

    public static void main(String[]args){
        Populate populate = new Populate();

        populate.populateQuestions();
    }
}