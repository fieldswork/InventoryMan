import axios from 'axios';
import warehouseService from '../../services/warehouseService';

jest.mock('axios');

describe('WarehouseService', () => {
    const API_URL = "http://inventoryman-env.us-east-1.elasticbeanstalk.com:8080/api/warehouses";

    afterEach(() => {
        jest.clearAllMocks();
    });

    it('should fetch all warehouses', async () => {
        const mockData = [{ id: 1, name: 'Warehouse A' }];

        axios.get.mockResolvedValue({ data: mockData });
        const result = await warehouseService.getAll();

        expect(axios.get).toHaveBeenCalledWith(API_URL);
        expect(result.data).toEqual(mockData);
    });

    it('should fetch a warehouse by ID', async () => {
        const mockData = { id: 1, name: 'Warehouse A' };

        axios.get.mockResolvedValue({ data: mockData });
        const result = await warehouseService.get(1);

        expect(axios.get).toHaveBeenCalledWith(`${API_URL}/1`);
        expect(result.data).toEqual(mockData);
    });

    it('should create a new warehouse', async () => {
        const newWarehouse = { name: 'Warehouse A' };
        const mockData = { id: 1, ...newWarehouse };
        axios.post.mockResolvedValue({ data: mockData });
        const result = await warehouseService.create(newWarehouse);

        expect(axios.post).toHaveBeenCalledWith(API_URL, newWarehouse, {
            headers: {
                'Content-Type': 'application/json'
            }
        });
        expect(result.data).toEqual(mockData);
    });

    it('should update a warehouse by ID', async () => {
        const updatedWarehouse = { name: 'Updated Warehouse A' };
        const mockData = { id: 1, ...updatedWarehouse };
        axios.put.mockResolvedValue({ data: mockData });
        const result = await warehouseService.update(1, updatedWarehouse);

        expect(axios.put).toHaveBeenCalledWith(`${API_URL}/1`, updatedWarehouse, {
            headers: {
                'Content-Type': 'application/json'
            }
        });

        expect(result.data).toEqual(mockData);
    });

    it('should delete a warehouse by ID', async () => {
        axios.delete.mockResolvedValue({});
        const result = await warehouseService.delete(1);

        expect(axios.delete).toHaveBeenCalledWith(`${API_URL}/1`);
        expect(result).toEqual({});
    });

    it('should fetch current space used by warehouse ID', async () => {
        const mockData = { usedSpace: 50 };
        axios.get.mockResolvedValue({ data: mockData });
        const result = await warehouseService.getCurrentSpaceUsed(1);

        expect(axios.get).toHaveBeenCalledWith(`${API_URL}/1/space-used`);
        expect(result.data).toEqual(mockData);
    });
});