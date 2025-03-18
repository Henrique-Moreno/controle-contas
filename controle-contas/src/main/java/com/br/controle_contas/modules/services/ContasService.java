package com.br.controle_contas.modules.services;

import com.br.controle_contas.exceptions.NotFoundException;
import com.br.controle_contas.modules.dtos.ContaDTO;
import com.br.controle_contas.modules.dtos.ContaDetalhadaDTO;
import com.br.controle_contas.modules.dtos.DtoConverter;
import com.br.controle_contas.modules.entities.Categoria;
import com.br.controle_contas.modules.entities.Contas;
import com.br.controle_contas.modules.entities.Usuarios;
import com.br.controle_contas.modules.repo.ContasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContasService {

    private final ContasRepository contasRepository;
    private final UsuarioService usuarioService;
    private final CategoriaService categoriaService;

    @Autowired
    public ContasService(ContasRepository contasRepository,
                         UsuarioService usuarioService,
                         CategoriaService categoriaService) {
        this.contasRepository = contasRepository;
        this.usuarioService = usuarioService;
        this.categoriaService = categoriaService;
    }

    public List<Contas> findAll() {
        return contasRepository.findAll();
    }

    public List<ContaDTO> findAllDTO() {
        List<Contas> contas = contasRepository.findAll();
        return contas.stream()
                .map(DtoConverter::toContaDTO)
                .collect(Collectors.toList());
    }

    public List<ContaDetalhadaDTO> findAllDetalhadasDTO() {
        List<Contas> contas = contasRepository.findAll();
        return contas.stream()
                .map(ContaDetalhadaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public Contas findById(Integer id) {
        return contasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Conta não encontrada"));
    }

    public ContaDTO findByIdDTO(Integer id) {
        Contas conta = findById(id);
        return DtoConverter.toContaDTO(conta);
    }

    public ContaDetalhadaDTO findByIdDetalhadaDTO(Integer id) {
        Contas conta = findById(id);
        return ContaDetalhadaDTO.fromEntity(conta);
    }

    public ContaDTO createConta(ContaDTO contaDTO) {
        Contas conta = new Contas();
        convertDtoToEntity(contaDTO, conta);
        Contas savedConta = contasRepository.save(conta);
        return DtoConverter.toContaDTO(savedConta);
    }

    public ContaDTO updateConta(Integer id, ContaDTO contaDTO) {
        Contas conta = findById(id);
        convertDtoToEntity(contaDTO, conta);
        Contas updatedConta = contasRepository.save(conta);
        return DtoConverter.toContaDTO(updatedConta);
    }

    public void deleteConta(Integer id) {
        Contas conta = findById(id);
        contasRepository.delete(conta);
    }

    private void convertDtoToEntity(ContaDTO dto, Contas entity) {
        entity.setDescricao(dto.getDescricao());
        entity.setValor(dto.getValor());
        entity.setDataVencimento(dto.getDataVencimento());
        entity.setDataPagamento(dto.getDataPagamento());
        entity.setTipoConta(dto.getTipoConta());
        entity.setStatus(dto.getStatus());

        // Busca e vincula usuário
        Usuarios usuario = usuarioService.findById(dto.getUsuarioId());
        entity.setUsuario(usuario);

        // Busca e vincula categoria
        Categoria categoria = categoriaService.findById(dto.getCategoriaId());
        entity.setCategoria(categoria);
    }
}
