package br.com.stefanini.clients.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.stefanini.clients.api.core.controller.AbstractRestController;
import br.com.stefanini.clients.api.data.vo.ClienteVO;
import br.com.stefanini.clients.api.repository.filter.ClienteFilter;
import br.com.stefanini.clients.api.service.impl.ClienteServiceImpl;

@RestController
@RequestMapping("/v1/clientes")
public class ClienteController extends AbstractRestController<ClienteVO, ClienteServiceImpl, ClienteFilter> {

}