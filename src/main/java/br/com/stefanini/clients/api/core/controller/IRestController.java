package br.com.stefanini.clients.api.core.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface IRestController<T> {

	public abstract List<T> findAll();

	public abstract ResponseEntity<T> findById(@PathVariable(value = "id") Long id);

	public abstract T create(@RequestBody @Valid T entity);
	
	public abstract void delete(@PathVariable Long id);

	public abstract T update(@RequestBody @Valid T entity, @PathVariable Long id);

}