package br.com.digitalzone.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.digitalzone.cursomc.domain.Categoria;
import br.com.digitalzone.cursomc.repositories.CategoriaRepository;
import br.com.digitalzone.cursomc.services.exceptions.DataIntegrityException;
import br.com.digitalzone.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;

	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! id: " + id));
	}

	public Categoria insert(Categoria cat) {
		cat.setId(null);

		return repo.save(cat);
	}

	public Categoria update(Categoria cat) {
		find(cat.getId());
		return repo.save(cat);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
		repo.deleteById(id);
		}catch (org.springframework.dao.DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria associada a um produto!");
		}
	}
}
