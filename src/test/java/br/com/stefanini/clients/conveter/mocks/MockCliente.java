package br.com.stefanini.clients.conveter.mocks;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.stefanini.clients.api.data.model.Cliente;
import br.com.stefanini.clients.api.data.vo.ClienteVO;
import br.com.stefanini.clients.api.enums.SexoEnum;

public class MockCliente {

	public Cliente mockEntity() {
		return mockEntity(0);
	}

	public ClienteVO mockVO() {
		return mockVO(0);
	}

	public List<Cliente> mockEntityList() {
		List<Cliente> clientes = new ArrayList<Cliente>();
		for (int i = 0; i < 14; i++) {
			clientes.add(mockEntity(i));
		}
		return clientes;
	}

	public List<ClienteVO> mockVOList() {
		List<ClienteVO> clientesvo = new ArrayList<>();
		for (int i = 0; i < 14; i++) {
			clientesvo.add(mockVO(i));
		}
		return clientesvo;
	}

	private Cliente mockEntity(Integer number) {
		Cliente cliente = new Cliente();
		cliente.setId(number.longValue());
		cliente.setCpf("01999759060");
		cliente.setNome("MOCK DA SILVA"+"_"+number);
		cliente.setDataNascimento(LocalDate.parse("1991-01-11"));
		cliente.setSexo(SexoEnum.MASCULINO);
		cliente.setNacionalidade("BRASILEIRO");
		cliente.setNaturalidade("PARAENSE");
		return cliente;
	}

	private ClienteVO mockVO(Integer number) {
		ClienteVO vo = new ClienteVO();
		vo.setId(number.longValue());
		vo.setCpf("01999759060");
		vo.setNome("MOCK DA SILVA"+"_"+number);
		vo.setDataNascimento(LocalDate.parse("1991-01-11"));
		vo.setSexo(SexoEnum.MASCULINO);
		vo.setNacionalidade("BRASILEIRO");
		vo.setNaturalidade("PARAENSE");
		return vo;
	}
}
