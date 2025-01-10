package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.inbound.controller;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Acompanhamento;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Bebida;
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
public class AcompanhamentoControllerTest {

    @Autowired
    private PersistenceItemRepository<Acompanhamento> acompanhamentoPersistenceItemRepository;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void deveriaRetornarTodosOsAcompanhamentos() throws Exception {
        Acompanhamento bebiba1 = new Acompanhamento("acompanhamento1", 10);
        Acompanhamento bebiba2 = new Acompanhamento("acompanhamento2", 5);
        Acompanhamento bebiba3 = new Acompanhamento("acompanhamento3", 15);

        acompanhamentoPersistenceItemRepository.salvar(bebiba1);
        acompanhamentoPersistenceItemRepository.salvar(bebiba2);
        acompanhamentoPersistenceItemRepository.salvar(bebiba3);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/acompanhamentos")
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3));
    }
}
