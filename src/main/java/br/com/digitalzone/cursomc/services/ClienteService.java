package br.com.digitalzone.cursomc.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.digitalzone.cursomc.domain.Cidade;
import br.com.digitalzone.cursomc.domain.Cliente;
import br.com.digitalzone.cursomc.domain.Endereco;
import br.com.digitalzone.cursomc.domain.enums.Perfil;
import br.com.digitalzone.cursomc.domain.enums.tipocliente.TipoCliente;
import br.com.digitalzone.cursomc.repositories.CidadeRepository;
import br.com.digitalzone.cursomc.repositories.ClienteRepository;
import br.com.digitalzone.cursomc.repositories.EnderecoRepository;
import br.com.digitalzone.cursomc.resources.dto.ClienteDTO;
import br.com.digitalzone.cursomc.resources.dto.ClienteNewDTO;
import br.com.digitalzone.cursomc.security.UserSS;
import br.com.digitalzone.cursomc.services.exceptions.AuthorizationException;
import br.com.digitalzone.cursomc.services.exceptions.DataIntegrityException;
import br.com.digitalzone.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	@Autowired
	private CidadeRepository cidadeRepo;
	@Autowired
	private EnderecoRepository enderecoRepo;
	@Autowired
	BCryptPasswordEncoder pEnc;
	@Autowired
	private S3Service s3Service;

	public Cliente find(Integer id) {
		UserSS user = UserService.authenticated();
		if(user==null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Optional<Cliente> obj = repo.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado! id: " + id));
	}
	
	@Transactional
	public Cliente insert(Cliente cli) {
		cli.setId(null);

		enderecoRepo.saveAll(cli.getEnderecos());
		
		return repo.save(cli);
	}

	public Cliente update(Cliente cli) {
		Cliente newCli = find(cli.getId());
		
		updateData(newCli, cli);
		
		return repo.save(newCli);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (org.springframework.dao.DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionadas!");
		}
	}

	public List<Cliente> findAll() {
		return repo.findAll();
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		return repo.findAll(pageRequest);
	}

	public Cliente fromDTO(ClienteDTO cliDto) {
		return new Cliente(cliDto.getId(),cliDto.getNome(),cliDto.getEmail(), null, null,null);
	}
	
	public Cliente fromDTO(ClienteNewDTO cliDto) {
		Cliente cli = new Cliente(null,cliDto.getNome(),cliDto.getEmail(),
				cliDto.getCpfOuCnpj(),TipoCliente.toEnum(cliDto.getTipo()), pEnc.encode(cliDto.getSenha()));
		
		Cidade cid = cidadeRepo.findById(cliDto.getCidadeId()).orElseThrow(() -> new ObjectNotFoundException("Cidade não encontrada! id: "
				+ cliDto.getCidadeId()));
		
		Endereco end = new Endereco(null, cliDto.getLogradouro(),cliDto.getNumero(),
				cliDto.getComplemento(),cliDto.getBairro(),cliDto.getCep(),cli, cid);
		
		cli.getEnderecos().add(end);
		cli.getTelefones().add(cliDto.getTelefone1());
		
		if(cliDto.getTelefone2() != null) {
			cli.getTelefones().add(cliDto.getTelefone2());
		}
		if(cliDto.getTelefone3() != null) {
			cli.getTelefones().add(cliDto.getTelefone3());
		}
		
		return cli;
		
	}
	
	private void updateData(Cliente newCli,Cliente cli) {
		newCli.setNome(cli.getNome());
		newCli.setEmail(cli.getEmail());
	}
	
	public URI uploadProfilePicture(MultipartFile file) {
		
		UserSS user = UserService.authenticated();
		if(user==null) {
			throw new AuthorizationException("Acesso negado");
		}
		
		URI uri = s3Service.uploadFile(file);
		
		Cliente cli = repo.findById(user.getId()).orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado! id: " + user.getId()));
		cli.setImageUrl(uri.toString());
		repo.save(cli);
		
		return uri;
	}
}
