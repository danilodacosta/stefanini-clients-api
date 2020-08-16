package br.com.stefanini.clients.api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.stefanini.clients.api.conveter.DozerConverter;
import br.com.stefanini.clients.api.data.exception.ClienteExistenteException;
import br.com.stefanini.clients.api.data.exception.EntidadeNaoEncontradaException;
import br.com.stefanini.clients.api.data.model.Cliente;
import br.com.stefanini.clients.api.data.vo.ClienteVO;
import br.com.stefanini.clients.api.repository.ClienteRepository;
import br.com.stefanini.clients.api.repository.filter.ClienteFilter;
import br.com.stefanini.clients.api.service.ClienteService;
import br.com.stefanini.clients.api.util.ClienteUtil;

@Service
public class ClienteServiceImpl implements ClienteService<ClienteVO, ClienteFilter> {

	@Autowired
	private ClienteRepository repository;

	public ClienteVO create(ClienteVO clienteVO) {

		this.buscarPessoaPorCpf(clienteVO.getCpf());

		ClienteUtil.isDataNascimentoValid(clienteVO.getDataNascimento());

		Cliente entity = DozerConverter.parseObject(clienteVO, Cliente.class);

		ClienteVO vo = DozerConverter.parseObject(this.repository.save(entity), ClienteVO.class);

		return vo;
	}

	public List<ClienteVO> findAll() {
		return DozerConverter.parseListObjects(this.repository.findAll(), ClienteVO.class);
	}

	public Optional<ClienteVO> findById(Long id) {
		Cliente c = this.findByCliente(id);
		Optional<ClienteVO> vo = Optional.of(DozerConverter.parseObject(c, ClienteVO.class));
		return vo;
	}

	public void delete(Long id) {
		Cliente cliente = this.findByCliente(id);
		repository.delete(cliente);
	}

	@Override
	public ClienteVO update(ClienteVO clienteVO, Long id) {

		Optional<Cliente> cliente = this.repository.findById(id);

		if (!cliente.isPresent()) {
			throw new EntidadeNaoEncontradaException("Cliente não encontrado");
		}

		ClienteUtil.isDataNascimentoValid(clienteVO.getDataNascimento());
		
		if(clienteVO.getId() == null) 
			clienteVO.setId(id);
				
		Cliente entity = DozerConverter.parseObject(clienteVO, Cliente.class);
		
		Cliente Clienteupdate = this.repository.save(entity);
		
		Clienteupdate.setCreatedAt(cliente.get().getCreatedAt());
		
		ClienteVO vo = DozerConverter.parseObject(Clienteupdate, ClienteVO.class);

		return vo;
	}

	@Override
	public Page<ClienteVO> filtrar(ClienteFilter filter, Pageable pageable) {

		Page<Cliente> clientes = repository.filtrar(filter, pageable);

		return clientes.map(this::convertToClienteVO);

	}

	private Cliente findByCliente(Long id) {
		Cliente c = this.repository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente não encontrado"));
		return c;
	}

	@Override
	public void buscarPessoaPorCpf(String cpf) {
		Cliente cliente = repository.findByCpf(cpf);
		if (cliente != null) {
			throw new ClienteExistenteException();
		}

	}

	private ClienteVO convertToClienteVO(Cliente entity) {
		return DozerConverter.parseObject(entity, ClienteVO.class);
	}

}
