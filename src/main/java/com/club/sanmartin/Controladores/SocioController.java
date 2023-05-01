package com.club.sanmartin.Controladores;


import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import com.club.sanmartin.Entidades.Role;
import com.club.sanmartin.Entidades.Socio;
import com.club.sanmartin.Entidades.Taller;
import com.club.sanmartin.ErrorService.ErrorServicio;
import com.club.sanmartin.Repository.RoleRepository;
import com.club.sanmartin.Repository.SocioRepository;
import com.club.sanmartin.Service.SocioService;
import com.club.sanmartin.Service.TallerService;
import com.sun.tools.javac.code.Attribute.Error;



@Controller
@RequestMapping("socio")
public class SocioController {
	@Autowired
	private SocioService service;
	 
	@Autowired
	private TallerService tallerService;
	
	@Autowired
    private AuthenticationManager authenticationManager;
	
	@Autowired
	private SocioRepository sRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@GetMapping("/talleres")
	public String talleresView(){
		return "talleres.html";
	}
	@GetMapping("/login")
	public String loginView(){
		return "login.html";
	}		
	@PostMapping("/login")
    public String authenticateUser(ModelMap model, @RequestParam String dni, @RequestParam String clave){
			
		
			service.loadUserByUsername(dni);
	
		return "inicio.html";
	}		
    @RequestMapping(value = "/registrar", method = RequestMethod.GET)
	public String registerView(ModelMap model) {

		List<Taller> taller;
		try {
			taller = tallerService.showAll();
			model.addAttribute("taller", taller);

		} catch (ErrorServicio e) {
			model.addAttribute("error", e.getMessage());
			return "/registrar.html";
		}

		return "/registrar.html";
	}
	@PostMapping ("/registrar")
	public String registrar(ModelMap model, @RequestParam String nombre, @RequestParam String apellido,
			@RequestParam String dni, @RequestParam String clave, @RequestParam String mail,
			@RequestParam String telefono, @RequestParam List<Taller> taller,
			
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaNacimiento, @RequestParam String direccion,
			@RequestParam String sexo, MultipartFile foto, @RequestParam String clave2) {

		try {
			service.create(nombre, apellido, dni, clave, mail, telefono, taller, fechaNacimiento, direccion,
					sexo, foto, clave2);

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			return "registrar.html";

		}

		return "talleres.html";
	}

	@GetMapping("/editar/{id}")
	public String modificar(ModelMap model, @PathVariable String id) {

		try {

			List<Taller> taller = tallerService.showAll();
			model.addAttribute("taller", taller);
			// List<Resultado> resultado =rService.showAll();
			// model.addAttribute("resultado", resultado);

			Socio socio = service.searchById(id);
			model.addAttribute("socio", socio);
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "redirect:/socio/inicio/";
	}

	@PostMapping("/editar")
	public String edit(ModelMap model, String id, String nombre, String apellido, String dni, String password,
			String mail, String telefono, List<Taller> taller, Integer numeroAsociado,
			@DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaNacimiento, String direccion, String sexo,
			MultipartFile foto, String clave2) {

		try {
			service.edit(model, id, nombre, apellido, dni, password, mail, telefono, taller, numeroAsociado, fechaNacimiento,
					direccion, sexo, foto, clave2);

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			return "editar.html";
		}
		return "redirect:/socio/mostrar";
	}

	@GetMapping("/mostrar")
	public String showAll(ModelMap model) {

		try {
			List<Socio> socio = service.showAll();
			model.addAttribute("socio", socio);
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			return "mostrar.html";

		}
		return "mostrar.html";
	}

	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable String id, ModelMap model) {

		try {
			service.delete(id);
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			return "redirect:/socio/mostrar";
		}
		return "redirect:/socio/mostrar";

	}

	@GetMapping("/buscarnombre")
	public String buscarNombre(ModelMap model, @RequestParam String nombre, @RequestParam String apellido) {

		try {

			List<Socio> socio = service.searchByName(nombre, apellido);
			model.addAttribute("socio", socio);
		} catch (ErrorServicio e) {
			model.addAttribute("error", e.getMessage());
			return "";

		}
		return "./src/main/resources/templates/mostrar.html";

	}

	@GetMapping("/buscardni")
	public String buscarDni(ModelMap model, @RequestParam String dni) {

		try {

			Optional <Socio> socio = service.searchBydni(dni);
			model.addAttribute("socio", socio);
		} catch (ErrorServicio e) {
			model.addAttribute("error", e.getMessage());
			return "";

		}
		return "mostrar.html";

	}

	@GetMapping("/buscartaller")
	public String buscarTaller(ModelMap model, @RequestParam String nombre) {

		try {

			List<Socio> socio = service.searchByTaller(nombre);
			model.addAttribute("socio", socio);
		} catch (ErrorServicio e) {
			model.addAttribute("error", e.getMessage());
			return "";

		}
		return "taller.html";

	}
	

}