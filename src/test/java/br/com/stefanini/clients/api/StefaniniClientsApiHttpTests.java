package br.com.stefanini.clients.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.stefanini.clients.api.data.vo.ClienteVO;
import br.com.stefanini.clients.api.data.vo.v2.ClienteVOV2;
import br.com.stefanini.clients.api.enums.SexoEnum;
import br.com.stefanini.clients.api.repository.filter.ClienteFilter;
import br.com.stefanini.clients.api.service.impl.ClienteServiceImpl;
import br.com.stefanini.clients.api.service.v2.ClienteServiceV2Impl;

@SpringBootTest
@AutoConfigureMockMvc
class StefaniniClientsApiHttpTests {

	@Autowired
	private MockMvc mockMvc;


    @Autowired
    private ObjectMapper objectMapper;

	
	@Autowired
	private ClienteServiceImpl service;
	
	@Autowired
	private ClienteServiceV2Impl serviceV2;

	@Test
	public void buscarClientePorID() {
		Long id = 1L;
		ClienteVO cliente = service.findById(id).get();
		assertThat(cliente.getId()).isEqualTo(id);
	}
		
	@Test
	void postClientesV1() throws Exception {
	 
		ClienteVO vo = new ClienteVO();
		vo.setCpf("65748852055");
		vo.setNome("MOCK DA SILVA JUNIOR");
		vo.setDataNascimento(LocalDate.parse("2017-01-17"));
		vo.setSexo(SexoEnum.MASCULINO);
		vo.setNacionalidade("BRASILEIRO");
		vo.setNaturalidade("PARAENSE");
				
	    mockMvc.perform(post("/v1/clientes")
	            .contentType("application/json")
	            .content(objectMapper.writeValueAsString(vo)))
	            .andExpect(status().isCreated());

	    ClienteVO cliente = service.findById(2L).get();
	    	
	    assertThat(cliente.getNome()).isEqualTo("MOCK DA SILVA JUNIOR");
    
	}
	
	
	@Test
	void postClientesV2() throws Exception {
	 
		ClienteVOV2 vo2 = new ClienteVOV2();
		vo2.setCpf("76790890009");
		vo2.setNome("MOCK DA SILVA JUNIOR 2");
		vo2.setDataNascimento(LocalDate.parse("2017-01-17"));
		vo2.setSexo(SexoEnum.MASCULINO);
		vo2.setNacionalidade("BRASILEIRO");
		vo2.setEndereco("ROD. BR 316 KM 12 - CONDOMINIO CITTA MARIS");
		vo2.setNaturalidade("PARAENSE");
				
	    mockMvc.perform(post("/v2/clientes")
	            .contentType("application/json")
	            .content(objectMapper.writeValueAsString(vo2)))
	            .andExpect(status().isCreated());

	    ClienteVOV2 cliente = serviceV2.findById(3L).get();
	    	
	    assertThat(cliente.getNome()).isEqualTo("MOCK DA SILVA JUNIOR 2");
    
	}
	
	
	@Test
	void putClientes() throws Exception {
	 
		ClienteVO vo = new ClienteVO();
		vo.setId(3L);
		vo.setCpf("65748852055");
		vo.setNome("MOCK D. S. JUNIOR SEGUNDO");
		vo.setDataNascimento(LocalDate.parse("2017-01-17"));
		vo.setSexo(SexoEnum.MASCULINO);
		vo.setNacionalidade("BRASILEIRO");
		vo.setNaturalidade("PARAENSE");
				
	    mockMvc.perform(put("/v1/clientes/"+vo.getId())
	            .contentType("application/json")
	            .content(objectMapper.writeValueAsString(vo)))
	            .andExpect(status().isOk());

	    ClienteVO cliente = service.findById(3L).get();
	    	
	    assertThat(cliente.getNome()).isEqualTo("MOCK D. S. JUNIOR SEGUNDO");    
	}
		
	
	
	@Test
	public void buscarClientesPageFilter() throws JsonProcessingException, Exception {
		ClienteFilter filter = new ClienteFilter();
		filter.setNome("MOCK D. S. JUNIOR");
		mockMvc.perform(get("/v2/clientes/pages")
		            .contentType("application/json")
		            .content(objectMapper.writeValueAsString(filter)))
		            .andExpect(status().isOk());		    
	}
	
	
	@Test
	void removeClientes() throws Exception {
	 
		ClienteVO vo = new ClienteVO();
		vo.setId(1L);		
	    mockMvc.perform(delete("/v1/clientes/"+vo.getId())
	    		.content(objectMapper.writeValueAsString(vo)))
	            .andExpect(status().isOk());
	}
	
	
}
