package com.club.sanmartin.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.club.sanmartin.Entidades.Reservas;
import com.club.sanmartin.Service.ReservasService;

@Controller
public class ReservasController {
	@Autowired
	private ReservasService sv;
	

	@GetMapping("/admin/reservas")
	public String prepa(ModelMap modelo) {
		try {
			List<Reservas> lista = sv.showAll();
			modelo.addAttribute("lista", lista);
		} catch (Exception e) {
			modelo.addAttribute("error", e.getMessage());
		}
		return "admin/reservas";
	}

	@GetMapping("/createreserva")
	public String createReserva() {
		return "admin/creareserva";
	}

	@PostMapping("/admin/reserva/save")
	public String save(ModelMap modelo, @RequestParam String nombre, @RequestParam String actividad) {
		try {
			sv.create(nombre, actividad);
			return "redirect:/admin/reservas";
		} catch (Exception e) {
			modelo.addAttribute("error", e.getMessage());
			return "redirect:/admin/reservas";
		}
	}

	@GetMapping("/editreserva/{id}")
	public String editReserva(ModelMap modelo, @PathVariable String id) {
		Reservas reservas = sv.findById(id);
		modelo.addAttribute("reser", reservas);
		return "admin/editreserva";
	}

	@PostMapping("/admin/reservas/saveeditt")
	public String saveEdit(ModelMap modelo, @RequestParam String id, @RequestParam String nombre,
			@RequestParam String evento) {
		try {
			System.out.println("ID=" + id);
			sv.edit(id, nombre, evento);
		} catch (Exception e) {
			modelo.addAttribute("error", e.getMessage());
			return "redirect:/admin/reservas";
		}
		return "redirect:/admin/reservas";
	}
}
