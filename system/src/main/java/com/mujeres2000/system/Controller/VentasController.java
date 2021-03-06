package com.mujeres2000.system.Controller;

import com.mujeres2000.system.Service.DetalleDeVentasService;
import com.mujeres2000.system.model.DetalleDeVentas;
import com.mujeres2000.system.model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(path = "/ventas")
public class VentasController {
    @Autowired
    DetalleDeVentasService detalleDeVentasService;

    Log log = LogFactory.getLog(UsuarioController.class);

    @PostMapping(path = "/registrar")
    @Operation(summary = "Guarda una nueva venta", description = "Guarda una nueva venta en la base de datos")
    public ResponseEntity<DetalleDeVentas> registrarVenta(@RequestBody DetalleDeVentas detalleDeVentas, HttpServletRequest request ){
        log.info("Sistema registra venta: " + detalleDeVentas.getVenta_id());
        Integer usuarioId = (Integer) request.getSession().getAttribute("USUARIO_ID");
        detalleDeVentasService.registrarVenta(detalleDeVentas, usuarioId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping
    //@Operation(summary = "busca un producto ya guardado", description = "busca un producto ya guardado")
    public ResponseEntity<List<DetalleDeVentas>> listarVentas(HttpServletRequest request) {
        Integer usuarioId = (Integer) request.getSession().getAttribute("USUARIO_ID");
        log.info("Llamada a obtener listado de productos usuario id: " + usuarioId);
        List<DetalleDeVentas> ventas = detalleDeVentasService.listarVentas(usuarioId);
        return new ResponseEntity<>(ventas, HttpStatus.OK);
    }

    @GetMapping(path = "/{venta_id}")
    @Operation(summary = "Busca venta registrada", description = "Busca venta registrada")
    public ResponseEntity<DetalleDeVentas> buscarVenta (@PathVariable Integer venta_id) {
        log.info("Sistema busca venta: " + venta_id);
        DetalleDeVentas ventaEncontrada = detalleDeVentasService.buscarVenta(venta_id);
        return new ResponseEntity<>(ventaEncontrada, HttpStatus.OK);
    }
}
