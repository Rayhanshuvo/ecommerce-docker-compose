package com.rayhan.ecommercecrud.service;

import com.rayhan.ecommercecrud.api.NotFoundException;
import com.rayhan.ecommercecrud.domain.Product;
import com.rayhan.ecommercecrud.repo.ProductRepository;
import com.rayhan.ecommercecrud.service.dto.ProductCreateRequest;
import com.rayhan.ecommercecrud.service.dto.ProductResponse;
import com.rayhan.ecommercecrud.service.dto.ProductUpdateRequest;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public ProductResponse create(ProductCreateRequest req) {
        Product p = new Product();
        p.setName(req.name());
        p.setSku(req.sku());
        p.setPrice(req.price());
        p.setQuantity(req.quantity());

        try {
            Product saved = repo.save(p);
            return toResponse(saved);
        } catch (DataIntegrityViolationException ex) {
            // likely duplicate SKU
            throw new IllegalArgumentException("SKU already exists: " + req.sku());
        }
    }

    public List<ProductResponse> list() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    public ProductResponse get(Long id) {
        Product p = repo.findById(id).orElseThrow(() -> new NotFoundException("Product not found: " + id));
        return toResponse(p);
    }

    @Transactional
    public ProductResponse update(Long id, ProductUpdateRequest req) {
        Product p = repo.findById(id).orElseThrow(() -> new NotFoundException("Product not found: " + id));

        p.setName(req.name());
        p.setSku(req.sku());
        p.setPrice(req.price());
        p.setQuantity(req.quantity());

        try {
            Product saved = repo.save(p);
            return toResponse(saved);
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalArgumentException("SKU already exists: " + req.sku());
        }
    }

    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new NotFoundException("Product not found: " + id);
        }
        repo.deleteById(id);
    }

    private ProductResponse toResponse(Product p) {
        return new ProductResponse(
                p.getId(),
                p.getName(),
                p.getSku(),
                p.getPrice(),
                p.getQuantity(),
                p.getCreatedAt(),
                p.getUpdatedAt()
        );
    }
}
