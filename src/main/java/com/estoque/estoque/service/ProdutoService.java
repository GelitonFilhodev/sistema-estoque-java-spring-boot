package com.estoque.estoque.service;

import com.estoque.estoque.model.Produto;
import com.estoque.estoque.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final ProdutoRepository repository;

    public List<Produto> listar(){
        return repository.findAll();
    }

    public Produto buscarId(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não Encontrado!!"));
    }

    public Produto salvar(Produto produto){
        return repository.save(produto);
    }

    public Produto atualizar(Long id, Produto produto){
        Produto existente = buscarId(id);
        existente.setNome(produto.getNome());
        existente.setDescricao(produto.getDescricao());
        existente.setPreco(produto.getPreco());
        existente.setQuantidade(produto.getQuantidade());
        return repository.save(existente);
    }

    public void deletar(Long id){
        buscarId(id);
        repository.deleteById(id);
    }
}
