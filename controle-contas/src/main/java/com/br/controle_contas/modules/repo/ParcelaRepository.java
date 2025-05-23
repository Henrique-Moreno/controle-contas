package com.br.controle_contas.modules.repo;

import com.br.controle_contas.modules.entities.Contas;
import com.br.controle_contas.modules.entities.Parcela;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ParcelaRepository extends JpaRepository<Parcela, Integer> {

    List<Parcela> findByDataVencimentoBetween(LocalDate dataInicio, LocalDate dataFim);
    List<Parcela> findByStatus(String status);

    List<Parcela> findByConta(Contas conta);
}
