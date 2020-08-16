package br.com.stefanini.clients.api.repository.cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.stefanini.clients.api.data.model.Cliente;
import br.com.stefanini.clients.api.repository.filter.ClienteFilter;

public interface ClienteRepositoryQuery {

	public Page<Cliente> filtrar(ClienteFilter clienteFilter, Pageable pageable);
	
}
