package br.com.stefanini.clients.api.core.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.stefanini.clients.api.service.ClienteService;

/**AbstractRestController Classe com implementação de metodos padrões para as operações crud.
 * @author Danilo Pereira
 * @param VO - value object que será usado para ser trafegado e posteriormente ser convertido em entidade para ser persistido.
 * @param Service - Implementação do Service que será usado para persistir a entidade.
 * @param F - Classe de filtro que será usada para pesquisa e paginação de registro. 
 */
public abstract class AbstractRestController <VO, Service extends ClienteService<VO , F>, F> implements IRestController<VO> {

	@Autowired
	private Service service;
		
	@GetMapping()
	@Override
	@ResponseStatus(value = HttpStatus.OK)
	public List<VO> findAll() {
		return service.findAll();
	}
	
	/**
	 * @author Danilo Pereira
	 * @param page - número da pagina corrente defaultValue = 0.
	 * @param limit - quantidade de registros por pagina defaultValue = 5.
	 * @param Filtro - Classe de filtro que será usada para pesquisa e paginação de registro. 
	 */
	@GetMapping("/pages")
	public Page<VO> pesquisar(F filter, 
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "5")int limit) {
		
		Pageable pageable = PageRequest.of(page, limit);
		
		return service.filtrar(filter, pageable);
	}		
	
	@GetMapping("/{id}")
	@Override
	public ResponseEntity<VO> findById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(service.findById(id).get());
	}

	@PostMapping()
	@Override
	@ResponseStatus(value = HttpStatus.CREATED)
	public VO create(@Valid VO vo) {
		return service.create(vo);
	}	
	
	@PutMapping("/{id}")
	@Override
	@ResponseStatus(value = HttpStatus.OK)
	public VO update(@RequestBody @Valid VO vo, @PathVariable Long id) {
		return service.update(vo, id);
	}
	
	@DeleteMapping("/{id}")
	@Override
	@ResponseStatus(value = HttpStatus.OK)
	public void delete(@PathVariable Long id) {
		service.delete(id);
		
	}
	
}