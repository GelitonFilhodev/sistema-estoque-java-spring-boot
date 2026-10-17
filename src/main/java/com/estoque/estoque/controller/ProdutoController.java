package com.estoque.estoque.controller;

import com.estoque.estoque.model.Produto;
import com.estoque.estoque.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {
    private final ProdutoService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Produto> listar(){
        return service.listar();
    }

    @GetMapping(value = {"/id"},
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Produto buscarId(@PathVariable("id") Long id){
        return service.buscarId(id);
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Produto criar(@RequestBody Produto produto){
        return service.salvar(produto);
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Produto atualiza(@RequestBody Produto produto) {
        return service.atualizar(produto.getId(), produto);
    }

    @DeleteMapping(value = {"/id"})
    public void deleta(@PathVariable("id") Long id){
        service.deletar(id);
    }
}
