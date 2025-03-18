package com.br.controle_contas.modules.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "categorias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String descricao;

    private String tipo; // Indica se a categoria é de despesa ou receita

    @OneToMany(mappedBy = "categoria")
    private List<Contas> contas;
}
