package br.com.digitalzone.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.digitalzone.cursomc.domain.Categoria;
import br.com.digitalzone.cursomc.domain.Cidade;
import br.com.digitalzone.cursomc.domain.Cliente;
import br.com.digitalzone.cursomc.domain.Endereco;
import br.com.digitalzone.cursomc.domain.Estado;
import br.com.digitalzone.cursomc.domain.ItemPedido;
import br.com.digitalzone.cursomc.domain.Pagamento;
import br.com.digitalzone.cursomc.domain.PagamentoComBoleto;
import br.com.digitalzone.cursomc.domain.PagamentoComCartao;
import br.com.digitalzone.cursomc.domain.Pedido;
import br.com.digitalzone.cursomc.domain.Produto;
import br.com.digitalzone.cursomc.domain.enums.EstadoPagamento;
import br.com.digitalzone.cursomc.domain.enums.tipocliente.TipoCliente;
import br.com.digitalzone.cursomc.repositories.CategoriaRepository;
import br.com.digitalzone.cursomc.repositories.CidadeRepository;
import br.com.digitalzone.cursomc.repositories.ClienteRepository;
import br.com.digitalzone.cursomc.repositories.EnderecoRepository;
import br.com.digitalzone.cursomc.repositories.EstadoRepository;
import br.com.digitalzone.cursomc.repositories.ItemPedidoRepository;
import br.com.digitalzone.cursomc.repositories.PagamentoRepository;
import br.com.digitalzone.cursomc.repositories.PedidoRepository;
import br.com.digitalzone.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		
	}

}
