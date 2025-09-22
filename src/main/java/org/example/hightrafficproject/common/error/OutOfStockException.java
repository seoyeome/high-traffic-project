package org.example.hightrafficproject.common.error;

import java.util.HashMap;
import java.util.Map;

public class OutOfStockException extends BusinessException {
    public OutOfStockException(Long productId, int requested, long available) {
        super(
            ErrorCode.OUT_OF_STOCK,
            String.format("재고가 부족합니다. productId=%d, requested=%d, available=%d", productId, requested, available),
            detail(productId, requested, available)
        );
    }

    private static Map<String, Object> detail(Long productId, int requested, long available) {
        Map<String, Object> m = new HashMap<>();
        m.put("productId", productId);
        m.put("requested", requested);
        m.put("available", available);
        return m;
    }
}
