package br.com.ajasoftware.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.ajasoftware.cdi.CDIServiceLocator;
import br.com.ajasoftware.model.usuario.Usuario;
import br.com.ajasoftware.repository.usuarios.UsuariosRepository;
import br.com.ajasoftware.service.msg.NegocioException;
import br.com.ajasoftware.util.Trata;

public class AppUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		UsuariosRepository usuarios = CDIServiceLocator.getBean(UsuariosRepository.class);
		Usuario usuario=null;
		try {
			usuario = usuarios.porLogin(login);
		} catch (NegocioException e) {
			// TODO Auto-generated catch block
			throw new UsernameNotFoundException(e.getMessage());
		}
		
		UsuarioSistema user = null;
		
		if (usuario != null) {
			user = new UsuarioSistema(usuario, getGrupos(usuario));
		}
		else{
			throw new UsernameNotFoundException("Usuário não encontrado.");
		}
		
		return user;
	}

	private Collection<? extends GrantedAuthority> getGrupos(Usuario usuario) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();

		if(Trata.Boolean(usuario.getAdministrador())) {
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMINISTRADORES"));
		}
		else {
			authorities.add(new SimpleGrantedAuthority("ROLE_"+usuario.getCodGrupoAcesso().getNome()));
		}
		
		return authorities;
	}

}
