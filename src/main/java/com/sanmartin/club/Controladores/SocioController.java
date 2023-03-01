package com.sanmartin.club.Controladores;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import org.apache.tomcat.jni.User;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.remoting.jaxws.SimpleHttpServerJaxWsServiceExporter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.sanmartin.club.Entidades.Socio;
import com.sanmartin.club.Entidades.Taller;

import com.sanmartin.club.ErrorService.ErrorServicio;
import com.sanmartin.club.Repository.SocioRepository;
import com.sanmartin.club.Service.CuotaService;
import com.sanmartin.club.Service.SocioService;
import com.sanmartin.club.Service.TallerService;


@Controller
@RequestMapping("/socio")
public class SocioController {
	@Autowired
	private SocioService service;

	@Autowired
	private TallerService tallerService;

	@Autowired
	private SocioRepository sRepository;
	@GetMapping("/login")
	public String loginView(){
		return "login.html";
	}
	@PostMapping("socio/login")
	public String login(User user,String username, ModelMap model, @RequestParam(required = false) String dni,
			@RequestParam(required = false) String clave) {
			
		service.loadUserByUsername(username);
		if(user!=null) {
			return "inicio.html";
		}
		return "registrar.html";
	}
				
	@GetMapping("/registrar")
	public String registerView(ModelMap model) {

		List<Taller> taller;
		try {
			taller = tallerService.showAll();
			model.addAttribute("taller", taller);

		} catch (ErrorServicio e) {
			model.addAttribute("error", e.getMessage());
			return "registrar.html";
		}

		return "registrar.html";
	}
	
	@PostMapping("/registrar")

	public String registrar(ModelMap model, @RequestParam(required = false) String nombre,
			@RequestParam(required = false) String apellido, @RequestParam(required = false) String dni,
			@RequestParam(required = false) String clave, @RequestParam(required = false) String mail,
			@RequestParam(required = false) Integer telefono,
			@RequestParam(required = false) List<Taller> taller,
			@RequestParam(required = false) Integer numeroAsociado,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaNacimiento,
			@RequestParam(required = false) String direccion, @RequestParam(required = false) String sexo,
			MultipartFile foto, 
			@RequestParam(required = false) String clave2) {
			
		try {
			service.create(nombre, apellido, dni, clave, mail, telefono, taller, numeroAsociado, fechaNacimiento, direccion, sexo, foto, clave2);

		} catch (ErrorServicio e) {
			model.addAttribute("error", e.getMessage());

			try {
				List<Taller> talleres = tallerService.showAll();
				model.addAttribute("taller", taller);
			} catch (ErrorServicio e1) {

			}

			return "registrar.html";

		}

		return "inicio.html";
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
			String mail, Integer telefono, List<Taller> taller, Integer numeroAsociado,
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

			Socio socio = service.searchBydni(dni);
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