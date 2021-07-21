package com.example.apiproductosecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.GeneratedValue;
import java.util.List;

@RequestMapping("/api/productos")
@RestController
public class ProductoController {
    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<String> getNombre(){
        return productoService.getNombreProducto();
    }
    @GetMapping("detalles")
    public List<Producto> getProducto(){
        return productoService.getProductos();
    }
}
