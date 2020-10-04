package ru.otus.lantukh.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sergey
 * created on 14.12.18.
 */
public class HWCacheDemo {
    private static final Logger logger = LoggerFactory.getLogger(HWCacheDemo.class);

    public static void main(String[] args) {
        new HWCacheDemo().demo();
    }

    private void demo() {
        HwCache<Integer, Integer> cache = new MyCache<>();

        HwListener<Integer, Integer> listener1 = new HwListener<Integer, Integer>() {
            @Override
            public void notify(Integer key, Integer value, String action) {
                logger.info("key:{}, value:{}, action: {}", key, value, action);
            }
        };

        HwListener<Integer, Integer> listener2 = new HwListener<Integer, Integer>() {
            @Override
            public void notify(Integer key, Integer value, String action) {
                logger.info("key:{}, value:{}, action: {}", key, value, action);
            }
        };

        cache.addListener(listener1);
        cache.addListener(listener2);

        cache.put(1, 155);
        cache.put(2, 266);

        logger.info("getValue:{}", cache.get(1));
        cache.remove(1);
        cache.removeListener(listener1);

        cache.put(3, 355);
    }
}
