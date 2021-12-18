package kitchenpos.application;

import kitchenpos.dao.ProductDao;
import kitchenpos.menu.Product;
import kitchenpos.menu.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class ProductService {
//    private final ProductDao productDao;
    private final ProductRepository productRepository;

    public ProductService(
//            final ProductDao productDao,
            final ProductRepository productRepository
    ) {
//        this.productDao = productDao;
        this.productRepository = productRepository;
    }

    @Transactional
    public Product create(final Product product) {
//        final BigDecimal price = product.getPrice();
//
//        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
//            throw new IllegalArgumentException();
//        }

//        return productDao.save(product);

        return productRepository.save(product);
    }

    public List<Product> list() {
        //return productDao.findAll();
        return productRepository.findAll();
    }
}
