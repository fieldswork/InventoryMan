import axios from 'axios';

const API_URL = 'http://18.209.149.218:8080/api/warehouses'; // EC2 public IP (may change)
// const API_URL = 'http://44.214.65.155:8080/api/warehouses'; // EC2 elastic IP (backup)

class WarehouseService { // Routes api calls to the backend for warehouse data
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
