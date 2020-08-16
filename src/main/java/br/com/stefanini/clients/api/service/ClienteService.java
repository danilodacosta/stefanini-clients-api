package br.com.stefanini.clients.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**Cliente BaseService interface com os metodos base para ser implementados no v1 e v2.
 * @author Danilo Pereira
 * @param T - VO que será usado para Trafegar e ser convertido em entidade para ser persistido no banco.
 * @param F - Classe de filtro que será usada para pesquisa e paginação. 
 */
public interface ClienteService<T, F> {

	List<T> findAll();
	
	Page<T> filtrar(F filter, Pageable pageable);	 
		
	T create(T vo);
	
	T update(T vo, Long id);
			
	Optional<T> findById(Long id);
	
	void delete (Long id);
	
	void buscarPessoaPorCpf(String cpf);	
	
}
