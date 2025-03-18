package com.br.controle_contas.modules.services;

import com.br.controle_contas.modules.entities.Contas;
import com.br.controle_contas.modules.entities.Parcela;
import com.br.controle_contas.modules.repo.ContasRepository;
import com.br.controle_contas.modules.repo.ParcelaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ParcelaService {

    @Autowired
    private ParcelaRepository parcelaRepository;

    @Autowired
    private ContasRepository contasRepository;

    public List<Parcela> findAll() {
        return parcelaRepository.findAll();
    }

    public Optional<Parcela> findById(Integer id) {
        return parcelaRepository.findById(id);
    }

    public Parcela save(Parcela parcela, Integer contaId) {
        Optional<Contas> contasOptional = contasRepository.findById(contaId);

        if (contasOptional.isEmpty()) {
            throw new IllegalArgumentException("Conta não encontrada com o ID: " + contaId);
        }

        Contas conta = contasOptional.get();
        parcela.setConta(conta);

        return parcelaRepository.save(parcela);
    }

    public void delete(Integer id) {
        parcelaRepository.deleteById(id);
    }

    public List<Parcela> findByDataVencimentoBetween(LocalDate dataInicio, LocalDate dataFim) {
        return parcelaRepository.findByDataVencimentoBetween(dataInicio, dataFim);
    }

    public List<Parcela> findByStatus(String status) {
        return parcelaRepository.findByStatus(status);
    }

    public List<Parcela> findByConta(Contas conta) {
        return parcelaRepository.findByConta(conta);
    }
}
