package com.algaworks.ecommerce.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToMany;
import javax.persistence.ParameterMode;
import javax.persistence.PostLoad;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.br.CPF;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NamedStoredProcedureQuery(name = "compraram_acima_media", procedureName = "compraram_acima_media", // name = pode ser qualquer nome, procedureName = tem que ser o nome da procedure
	parameters = {
			@StoredProcedureParameter(name = "ano", type = Integer.class, mode = ParameterMode.IN)
	},
	resultClasses = Cliente.class
)
@SecondaryTable(name = "cliente_detalhe",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "cliente_id"),
        foreignKey = @ForeignKey(name = "fk_cliente_detalhe_cliente")) // permite trazer para uma entidade valores que est�o em duas tabelas
@Entity
@Table(name = "cliente",
        uniqueConstraints = { @UniqueConstraint(name = "unq_cpf", columnNames = { "cpf" }) }, // anota uma coluna como unique na tabela
        indexes = { @Index(name = "idx_nome", columnList = "nome") }) // para organizar uma coluna da tabela para buscar de uma forma mais agil
public class Cliente extends EntidadeBaseInteger {

    @NotBlank
	@Column(length = 100, nullable = false)
    private String nome;
    
    @Transient // essa marca��o faz com que o JPA ignore essa propriedade
	private String primeiroNome;

    @CPF
    @NotBlank
    //@Pattern(regexp = "(^\\d{3}\\x2E\\d{3}\\x2E\\d{3}\\x2D\\d{2}$)")
    @Column(length = 14, nullable = false)
    private String cpf;

    @NotEmpty
    @ElementCollection
    @CollectionTable(name = "cliente_contato",
            joinColumns = @JoinColumn(name = "cliente_id", nullable = false,
                    foreignKey = @ForeignKey(name = "fk_cliente_contato_cliente")))
    @MapKeyColumn(name = "tipo")
    @Column(name = "descricao")
    private Map<String, String> contatos;

    @NotNull
    @Column(table = "cliente_detalhe", length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private SexoCliente sexo;

    @Past
    @Column(name = "data_nascimento", table = "cliente_detalhe")
    private LocalDate dataNascimento;

    // esse mapeamento n�o � necess�rio. j� que na tabela de pedidos esta mapeado
 	// como manytoone para cliente
    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos;

    @PostLoad
    public void configurarPrimeiroNome(){
        if (nome != null && !nome.isBlank()) {
            int index = nome.indexOf(" ");
            if (index > -1) {
                primeiroNome = nome.substring(0, index);
            }
        }
    }
}

/*@Getter
@Setter
@Entity
@SecondaryTable(name = "cliente_detalhe", 
		pkJoinColumns = @PrimaryKeyJoinColumn(name = "cliente_id"),
		foreignKey = @ForeignKey(name = "fk_cliente_detalhe_cliente")) // permite trazer para uma entidade valores que est�o em duas tabelas
@Table(name = "cliente", 
		uniqueConstraints = { @UniqueConstraint(name = "unq_cpf", columnNames = { "cpf" }) }, // anota uma coluna como unique na tabela
		indexes = { @Index(name = "idx_nome", columnList = "nome") }) // para organizar uma coluna da tabela para buscar de uma forma mais agil
public class Cliente extends EntidadeBaseInteger {
	
	@Column(length = 100, nullable = false)
	private String nome;
	
	@Column(length = 14, nullable = false)
	private String cpf;
	
	@ElementCollection
	@CollectionTable(name = "cliente_contato",
			joinColumns = @JoinColumn(name = "cliente_id"),
			foreignKey = @ForeignKey(name = "fk_cliente_contato_cliente"))
	@MapKeyColumn(name = "tipo")
	@Column(name = "descricao")
	private Map<String, String> contatos;
	
	@Transient // essa marca��o faz com que o JPA ignore essa propriedade
	private String primeiroNome;
	
	@Column(table = "cliente_detalhe", length = 9, nullable = false)
	@Enumerated(EnumType.STRING)
	private SexoCliente sexo;
	
	@Column(name = "data_nascimento", table = "cliente_detalhe_cliente")
	private LocalDate dataNascimento;

	// esse mapeamento n�o � necess�rio. j� que na tabela de pedidos esta mapeado
	// como manytoone para cliente
	@OneToMany(mappedBy = "cliente")
	private List<Pedido> pedidos;
	
	@PostLoad
	public void configurarPrimeiroNome() {
		if (nome != null && !nome.isBlank()) {
			int index = nome.indexOf(" ");
			if (index > -1) {
				this.primeiroNome = nome.substring(0, index);
			}
		}
	}
}*/
