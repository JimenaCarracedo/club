package com.sanmartin.club.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sanmartin.club.Entidades.Eventos;
import com.sanmartin.club.Entidades.Taller;
import com.sanmartin.club.ErrorService.ErrorServicio;
import com.sanmartin.club.Service.EventosService;
import com.sanmartin.club.Service.ReservasService;
import com.sanmartin.club.Service.TallerService;

@Controller
@RequestMapping("/eventos")
public class EventosController {
	
	@Autowired
	private EventosService eService;

	@Autowired
	private TallerService tService;
	
	@Autowired
	private ReservasService rService;

	@GetMapping("")
	public String fullView(ModelMap model) {
		try {
			List<Eventos> list = eService.showAll();
			model.addAttribute("list", list);
			model.addAttribute("activeLink", "evento");
		} catch (ErrorServicio e) {
			model.addAttribute("error", e.getMessage());
			return "admin/evento";
		}

		return "admin/evento";
	}

	@GetMapping("/crear")
	public String crearView(ModelMap model) {
		List<Taller> list;
		try {
			list = tService.showAll();
			model.addAttribute("list", list);
			model.addAttribute("activeLink", "CrearEvento");
		} catch (ErrorServicio e) {
			model.addAttribute("error", e.getMessage());
			return "admin/nuevo-evento";
		}

		return "admin/nuevo-evento";
	}

	@PostMapping("/crear")
	public String create(@RequestParam String nombre, @RequestParam Integer valorentrada, @RequestParam Taller t,
			ModelMap model) {
		try {
			eService.create(nombre, valorentrada, t);
		} catch (ErrorServicio e) {
			model.addAttribute("error", e.getMessage());
			return "admin/nuevo-evento";
		}
		return "redirect:/evento";
	}


	@PostMapping("/edit")
	public String edit(@RequestParam String id, @RequestParam String nombre, @RequestParam Integer valorentrada,
			@RequestParam Taller t, ModelMap model) {
		try {
			eService.edit(id, nombre, valorentrada, t);
		} catch (ErrorServicio e) {
			model.addAttribute("error", e.getMessage());
			return "admin/editar";
		}
		return "redirect:/evento";
	}


	public String searchName(@RequestParam(required = false) String name, ModelMap model) {

		try {
			List<Eventos> evento = eService.searchByName(name);
			model.addAttribute("evento", evento);
		} catch (ErrorServicio e) {
			model.addAttribute("error", e.getMessage());
			return "admin/buscar";
		}
		return "admin/buscar";
	}
}
