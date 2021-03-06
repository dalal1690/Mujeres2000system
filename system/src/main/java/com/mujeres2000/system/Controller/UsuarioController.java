package com.mujeres2000.system.Controller;

import com.mujeres2000.system.Service.UsuarioService;
import com.mujeres2000.system.model.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    Log log = LogFactory.getLog(UsuarioController.class);

    @PostMapping(path = "/registrar")
    @Operation(summary = "Guarda nuevo usuario", description = "Guarda un nuevo usuario a la base de datos")
    public ResponseEntity<String> crearUsuario(@RequestBody Usuario usuario) {
        log.info("Llamada a /registrar con usuario: " + usuario.getEmail());
        usuarioService.crearUsuario(usuario);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(path = "/ingresar")
    @Operation(summary = "login del usuario", description = "ingreso del usuario ya registrado")
    public ResponseEntity<String> ingresoUsuario(@RequestBody Usuario usuario, HttpServletRequest request) {
        log.info("Llamada a /ingresar con usuario: " + usuario.getEmail());
        usuario = usuarioService.ingresoUsuario(usuario);
        request.getSession().setAttribute("USUARIO_ID", usuario.getUsuario_id());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
