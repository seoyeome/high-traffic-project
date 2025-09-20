import http from 'k6/http';
import { sleep, check } from 'k6';

export const options = {
  vus: __ENV.VUS ? parseInt(__ENV.VUS) : 200,
  duration: __ENV.DURATION || '1m',
  thresholds: {
    http_req_duration: ['p(95)<500'],
    http_req_failed: ['rate<0.01'],
  },
};

const BASE_URL = __ENV.BASE_URL || 'http://localhost:8000';

export default function () {
  const res = http.get(`${BASE_URL}/products`);
  check(res, {
    'status is 200': (r) => r.status === 200,
  });
  sleep(1);
}
