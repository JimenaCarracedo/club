package com.club.sanmartin.Controladores;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.club.sanmartin.Entidades.Cuotas;
import com.club.sanmartin.Entidades.Eventos;
import com.club.sanmartin.Entidades.Socio;
import com.club.sanmartin.Entidades.Taller;
import com.club.sanmartin.ErrorService.ErrorServicio;
import com.club.sanmartin.Service.CuotaService;
import com.club.sanmartin.Service.EventosService;
import com.club.sanmartin.Service.TallerService;



@Controller
public class CuotaController {
	
	@Autowired
	private EventosService evSv;

	@Autowired
	private TallerService taSv;

	@Autowired
	private CuotaService cuoSv;

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("admin/cuota")
	public String listar(ModelMap modelo) {
		try {
			List<Cuotas> lista = cuoSv.showAll();
			modelo.addAttribute("cuota", lista);
			modelo.addAttribute("activeLink", "Cuotas");

		} catch (Exception e) {
			modelo.addAttribute("error", e.getMessage());
		}
		return ("cuotaCrear");
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/eliminar/{id}")
	public String borrar(ModelMap modelo, @PathVariable String id) {
		try {
			cuoSv.delete(id);
		} catch (Exception e) {
			modelo.addAttribute("error", e.getMessage());
		}

		return ("redirect:/cuotas");
	}

	
	  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	  
	  @GetMapping("/ver-cuota{id}") public String verAnalisis(ModelMap
	  modelo, @PathVariable String id) { try { Optional<Cuotas> cu = cuoSv.findById(id);
	  modelo.addAttribute(cu); } catch (Exception e) { modelo.addAttribute("error",
	  e.getMessage()); }
	  
	  return ("redirect:/cuota"); }
	 
	 @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/modificar/{id}")
	public String viewEdit(ModelMap modelo, @PathVariable String id) {
		try {
		    
			modelo.addAttribute("taller", taSv.showAll());
			List<Eventos> ev = evSv.showAll();
			modelo.addAttribute("eventos", ev);
			Optional<Cuotas> cuo = cuoSv.findById(id);
			modelo.addAttribute("cuo", cuo);
		} catch (Exception e) {
			modelo.addAttribute("error", e.getMessage());
		}

		return "cuotaEditar";
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/save-edit")
	public String editar(ModelMap modelo, @RequestParam String id, @RequestParam List<Eventos> evento,
			@RequestParam MultipartFile foto, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaOrden,
			@RequestParam Socio socio, @RequestParam Taller taller) throws ErrorServicio {
		try {
			cuoSv.edit(id, evento, foto, fechaOrden, socio, taller);
		} catch (ErrorServicio e) {
			modelo.addAttribute("error", e.getMessage());
		}

		return ("redirect:/cuota");
	}

	@PreAuthorize("hasAnyRole('ROLE_Socio')")
	@GetMapping("/crear")
	public String nuevaCuo(ModelMap modelo) {
		try {
			
			modelo.addAttribute("Taller", taSv.showAll());
			List<Eventos> ev = evSv.showAll();
			modelo.addAttribute("evento", ev);
		} catch (Exception e) {
			modelo.addAttribute("error", e.getMessage());
		}
		return ("cuotaCrear");
	}

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/guardar")
	public String sendNew(ModelMap modelo, @RequestParam List<Eventos> evento, @RequestParam MultipartFile foto,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaOrden, @RequestParam Socio socio,
			@RequestParam Taller taller) throws ErrorServicio {
		try {
			cuoSv.create(evento, foto, fechaOrden, socio, taller);
		} catch (ErrorServicio e) {
			modelo.addAttribute("error", e.getMessage());
			return ("redirect:/cuota");
		}

		return ("redirect:/cuota");
	}

}
