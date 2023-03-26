package com.club.sanmartin.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.club.sanmartin.Entidades.Foto;
import com.club.sanmartin.Entidades.Role;
import com.club.sanmartin.Entidades.Socio;
import com.club.sanmartin.Entidades.Taller;
import com.club.sanmartin.ErrorService.ErrorServicio;
import com.club.sanmartin.Repository.RoleRepository;
import com.club.sanmartin.Repository.SocioRepository;


@Service
public class SocioService implements UserDetailsService {
	@Autowired
	private SocioRepository repositorio;

	@Autowired 
	private RoleRepository rolerepository;
	

	@Autowired
	private FotoService fService;

	@Transactional
	public void create( @RequestParam String nombre, @RequestParam String apellido,
			@RequestParam String dni, @RequestParam String clave, @RequestParam String mail,
			@RequestParam Integer telefono, @RequestParam List<Taller> taller,
			
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaNacimiento, @RequestParam String direccion,
			@RequestParam String sexo, MultipartFile foto, @RequestParam String clave2) throws ErrorServicio {

		validate(nombre, apellido, dni, clave, mail, telefono, taller, fechaNacimiento, direccion,
				sexo, foto, clave2);

		Socio socio = new Socio();
		boolean bool = validateMail(mail, socio);
		boolean bool2 = validateDni(dni, socio);
		if (bool && bool2) {

			socio.setApellido(apellido);
			socio.setNombre(nombre);
			socio.setDni(dni);
			String encriptada = new BCryptPasswordEncoder().encode(clave);
			socio.setClave(encriptada);
			socio.setMail(mail);
			socio.setTelefono(telefono);
			socio.setTaller(taller);
			
			socio.setFechaNacimiento(fechaNacimiento);
			socio.setDireccion(direccion);
			socio.setSexo(sexo);
			Foto imagen = fService.crear(foto);
			socio.setFoto(imagen);
			socio.setNombreUsuario(dni);
			Role roles = rolerepository.searchByName("ROLE_ADMIN").get();
	        socio.setRoles(Collections.singleton(roles));

	        repositorio.save(socio);
		} else {
			if (!bool) {
				if (!bool2) {
					throw new ErrorServicio("El dni y el mail que esta ingresando ya existen");
				} else {
					throw new ErrorServicio("el mail ya existe");
				}
			}
			if (!bool2) {
				throw new ErrorServicio("el dni ya existe");

			}
		}
	}
	
	
	@Transactional
	public void edit(ModelMap model, String id, String nombre, String apellido, String dni, String clave,
			String mail, Integer telefono, List<Taller> taller, Integer numeroAsociado,
			@DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaNacimiento, String direccion, String sexo,
			MultipartFile foto, String clave2) throws ErrorServicio {
		{

			validate(nombre, apellido, dni, clave, mail, telefono, taller, fechaNacimiento,
					direccion, sexo, foto, clave2);

			Optional<Socio> rta = repositorio.findById(id);
			if (rta.isPresent()) {

				Socio socio = rta.get();
				boolean bool = validateMail(mail, socio);
				boolean bool2 = validateDni(dni, socio);
				if (bool && bool2) {

					socio.setApellido(apellido);
					socio.setNombre(nombre);
					socio.setDni(dni);
					
					socio.setClave(clave);
					socio.setMail(mail);
					socio.setTelefono(telefono);
					socio.setTaller(taller);
					
					socio.setFechaNacimiento(fechaNacimiento);
					socio.setDireccion(direccion);
					socio.setSexo(sexo);
					socio.setNombreUsuario(dni);
					Foto imagen = fService.editar(socio.getFoto().getId(), foto);
					socio.setFoto(imagen);

					repositorio.save(socio);
				} else {
					if (!bool) {
						if (!bool2) {
							throw new ErrorServicio("El dni y el mail que esta ingresando ya existen");
						} else {
							throw new ErrorServicio("el mail ya existe");
						}
					}
					if (!bool2) {
						throw new ErrorServicio("el dni ya existe");
					}
				}

			} else {

				throw new ErrorServicio("No se pudo encontrar el usuario");
			}

		}
	}

	public Socio searchById(String id) throws ErrorServicio {

		if (id == null || id.isEmpty()) {
			throw new ErrorServicio("El parametro de id a buscar no puede estar vacio");
		}

		Optional<Socio> rta = repositorio.findById(id);
		if (rta.isPresent()) {

			Socio socio = rta.get();
			return socio;
		} else {

			throw new ErrorServicio("No se pudo encontrar al usuario con el id indicado ");

		}

	}

	

	@Transactional(readOnly = true)
	public List<Socio> showAll() throws ErrorServicio {

		List<Socio> lista = repositorio.findAll();

		if (lista.isEmpty()) {

			throw new ErrorServicio("la lista esta vacia");

		} else {

			return lista;

		}

	}

	@Transactional(readOnly = true)
	public List<Socio> searchByName(String nombre, String apellido) throws ErrorServicio {

		if (nombre.isEmpty() || nombre == null) {
			throw new ErrorServicio(" el nombre no puede estar vacio");
		}

		if (apellido.isEmpty() || apellido == null) {
			throw new ErrorServicio(" el apellido no puede estar vacio");
		}
		List<Socio> lista = repositorio.buscarNombre(nombre, apellido);

		if (lista.isEmpty()) {
			throw new ErrorServicio("no se encontro usuario con ese nombre y apellido");

		} else {

			return lista;
		}

	}

