package com.autobots.automanager.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entitades.Credencial;
import com.autobots.automanager.entitades.CredencialUsuarioSenha;
import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.repositorios.RepositorioUsuario;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = repositorioUsuario.findByNomeUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        CredencialUsuarioSenha credencial = usuario.getCredenciais().stream()
                .filter(c -> c instanceof CredencialUsuarioSenha)
                .map(CredencialUsuarioSenha.class::cast)
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("Credencial de usuário não encontrada"));

        Collection<GrantedAuthority> authorities = usuario.getPerfis().stream()
                .map(perfil -> new SimpleGrantedAuthority(perfil.name()))
                .collect(Collectors.toSet());

        return new User(credencial.getNomeUsuario(), credencial.getSenha(), authorities);
    }
}
