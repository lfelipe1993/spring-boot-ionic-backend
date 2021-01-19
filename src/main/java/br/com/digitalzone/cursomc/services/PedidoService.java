package br.com.digitalzone.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.digitalzone.cursomc.domain.ItemPedido;
import br.com.digitalzone.cursomc.domain.PagamentoComBoleto;
import br.com.digitalzone.cursomc.domain.Pedido;
import br.com.digitalzone.cursomc.domain.enums.EstadoPagamento;
import br.com.digitalzone.cursomc.repositories.ItemPedidoRepository;
import br.com.digitalzone.cursomc.repositories.PagamentoRepository;
import br.com.digitalzone.cursomc.repositories.PedidoRepository;
import br.com.digitalzone.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private BoletoService boletoService;

	@Autowired
	private ProdutoService produtoService;
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException("Pedido não encontrado! id: "
																+ id));
	}
	
	public Pedido insert(Pedido ped) {
		ped.setId(null);
		ped.setInstante(new Date());
		ped.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		ped.getPagamento().setPedido(ped);
		
		if(ped.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pgto = (PagamentoComBoleto) ped.getPagamento();
			boletoService.preencherPagamentoComBoleto(pgto, ped.getInstante());
		}
		
		
		ped = repo.save(ped);
		pagamentoRepository.save(ped.getPagamento());
		
		for(ItemPedido ip: ped.getItens()) {
			ip.setDesconto(0.0);
			ip.setPreco(produtoService.find(ip.getProduto().getId()).getPreco());
			ip.setPedido(ped);
		}
		
		itemPedidoRepository.saveAll(ped.getItens());
		
		return ped;
	}
}
