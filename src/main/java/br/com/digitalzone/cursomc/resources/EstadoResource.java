package br.com.digitalzone.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.digitalzone.cursomc.domain.Cidade;
import br.com.digitalzone.cursomc.domain.Estado;
import br.com.digitalzone.cursomc.resources.dto.CidadeDTO;
import br.com.digitalzone.cursomc.resources.dto.EstadoDTO;
import br.com.digitalzone.cursomc.services.CidadeService;
import br.com.digitalzone.cursomc.services.EstadoService;

@RestController
@RequestMapping(value="/estados")
public class EstadoResource {
	
	@Autowired
	EstadoService service;
	
	@Autowired
	CidadeService cidadeService;

	@GetMapping()
	public ResponseEntity<List<EstadoDTO>> findAll() {
		
		List<Estado> list = service.findAll();
		List<EstadoDTO> listDto = list.stream().map(obj -> new EstadoDTO(obj)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDto);
		
	}
	
	
	@GetMapping(value = "{estadoId}/cidades")
	public ResponseEntity<List<CidadeDTO>> findCidade(@PathVariable Integer estadoId) {
		
		List<Cidade> list = cidadeService.findByEstado(estadoId);
		List<CidadeDTO> listDto = list.stream().map(obj -> new CidadeDTO(obj)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDto);
		
	}
}
