import axios from 'axios';
import warehouseService from '../../services/warehouseService';

jest.mock('axios');

describe('WarehouseService', () => {
    const API_URL = "http://inventoryman-env.us-east-1.elasticbeanstalk.com:8080/api/warehouses";

    afterEach(() => { // Cleanup after each test
        jest.clearAllMocks();
    });

    it('should fetch all warehouses', async () => { // Checks if all warehouses are fetched
        const mockData = [{ id: 1, name: 'Warehouse A' }];

        axios.get.mockResolvedValue({ data: mockData });
        const result = await warehouseService.getAll();

        expect(axios.get).toHaveBeenCalledWith(API_URL); // Check if the get method is called with the API URL
        expect(result.data).toEqual(mockData); // Check if the data fetched is equal to the mock data
    });

    it('should fetch a warehouse by ID', async () => { // Checks if a warehouse is fetched by ID
        const mockData = { id: 1, name: 'Warehouse A' };

        axios.get.mockResolvedValue({ data: mockData });
        const result = await warehouseService.get(1); // Get the warehouse with ID 1

        expect(axios.get).toHaveBeenCalledWith(`${API_URL}/1`);
        expect(result.data).toEqual(mockData); // Check if the data fetched is equal to the mock data - ID 1, name 'Warehouse A'
    });

    it('should create a new warehouse', async () => { // Checks if a new warehouse is created after the post method is called
        const newWarehouse = { name: 'Warehouse A' };
        const mockData = { id: 1, ...newWarehouse };
        axios.post.mockResolvedValue({ data: mockData }); // Mock the axios post method to return the mock data
        const result = await warehouseService.create(newWarehouse); // Create a new warehouse with the name 'Warehouse A'

        expect(axios.post).toHaveBeenCalledWith(API_URL, newWarehouse, { // Check if the post method is called with the new warehouse with post method
            headers: {
                'Content-Type': 'application/json'
            }
        });
        expect(result.data).toEqual(mockData); // Check if the data fetched is equal to the mock data - ID 1, name 'Warehouse A'
    });

    it('should update a warehouse by ID', async () => { // Checks if a warehouse is updated by ID
        const updatedWarehouse = { name: 'Updated Warehouse A' }; // Updated warehouse name
        const mockData = { id: 1, ...updatedWarehouse };
        axios.put.mockResolvedValue({ data: mockData }); // Mock the axios put method
        const result = await warehouseService.update(1, updatedWarehouse);

        expect(axios.put).toHaveBeenCalledWith(`${API_URL}/1`, updatedWarehouse, { // Check if the put method is called with the updated warehouse
            headers: {
                'Content-Type': 'application/json'
            }
        });

        expect(result.data).toEqual(mockData); // Check if the data fetched is equal to the mock data - ID 1, name 'Updated Warehouse A'
    });

    it('should delete a warehouse by ID', async () => { // Checks if a warehouse is deleted by ID
        axios.delete.mockResolvedValue({});
        const result = await warehouseService.delete(1); // Delete the warehouse with ID 1 via the method

        expect(axios.delete).toHaveBeenCalledWith(`${API_URL}/1`); // Axios delete method assertion
        expect(result).toEqual({});
    });

    it('should fetch current space used by warehouse ID', async () => { // Checks if the current space used by a warehouse is fetched by ID
        const mockData = { usedSpace: 50 }; // Mock data - used space is 50
        axios.get.mockResolvedValue({ data: mockData });
        const result = await warehouseService.getCurrentSpaceUsed(1); // Get the current space used by warehouse ID 1

        expect(axios.get).toHaveBeenCalledWith(`${API_URL}/1/space-used`);
        expect(result.data).toEqual(mockData); // Should return the mock data - 50 again
    });
});