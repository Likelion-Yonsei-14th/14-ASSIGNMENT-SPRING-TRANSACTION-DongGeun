package com.example.shop_app.service;

import com.example.shop_app.domain.Member;
import com.example.shop_app.domain.Product;
import com.example.shop_app.dto.ProductCreateRequest;
import com.example.shop_app.dto.ProductResponse;
import com.example.shop_app.dto.ProductUpdateRequest;
import com.example.shop_app.exception.CustomException;
import com.example.shop_app.exception.ErrorCode;
import com.example.shop_app.exception.ForbiddenException;
import com.example.shop_app.exception.ProductNotFoundException;
import com.example.shop_app.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductCreateRequest request, Member member) {
        validateProduct(request.getName(), request.getDescription(), request.getPrice(), request.getStockQuantity());

        Product product = Product.create(member, request.getName(), request.getDescription(), request.getPrice(), request.getStockQuantity());
        Product savedProduct = productRepository.save(product);

        return ProductResponse.from(savedProduct);
    }

    public ProductResponse getProduct(Long productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(ProductNotFoundException::new);

        return ProductResponse.from(product);
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findByNameContainingOrderByCreatedAtDesc()
            .stream()
            .map(ProductResponse::from)
            .toList();
    }

    public ProductResponse updateProduct(Long productId, ProductUpdateRequest request, Member member) {
        validateProduct(request.getName(), request.getDescription(), request.getPrice(), request.getStockQuantity());

        Product product = productRepository.findById(productId)
            .orElseThrow(ProductNotFoundException::new);
        
        validateOwner(product, member);

        product.updateProduct(request.getName(), request.getDescription(), request.getPrice(), request.getStockQuantity());
        Product savedProduct = productRepository.save(product);

        return ProductResponse.from(savedProduct);
    }

    public void deleteProduct(Long productId, Member member) {
        Product product = productRepository.findById(productId)
            .orElseThrow(ProductNotFoundException::new);
        
        validateOwner(product, member);

        productRepository.delete(product);
    }

    private void validateProduct(String name, String description, int price, int stockQuantity) {
        if (name == null || name.isBlank()) {
            throw new CustomException(ErrorCode.INVALID_PRODUCT_NAME);
        }
        if (description == null || description.isBlank()) {
            throw new CustomException(ErrorCode.INVALID_PRODUCT_DESCRIPTION);
        }
        if (price <= 0) {
            throw new CustomException(ErrorCode.INVALID_PRODUCT_PRICE);
        }
        if (stockQuantity < 0) {
            throw new CustomException(ErrorCode.INVALID_PRODUCT_STOCK_QUANTITY);
        }
    }

    private void validateOwner(Product product, Member member) {
        if (!product.getMember().getId().equals(member.getId())) {
            throw new ForbiddenException();
        }
    }
}