	@Transactional(readOnly = true)
	public Socio searchBydni(String dni) throws ErrorServicio {

		if (dni == null) {
			throw new ErrorServicio("el dni no puede estar vacio  ");
		} else if (dni.toString().length() < 11) {
			throw new ErrorServicio("el formato es incorrecto");

		}

		List<Socio> lista = repositorio.buscarDni(dni);

		if (lista==null) {
			throw new ErrorServicio("no existe socio con ese dni");
		} else {
			return lista.get(0);
		}
	}

	@Transactional(readOnly = true)
	public List<Socio> searchByTaller(String nombre) throws ErrorServicio {

		if (nombre.isEmpty() || nombre == null) {
			throw new ErrorServicio(" el nombre no puede estar vacio");

		}
		List<Socio> lista = repositorio.buscarTaller(nombre);

		if (lista.isEmpty()) {
			throw new ErrorServicio("no se encontro ningun socio perteneciente a ese taller");

		} else {
			return lista;
		}

	}

	public void validate(String nombre, String apellido, String dni, String clave, String mail, Integer telefono,
			List<Taller> taller, Date fechaNacimiento, String direccion, String sexo,
			MultipartFile foto, String clave2) throws ErrorServicio {

		if (nombre.isEmpty()) {
			throw new ErrorServicio(" el nombre no puede estar vacio");
		} else if (apellido.isEmpty()) {
			throw new ErrorServicio(" el apellido no puede estar vacio");
		} else {
		}

		if (clave.isEmpty()) {
			throw new ErrorServicio(" la clave  no puede estar vacia");

		} else {
		}
		if (clave2.isEmpty()) {
			throw new ErrorServicio(" la clave  no puede estar vacia");

		} else {
		}

		if (!clave.equals(clave2)) {
			throw new ErrorServicio("las claves no pueden ser distintas");
		}

		if (mail.isEmpty()) {
			throw new ErrorServicio(" el mail no puede estar vacio");

		} else {
		}

		if (direccion.isEmpty()) {
			throw new ErrorServicio(" la clave  no puede estar vacia");

		} else {
		}

		if (telefono == null) {
			throw new ErrorServicio("el tefono no puede estar vacio o ");

		} else {
		}
		if (dni == null) {
			throw new ErrorServicio("el dni no puede estar vacio o ");
		} else if (dni.toString().length() < 1) {
			throw new ErrorServicio("el dni no puede ser menor de 11 digitos ");

		}

		if (foto == null) {
			throw new ErrorServicio("la foto  no puede estar vacia ");

		}
		else if (taller.isEmpty()) {
			throw new ErrorServicio(" la seccion taller  no puede estar vacia");

		}}
		
	
		
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
		Socio usuario = repositorio.buscarDni(username).get(0);
		if(usuario!=null){
			List<GrantedAuthority> permisos = new ArrayList<>();

			GrantedAuthority permiso1 = new SimpleGrantedAuthority("ROLE_SOCIO");
			permisos.add(permiso1);

			if (usuario.getNombre().equalsIgnoreCase("ADMIN")) {
				GrantedAuthority permiso2 = new SimpleGrantedAuthority("ROLE_ADMIN");
				permisos.add(permiso2);

			}

			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

			HttpSession session = attr.getRequest().getSession(true);
			session.setAttribute("usuariosession", usuario);

			User user = new User(usuario.getDni().toString(), usuario.getClave(), permisos);
			return user;
		} else {
			return null;
		}

	}

		



	public boolean validateMail(String mail, Socio socio) {
		if(mail.equals(socio.getMail())){
			return true;
		} else {
			List<Socio> lista = repositorio.findAll();

			if (!lista.isEmpty()) {
				List<String> listaMails = new ArrayList<String>();

				for (Socio aux : lista) {
					listaMails.add(aux.getMail());
				}

				if (listaMails.contains(mail)) {
					return false;

				} else {
					return true;
				}
			}

		}
		return true;

	}

	public boolean validateDni(String dni, Socio socio) {

		if (dni.equals(socio.getDni())) {
			return true;
		} else {
			List<Socio> lista = repositorio.findAll();

			if (!lista.isEmpty()) {
				List<String> listaDni = new ArrayList<String>();

				for (Socio aux : lista) {
					listaDni.add(aux.getDni());
				}

				if (listaDni.contains(dni)) {
					return false;

				} else {
					return true;
				}
			}

		}
		return true;

	}
	@Transactional
	public void delete(String id) throws ErrorServicio {

		if (id.isEmpty() || id == null) {
			throw new ErrorServicio(" el id del usuario a modificar no puede estar vacio");
		}

		Optional<Socio> rta = repositorio.findById(id);
		if (rta.isPresent()) {

			Socio socio = rta.get();
			repositorio.delete(socio);
		} else {
			throw new ErrorServicio("No se pudo encontrar el usuario");
		}

	}
	
}
