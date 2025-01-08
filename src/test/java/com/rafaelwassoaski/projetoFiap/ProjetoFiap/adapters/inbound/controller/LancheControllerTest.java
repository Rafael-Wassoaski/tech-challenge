package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.inbound.controller;

import com.google.gson.Gson;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Lanche;
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

@SpringBootTest
public class LancheControllerTest {

    @Autowired
    private PersistenceItemRepository<Lanche> lanchePersistenceItemRepository;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void deveriaRetornarTodosOsLanches() throws Exception {
        Lanche lanche1 = new Lanche("lanche1", 10);
        Lanche lanche2 = new Lanche("lanche2", 5);
        Lanche lanche3 = new Lanche("lanche3", 15);

        lanchePersistenceItemRepository.salvar(lanche1);
        lanchePersistenceItemRepository.salvar(lanche2);
        lanchePersistenceItemRepository.salvar(lanche3);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/lanches")
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3));
    }
}
