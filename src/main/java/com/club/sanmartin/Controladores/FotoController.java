package com.club.sanmartin.Controladores;


import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.club.sanmartin.Entidades.Socio;
import com.club.sanmartin.ErrorService.ErrorServicio;
import com.club.sanmartin.Service.SocioService;

@Controller
@RequestMapping("/foto")
public class FotoController {
	@Autowired
	private SocioService sService;

	@GetMapping("/socio/{id}")
	public ResponseEntity<byte[]> socioFoto(@PathVariable String id) {
		try {
			Socio socio = sService.searchById(id);

			if (socio.getFoto() == null) {
				throw new ErrorServicio("El socio no cuenta con foto");
			}

			byte[] imagen = socio.getFoto().getContenido();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.IMAGE_JPEG);

			return new ResponseEntity<>(imagen, headers, HttpStatus.OK);

		} catch (ErrorServicio e) {
			Logger.getLogger(FotoController.class.getName()).log(Level.SEVERE, null, e);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}
}
