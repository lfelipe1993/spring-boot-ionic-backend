package br.com.digitalzone.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import br.com.digitalzone.cursomc.domain.Cliente;
import br.com.digitalzone.cursomc.domain.enums.tipocliente.CnpjGroup;
import br.com.digitalzone.cursomc.domain.enums.tipocliente.CpfGroup;
import br.com.digitalzone.cursomc.domain.enums.tipocliente.TipoCliente;
import br.com.digitalzone.cursomc.resources.dto.ClienteNewDTO;

public class ClienteGroupSequenceProvider implements DefaultGroupSequenceProvider<ClienteNewDTO> {

	@Override
	public List<Class<?>> getValidationGroups(ClienteNewDTO cliente) {
		@SuppressWarnings("unchecked")
		List<Class<?>> groups = new ArrayList();
		groups.add(ClienteNewDTO.class);
		if (isPessoaSelecionada(cliente)) {
			
			if(cliente.getTipo() == TipoCliente.PESSOAFISICA.getCod()){
				groups.add(CpfGroup.class);
			}else if(cliente.getTipo() == TipoCliente.PESSOAJURIDICA.getCod()){
				groups.add(CnpjGroup.class);
			}
			
			

		}

		return groups;
	}

	protected boolean isPessoaSelecionada(ClienteNewDTO cliente) {
		return cliente != null && cliente.getTipo() != null;

	}

}