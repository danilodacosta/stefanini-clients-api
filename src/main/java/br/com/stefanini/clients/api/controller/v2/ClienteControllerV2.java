package br.com.stefanini.clients.api.controller.v2;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.stefanini.clients.api.core.controller.AbstractRestController;
import br.com.stefanini.clients.api.data.vo.v2.ClienteVOV2;
import br.com.stefanini.clients.api.repository.filter.ClienteFilter;
import br.com.stefanini.clients.api.service.v2.ClienteServiceV2Impl;

@RestController
@RequestMapping("/v2/clientes")
public class ClienteControllerV2 extends AbstractRestController<ClienteVOV2, ClienteServiceV2Impl, ClienteFilter> {

}