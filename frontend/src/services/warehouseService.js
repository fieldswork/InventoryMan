import axios from 'axios';

const API_URL = 'http://localhost:8080/api/warehouses';

class WarehouseService {
  getAll() {
    return axios.get(API_URL);
  }

  get(id) {
    return axios.get(`${API_URL}/${id}`);
  }

  create(data) {
    return axios.post(API_URL, data, {
      headers: {
        'Content-Type': 'application/json'
      }
    });
  }

  update(id, data) {
    return axios.put(`${API_URL}/${id}`, data, {
      headers: {
        'Content-Type': 'application/json'
      }
    });
  }

  delete(id) {
    return axios.delete(`${API_URL}/${id}`);
  }

  getCurrentSpaceUsed(id) {
    return axios.get(`${API_URL}/${id}/space-used`);
  }
}

const warehouseService = new WarehouseService();
export default warehouseService;
