package br.com.digitalzone.cursomc.domain.enums.tipocliente;

public enum TipoCliente {

	PESSOAFISICA(1, "Pessoa Física", CpfGroup.class), PESSOAJURIDICA(2, "Pessoa Jurídica", CnpjGroup.class);

	private int cod;
	private String descricao;
	private Class<?> grupo;

	private TipoCliente(int cod, String descricao, Class<?> grupo) {
		this.cod = cod;
		this.descricao = descricao;
		this.grupo = grupo;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public Class<?> getGrupo() {
		return grupo;
	}

	public static TipoCliente toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}

		for (TipoCliente x : TipoCliente.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}

		throw new IllegalArgumentException("Id inválido: " + cod);
	}
}


