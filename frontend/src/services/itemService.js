import axios from 'axios';

const API_URL = 'http://localhost:8080/api/items'; // This also needs to be updated if moved to AWS

class ItemService { // Routes api calls to the backend for item data
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
}

const itemService = new ItemService();
export default itemService;
