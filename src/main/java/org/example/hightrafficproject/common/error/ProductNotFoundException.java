package org.example.hightrafficproject.common.error;

import java.util.HashMap;
import java.util.Map;

public class ProductNotFoundException extends BusinessException {
    public ProductNotFoundException(Long productId) {
        super(
            ErrorCode.PRODUCT_NOT_FOUND,
            String.format("상품을 찾을 수 없습니다. productId=%d", productId),
            detail(productId)
        );
    }

    private static Map<String, Object> detail(Long productId) {
        Map<String, Object> m = new HashMap<>();
        m.put("productId", productId);
        return m;
    }
}
