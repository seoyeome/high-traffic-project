import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  vus: __ENV.VUS ? parseInt(__ENV.VUS) : 100,
  duration: __ENV.DURATION || '1m',
  thresholds: {
    http_req_duration: ['p(95)<800'],
    http_req_failed: ['rate<0.02'],
  },
};

const BASE_URL = __ENV.BASE_URL || 'http://localhost:8000';

function randomInt(min, max) {
  return Math.floor(Math.random() * (max - min + 1)) + min;
}

export default function () {
  const userId = `user-${__VU % 1000}`;
  const productId = randomInt(1, 5);
  const quantity = randomInt(1, 3);

  const payload = JSON.stringify({ userId, productId, quantity });
  const params = { headers: { 'Content-Type': 'application/json' } };

  const res = http.post(`${BASE_URL}/orders`, payload, params);
  check(res, {
    'create order 201': (r) => r.status === 201,
  });

  if (Math.random() < 0.3) {
    const res2 = http.get(`${BASE_URL}/orders?userId=${userId}`);
    check(res2, {
      'get orders 200': (r) => r.status === 200,
    });
  }

  sleep(1);
}
