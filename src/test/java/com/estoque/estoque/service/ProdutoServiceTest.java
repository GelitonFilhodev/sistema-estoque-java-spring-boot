package com.estoque.estoque.service;

import com.estoque.estoque.model.Produto;
import com.estoque.estoque.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

    @Mock
    ProdutoRepository repository;

    @InjectMocks
    ProdutoService service;

    @Test
    public void deveSalvarProduto(){
        Produto produto = new Produto();
        produto.setNome("Teclado");

        when(repository.save(produto)).thenReturn(produto);

        Produto resultado = service.salvar(produto);

        assertEquals("Teclado", resultado.getNome());
    }

    @Test
    public void testarErro(){
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.buscarId(99L));
    }

    @Test
    public void deveAtualizarProduto(){
        Produto existente = new Produto();
        existente.setId(1L);
        existente.setNome("Teclado");

        Produto novo = new Produto();
        novo.setNome("Teclado RGB");

        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(repository.save(existente)).thenReturn((existente));

        Produto resultado = service.atualizar(1L, novo);
        assertEquals("Teclado RGB", resultado.getNome());
    }

    @Test
    public void deveDeletarProduto(){
        when(repository.findById(1L)).thenReturn(Optional.of(new Produto()));

        service.deletar(1L);

        verify(repository).deleteById(1L);
    }
}
