import axios from 'axios';
import itemService from '../../services/itemService';

jest.mock('axios');

describe('ItemService', () => {
    const API_URL = "http://inventoryman-env.us-east-1.elasticbeanstalk.com:8080/api/items";

    afterEach(() => {
        jest.clearAllMocks();   
    });

    it('should fetch all items', async () => {
        const mockData = [{ id: 1, name: 'Item A' }];
        axios.get.mockResolvedValue({ data: mockData });

        const result = await itemService.getAll();

        expect(axios.get).toHaveBeenCalledWith(API_URL);
        expect(result.data).toEqual(mockData);
    });

    it('should fetch an item by ID', async () => {
        const mockData = { id: 1, name: 'Item A' };
        axios.get.mockResolvedValue({ data: mockData });

        const result = await itemService.get(1);

        expect(axios.get).toHaveBeenCalledWith(`${API_URL}/1`);
        expect(result.data).toEqual(mockData);
    });

    it('should create a new item', async () => {
        const newItem = { name: 'Item A' };
        const mockData = { id: 1, ...newItem };
        axios.post.mockResolvedValue({ data: mockData });

        const result = await itemService.create(newItem);

        expect(axios.post).toHaveBeenCalledWith(API_URL, newItem, {
            headers: {
                'Content-Type': 'application/json'
            }
        });
        expect(result.data).toEqual(mockData);
    });

    it('should update an item by ID', async () => {
        const updatedItem = { name: 'Updated Item A' };
        const mockData = { id: 1, ...updatedItem };
        axios.put.mockResolvedValue({ data: mockData });

        const result = await itemService.update(1, updatedItem);

        expect(axios.put).toHaveBeenCalledWith(`${API_URL}/1`, updatedItem, {
            headers: {
                'Content-Type': 'application/json'
            }
        });

        expect(result.data).toEqual(mockData);
    });

    it('should delete an item by ID', async () => {
        axios.delete.mockResolvedValue({});
        const result = await itemService.delete(1);

        expect(axios.delete).toHaveBeenCalledWith(`${API_URL}/1`);
        expect(result).toEqual({});
    });
});