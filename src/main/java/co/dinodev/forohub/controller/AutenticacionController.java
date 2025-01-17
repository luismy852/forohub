package co.dinodev.forohub.controller;

import co.dinodev.forohub.domain.usuario.Usuario;
import co.dinodev.forohub.infra.security.AutenticacionService;
import co.dinodev.forohub.infra.security.DTOJWTtoken;
import co.dinodev.forohub.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity realizarLogin(@RequestBody @Valid Usuario datos) {
        var authToken = new UsernamePasswordAuthenticationToken(datos.getLogin(), datos.getClave());
        var usarioAutenticado = manager.authenticate(authToken);
        var JWTtoken = tokenService.generarToken((Usuario) usarioAutenticado.getPrincipal());


        return ResponseEntity.ok(new DTOJWTtoken(JWTtoken));
    }
}
