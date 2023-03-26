package com.club.sanmartin.Controladores;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.club.sanmartin.Entidades.Role;
import com.club.sanmartin.Entidades.Socio;
import com.club.sanmartin.Entidades.Taller;
import com.club.sanmartin.ErrorService.ErrorServicio;
import com.club.sanmartin.Repository.RoleRepository;
import com.club.sanmartin.Repository.SocioRepository;
import com.club.sanmartin.Service.TallerService;
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SocioRepository sRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
   
    @Autowired
    private TallerService tallerService;
    @GetMapping("/login")
    public String loginview(){
    	return "/login";
    }
    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("Estás Loggeado!.", HttpStatus.OK);
    }
    @GetMapping("/registrar")
    public String registerView(ModelMap model) {

    	List<Taller> taller;
    	
		try {
			taller = tallerService.showAll();
			model.addAttribute("taller", taller);

		} catch (ErrorServicio e) {
			model.addAttribute("error", e.getMessage());
			return "/registrar.html";
		}

		return "registrar.html";
	}
    @PostMapping("/registrar")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDto registerDto){

        // add check for username exists in a DB
        if(sRepository.buscarDni(registerDto.getNombreUsuario())!=null){
            return new ResponseEntity<>("El dni ya está registrado!", HttpStatus.BAD_REQUEST);
        }

        // create user object
        Socio socio = new Socio();
        socio.setNombre(registerDto.getNombre());
        socio.setNombreUsuario(registerDto.getNombreUsuario());
        socio.setMail(registerDto.getMail());
        socio.setClave(passwordEncoder.encode(registerDto.getPassword()));
        socio.setApellido(registerDto.getApellido());
        socio.setDireccion(registerDto.getDireccion());
        socio.setDni(registerDto.getDni());
        socio.setFoto(registerDto.getFoto());
        socio.setFechaNacimiento(registerDto.getFechaNacimiento());
        socio.setNumeroAsociado(registerDto.getNumeroAsociado());
        socio.setId(registerDto.getId());
        socio.setSexo(registerDto.getSexo());
        socio.setTaller(registerDto.getTaller());
        socio.setTelefono(registerDto.getTelefono());
        Role roles = roleRepository.searchByName("ROLE_ADMIN").get();
        socio.setRoles(Collections.singleton(roles));

        sRepository.save(socio);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);

    }

}
