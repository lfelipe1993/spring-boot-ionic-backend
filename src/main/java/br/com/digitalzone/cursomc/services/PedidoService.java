package br.com.digitalzone.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.digitalzone.cursomc.domain.Cliente;
import br.com.digitalzone.cursomc.domain.ItemPedido;
import br.com.digitalzone.cursomc.domain.PagamentoComBoleto;
import br.com.digitalzone.cursomc.domain.Pedido;
import br.com.digitalzone.cursomc.domain.enums.EstadoPagamento;
import br.com.digitalzone.cursomc.repositories.ItemPedidoRepository;
import br.com.digitalzone.cursomc.repositories.PagamentoRepository;
import br.com.digitalzone.cursomc.repositories.PedidoRepository;
import br.com.digitalzone.cursomc.security.UserSS;
import br.com.digitalzone.cursomc.services.exceptions.AuthorizationException;
import br.com.digitalzone.cursomc.services.exceptions.ObjectNotFoundException;
import br.com.digitalzone.cursomc.services.mail.EmailService;

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
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException("Pedido não encontrado! id: "
																+ id));
	}
	
	public Pedido insert(Pedido ped) {
		ped.setId(null);
		ped.setInstante(new Date());
		ped.setCliente(clienteService.find(ped.getCliente().getId()));
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
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(ped);
		}
		
		itemPedidoRepository.saveAll(ped.getItens());
		
		emailService.sendOrderConfirmationHtmlEmail(ped);
		return ped;
	}
	
	public Page<Pedido> findPage(Integer page,Integer linesPerPage, String orderBy,
			String direction){
		
		UserSS user = UserService.authenticated();
		
		if(user==null) {
			throw new AuthorizationException("Acesso negado");
		}
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage,Direction.valueOf(direction),orderBy);
		Cliente cliente = clienteService.find(user.getId());
		
		return repo.findByCliente(cliente, pageRequest);
	}
}
