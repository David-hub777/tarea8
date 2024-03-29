package com.example.tarea8;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.tarea8.dto.PersonDTO;
import com.example.tarea8.repository.PersonRepository;
import com.example.tarea8.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PersonRestController.class)
public class PersonRestControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockBean private PersonService personService;

  @MockBean private PersonRepository personRepository;

  private JacksonTester<PersonDTO> jsonTester;

  private PersonDTO personDTO;

  @BeforeEach
  public void setup() {
    JacksonTester.initFields(this, objectMapper);
    personDTO = new PersonDTO();
  }

  @Test
  public void persistPerson_IsValid_PersonPersisted() throws Exception {
    final String personDTOJson = jsonTester.write(personDTO).getJson();
    given(personService.isValid(any(PersonDTO.class))).willReturn(true);
    mockMvc
        .perform(post("/persistPerson").content(personDTOJson).contentType(APPLICATION_JSON_UTF8))
        .andExpect(status().isCreated());
    verify(personRepository).persist(any(PersonDTO.class));
  }

  @Test
  public void persistPerson_IsNotValid_PersonNotPersisted() throws Exception {
    final String personDTOJson = jsonTester.write(personDTO).getJson();
    given(personService.isValid(any(PersonDTO.class))).willReturn(false);
    mockMvc
        .perform(post("/persistPerson").content(personDTOJson).contentType(APPLICATION_JSON_UTF8))
        .andExpect(status().isIAmATeapot());
    verify(personRepository, times(0)).persist(any(PersonDTO.class));
  }
}
