package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.inbound.controller;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Bebida;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Sobremesa;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.repository.PersistenceItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class SobremesaControllerTest {

    @Autowired
    private PersistenceItemRepository<Sobremesa> sobremesaPersistenceItemRepository;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void deveriaRetornarTodasAsSobremesas() throws Exception {
        Sobremesa sobremesa1 = new Sobremesa("sobremesa1", 10);
        Sobremesa sobremesa2 = new Sobremesa("sobremesa2", 5);
        Sobremesa sobremesa3 = new Sobremesa("sobremesa3", 15);

        sobremesaPersistenceItemRepository.salvar(sobremesa1);
        sobremesaPersistenceItemRepository.salvar(sobremesa2);
        sobremesaPersistenceItemRepository.salvar(sobremesa3);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/sobremesas")
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3));
    }
}
