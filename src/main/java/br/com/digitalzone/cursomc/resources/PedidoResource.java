package br.com.digitalzone.cursomc.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.digitalzone.cursomc.domain.Categoria;
import br.com.digitalzone.cursomc.domain.Pedido;
import br.com.digitalzone.cursomc.resources.dto.CategoriaDTO;
import br.com.digitalzone.cursomc.services.PedidoService;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoResource {
	
	@Autowired
	private PedidoService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {
		
		Pedido obj = service.find(id);
		
		return ResponseEntity.ok().body(obj);
		
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody @Valid Pedido ped) {
		ped = service.insert(ped);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(ped.getId()).toUri();

		return ResponseEntity.created(uri).build();

	}

}
