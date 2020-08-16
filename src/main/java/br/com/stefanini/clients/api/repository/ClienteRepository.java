package br.com.stefanini.clients.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.stefanini.clients.api.data.model.Cliente;
import br.com.stefanini.clients.api.repository.cliente.ClienteRepositoryQuery;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> , ClienteRepositoryQuery {

	Cliente findByCpf(String cpf);
}