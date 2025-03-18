package com.br.controle_contas.modules.controllers;

import com.br.controle_contas.modules.entities.Parcela;
import com.br.controle_contas.modules.services.ParcelaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/parcelas")
public class ParcelaController {

    @Autowired
    private ParcelaService parcelaService;

    @GetMapping
    public ResponseEntity<List<Parcela>> findAll() {
        List<Parcela> parcelas = parcelaService.findAll();
        return ResponseEntity.ok(parcelas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Parcela> findById(@PathVariable Integer id) {
        Optional<Parcela> parcela = parcelaService.findById(id);
        return parcela.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Parcela> create(@RequestBody Parcela parcela, @RequestParam Integer contaId) {
        try {
            Parcela novaParcela = parcelaService.save(parcela, contaId);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaParcela);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Parcela> update(@PathVariable Integer id, @RequestBody Parcela parcela, @RequestParam Integer contaId) {
        Optional<Parcela> parcelaExistente = parcelaService.findById(id);
        if (parcelaExistente.isPresent()) {
            try {
                parcela.setId(id);
                Parcela parcelaAtualizada = parcelaService.save(parcela, contaId);
                return ResponseEntity.ok(parcelaAtualizada);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(null);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        parcelaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
