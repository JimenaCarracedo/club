package com.sanmartin.club.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sanmartin.club.Entidades.Taller;
import com.sanmartin.club.Service.TallerService;


@Controller
@RequestMapping("/taller")
public class TallerController {
	@Autowired
	private TallerService tallerService;

	// @PreAuthorize("hasAnyRole('ROLE_SOCIO')") //--> TERMINAL LOS ROLES //-->
	// al comentarla FUNCIONA
	@GetMapping("")
	public String listartalleres(ModelMap modelo) {
		try {
			modelo.addAttribute("listT", tallerService.showAll());
			modelo.addAttribute("activeLink", "TallereS");
			return "taller";
		} catch (Exception e) {
			modelo.addAttribute("error", e.getMessage());
			return "taller";
		}
	}

	// @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/crear")
	public String crearTaller(ModelMap modelo) {
		modelo.addAttribute("activeLink", "TallereS");
		return "tallercrear";
	}

	// @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/crear")
	public String guardarObraSocial(@RequestParam String nombre, @RequestParam Integer codigo,
			@RequestParam Long telefono, @RequestParam String direccion, @RequestParam Double arancel,
			@RequestParam String mail, ModelMap modelo) {
		try {
			tallerService.create(nombre, codigo, telefono, direccion, arancel, mail);
			return "redirect:/taller";
		} catch (Exception e) {
			modelo.addAttribute("error", e.getMessage());
			return "tallercrear";
		}
	}

	
	
	
	
	
	// @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/eliminar/{id}")
	public String eliminarTaller(@PathVariable String id, ModelMap modelo) {

		try {
			tallerService.delete(id);
			return "redirect:/taller";
		} catch (Exception e) {
			modelo.addAttribute("error", e.getMessage());
			return "redirect:/taller";
		}

	}

	// @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/modificar/{id}")
	public String modificarTaller(@PathVariable String id, ModelMap modelo) {
		try {
			Taller t = tallerService.findById(id);
			modelo.addAttribute("taller", t);
			modelo.addAttribute("activeLink", "TallereS");
			return "tallermodificar";
		} catch (Exception e) {
			modelo.addAttribute("error", e.getMessage());
			return "tallermodificar";
		}
	}

	// @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/modificar")
	public String modificarTaller(@RequestParam String id, @RequestParam String nombre,
			@RequestParam Integer codigo, @RequestParam Long telefono, @RequestParam String direccion,
			@RequestParam Double arancel, @RequestParam String mail, ModelMap modelo) {
		try {
			tallerService.edit(id, nombre, codigo, telefono, direccion, arancel, mail);
			return "redirect:/taller";
		} catch (Exception e) {
			modelo.addAttribute("error", e.getMessage());
			return "tallermodificar";
		}
	}

	// @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/TallerporDniSocio")
	public String tporDniSocio(@RequestParam Integer dni, ModelMap modelo) {
		try {
			modelo.addAttribute("tporDniSocio", tallerService.searchByDni(dni));
			return "TallerporDniUsuario";
		} catch (Exception e) {
			modelo.addAttribute("error", e.getMessage());
			return "TallerporDniUsuario";
		}

	}

}
