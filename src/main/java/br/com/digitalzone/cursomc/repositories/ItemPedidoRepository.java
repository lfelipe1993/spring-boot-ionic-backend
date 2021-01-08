package br.com.digitalzone.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.digitalzone.cursomc.domain.ItemPedido;
import br.com.digitalzone.cursomc.domain.Produto;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {

	
}
