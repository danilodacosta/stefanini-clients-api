package br.com.stefanini.clients.api.service.v2;

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
import br.com.stefanini.clients.api.data.vo.v2.ClienteVOV2;
import br.com.stefanini.clients.api.repository.ClienteRepository;
import br.com.stefanini.clients.api.repository.filter.ClienteFilter;
import br.com.stefanini.clients.api.service.ClienteService;
import br.com.stefanini.clients.api.util.ClienteUtil;

@Service
public class ClienteServiceV2Impl implements ClienteService<ClienteVOV2, ClienteFilter> {

	@Autowired
	private ClienteRepository repository;

	public ClienteVOV2 create(ClienteVOV2 clienteVO) {

		this.buscarPessoaPorCpf(clienteVO.getCpf());

		ClienteUtil.isDataNascimentoValid(clienteVO.getDataNascimento());

		Cliente entity = DozerConverter.parseObject(clienteVO, Cliente.class);

		ClienteVOV2 vo = DozerConverter.parseObject(this.repository.save(entity), ClienteVOV2.class);

		return vo;
	}

	public List<ClienteVOV2> findAll() {

		List<Cliente> clients = this.repository.findAll();

		return DozerConverter.parseListObjects(clients, ClienteVOV2.class);
	}

	public Optional<ClienteVOV2> findById(Long id) {
		Cliente c = this.findByCliente(id);
		Optional<ClienteVOV2> vo = Optional.of(DozerConverter.parseObject(c, ClienteVOV2.class));
		return vo;
	}

	public void delete(Long id) {
		Cliente cliente = this.findByCliente(id);
		repository.delete(cliente);
	}

	@Override
	public Page<ClienteVOV2> filtrar(ClienteFilter filter, Pageable pageable) {

		Page<Cliente> clientes = repository.filtrar(filter, pageable);

		return clientes.map(this::convertToClienteVOV2);

	}

	@Override
	public ClienteVOV2 update(ClienteVOV2 clienteVO, Long id) {

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
				
		ClienteVOV2 vo = DozerConverter.parseObject(Clienteupdate, ClienteVOV2.class);

		return vo;

	}

	@Override
	public void buscarPessoaPorCpf(String cpf) {
		Cliente cliente = repository.findByCpf(cpf);
		if (cliente != null) {
			throw new ClienteExistenteException();

		}

	}

	private ClienteVOV2 convertToClienteVOV2(Cliente entity) {
		return DozerConverter.parseObject(entity, ClienteVOV2.class);
	}

	private Cliente findByCliente(Long id) {
		return this.repository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente não encontrado"));

	}

}
