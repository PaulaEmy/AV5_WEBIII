package com.autobots.automanager.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.autobots.automanager.entitades.Usuario;

public interface RepositorioUsuario extends JpaRepository<Usuario, Long> {

	@Query("select u from Usuario u join u.credenciais c where type(c) = CredencialUsuarioSenha and c.nomeUsuario = :nomeUsuario")
	Optional<Usuario> findByNomeUsuario(@Param("nomeUsuario") String nomeUsuario);

	//public Usuario findByNome(String nome);
}