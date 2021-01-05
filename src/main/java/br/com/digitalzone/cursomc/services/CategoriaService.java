package br.com.digitalzone.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.digitalzone.cursomc.domain.Categoria;
import br.com.digitalzone.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	public Optional<Categoria> buscar(Integer Id) {
		Optional<Categoria> obj = repo.findById(Id);
		
		return obj;
	}
}
