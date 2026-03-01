package com.rayhan.ecommercecrud.web;

import com.rayhan.ecommercecrud.service.ProductService;
import com.rayhan.ecommercecrud.service.dto.ProductCreateRequest;
import com.rayhan.ecommercecrud.service.dto.ProductResponse;
import com.rayhan.ecommercecrud.service.dto.ProductUpdateRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Validated
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(@Valid @RequestBody ProductCreateRequest req) {
        return service.create(req);
    }

    @GetMapping
    public List<ProductResponse> list() {
        return service.list();
    }

    @GetMapping("/<built-in function id>")
    public ProductResponse get(@PathVariable @Min(1) Long id) {
        return service.get(id);
    }

    @PutMapping("/<built-in function id>")
    public ProductResponse update(@PathVariable @Min(1) Long id, @Valid @RequestBody ProductUpdateRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/<built-in function id>")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Min(1) Long id) {
        service.delete(id);
    }
}
