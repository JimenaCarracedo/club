package com.sanmartin.club.Controladores;

import java.util.Date;
import java.util.List;

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

import com.sanmartin.club.Entidades.Cuotas;
import com.sanmartin.club.Entidades.Eventos;
import com.sanmartin.club.Entidades.Socio;
import com.sanmartin.club.Entidades.Taller;
import com.sanmartin.club.ErrorService.ErrorServicio;
import com.sanmartin.club.Service.CuotaService;
import com.sanmartin.club.Service.EventosService;
import com.sanmartin.club.Service.TallerService;



@Controller
public class CuotaController {
	
	@Autowired
	private EventosService EvSv;

	@Autowired
	private TallerService TaSv;

	@Autowired
	private CuotaService CuoSv;

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("admin/cuota")
	public String listar(ModelMap modelo) {
		try {
			List<Cuotas> lista = CuoSv.showAll();
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
			CuoSv.delete(id);
		} catch (Exception e) {
			modelo.addAttribute("error", e.getMessage());
		}

		return ("redirect:/cuotas");
	}

	
	  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	  
	  @GetMapping("/ver-cuota{id}") public String verAnalisis(ModelMap
	  modelo, @PathVariable String id) { try { Cuotas cu = CuoSv.findById(id);
	  modelo.addAttribute(cu); } catch (Exception e) { modelo.addAttribute("error",
	  e.getMessage()); }
	  
	  return ("redirect:/cuota"); }
	 
	 @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/modificar/{id}")
	public String viewEdit(ModelMap modelo, @PathVariable String id) {
		try {
			List<Taller> taller = TaSv.showAll();
			modelo.addAttribute("taller", TaSv);
			List<Eventos> ev = EvSv.showAll();
			modelo.addAttribute("eventos", ev);
			Cuotas cuo = CuoSv.findById(id);
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
			CuoSv.edit(id, evento, foto, fechaOrden, socio, taller);
		} catch (ErrorServicio e) {
			modelo.addAttribute("error", e.getMessage());
		}

		return ("redirect:/cuota");
	}

	@PreAuthorize("hasAnyRole('ROLE_Socio')")
	@GetMapping("/crear")
	public String nuevaCuo(ModelMap modelo) {
		try {
			List<Taller> Os = TaSv.showAll();
			modelo.addAttribute("Taller", TaSv);
			List<Eventos> ev = EvSv.showAll();
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
			CuoSv.create(evento, foto, fechaOrden, socio, taller);
		} catch (ErrorServicio e) {
			modelo.addAttribute("error", e.getMessage());
			return ("redirect:/cuota");
		}

		return ("redirect:/cuota");
	}

}
