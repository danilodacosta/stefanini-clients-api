package br.com.stefanini.clients.api.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import br.com.stefanini.clients.api.data.model.Cliente;
import br.com.stefanini.clients.api.repository.cliente.ClienteRepositoryQuery;
import br.com.stefanini.clients.api.repository.filter.ClienteFilter;

public class ClienteRepositoryImpl implements ClienteRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Page<Cliente> filtrar(ClienteFilter clienteFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Cliente> criteria = builder.createQuery(Cliente.class);
	
		Root<Cliente> root = criteria.from(Cliente.class);
	
		Predicate[] predicates = criarRestricoes(clienteFilter, builder, root);
		criteria.where(predicates);
	
		TypedQuery<Cliente> query = manager.createQuery(criteria);
		
		adicionarRestricoesDePaginacao(query, pageable);
		
		List<Cliente> results = query.getResultList(); 
		
		return new PageImpl<>(results, pageable, total(clienteFilter));
	}
	
	private Predicate[] criarRestricoes(ClienteFilter clienteFilter, CriteriaBuilder builder,
			Root<Cliente> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if (!StringUtils.isEmpty(clienteFilter.getNome())) {
			predicates.add(builder.like(
					builder.lower(root.get("nome")), "%" + clienteFilter.getNome().toLowerCase() + "%"));
		}
		
		if (!StringUtils.isEmpty(clienteFilter.getCpf())) {
			predicates.add(builder.like(
					builder.lower(root.get("cpf")), "%" + clienteFilter.getCpf() + "%"));
		}
		
		if (!StringUtils.isEmpty(clienteFilter.getEmail())) {
			predicates.add(builder.like(
					builder.lower(root.get("email")), "%" + clienteFilter.getEmail().toLowerCase() + "%"));
		}
		
		if (clienteFilter.getDataNascimento() != null) {
			predicates.add(
              builder.equal(root.get("dataNascimento"), clienteFilter.getDataNascimento()));
		}
				
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	
	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}
	
	private Long total(ClienteFilter pessoaFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Cliente> root = criteria.from(Cliente.class);
		
		Predicate[] predicates = criarRestricoes(pessoaFilter, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}
}
