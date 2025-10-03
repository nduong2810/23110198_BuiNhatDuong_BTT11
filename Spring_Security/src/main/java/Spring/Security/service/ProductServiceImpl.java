package Spring.Security.service;

import Spring.Security.entity.Product;
import Spring.Security.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductServices {

    private final ProductRepository repo;

    @Override
    public List<Product> listAll() {
        return repo.findAll();
    }

    @Override
    public Product save(Product product) {
        return repo.save(product);
    }

    @Override
    public Product get(Long id) {
        return repo.findById(id)
                   .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Product not found: " + id);
        }
        repo.deleteById(id);
    }
}
