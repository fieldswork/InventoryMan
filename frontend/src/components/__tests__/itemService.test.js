import axios from 'axios';
import itemService from '../../services/itemService';

jest.mock('axios');

describe('ItemService', () => {
    const API_URL = "http://inventoryman-env.us-east-1.elasticbeanstalk.com:8080/api/items";

    afterEach(() => { // Cleanup after each test
        jest.clearAllMocks();   
    });

    it('should fetch all items', async () => { // Checks if all items are fetched
        const mockData = [{ id: 1, name: 'Item A' }]; // Mock data - one item with ID 1 and name 'Item A'
        axios.get.mockResolvedValue({ data: mockData });

        const result = await itemService.getAll(); // Get all items from the service

        expect(axios.get).toHaveBeenCalledWith(API_URL);
        expect(result.data).toEqual(mockData); // Check if the data fetched is equal to the mock data
    });

    it('should fetch an item by ID', async () => { // Checks if an item is fetched by ID
        const mockData = { id: 1, name: 'Item A' };
        axios.get.mockResolvedValue({ data: mockData }); // Mock the axios get method to return the mock data

        const result = await itemService.get(1); // Get the item with ID 1

        expect(axios.get).toHaveBeenCalledWith(`${API_URL}/1`);
        expect(result.data).toEqual(mockData); // Check if the data fetched is equal to the mock data - ID 1, name 'Item A'
    });

    it('should create a new item', async () => { // Checks if a new item is created
        const newItem = { name: 'Item A' }; 
        const mockData = { id: 1, ...newItem };
        axios.post.mockResolvedValue({ data: mockData }); // Mock the axios post method to return the mock data

        const result = await itemService.create(newItem); // Create a new item with the name 'Item A'

        expect(axios.post).toHaveBeenCalledWith(API_URL, newItem, { // Check if the post method is called with the new item
            headers: {
                'Content-Type': 'application/json'
            }
        });
        expect(result.data).toEqual(mockData); // Check if the data fetched is equal to the mock data - ID 1 name 'Item A' as expected
    });

    it('should update an item by ID', async () => { // Checks if an item is updated by ID
        const updatedItem = { name: 'Updated Item A' }; 
        const mockData = { id: 1, ...updatedItem };
        axios.put.mockResolvedValue({ data: mockData }); // Mock the axios put method to return the mock data

        const result = await itemService.update(1, updatedItem); // Update the item with ID 1 with the updated name 'Updated Item A'

        expect(axios.put).toHaveBeenCalledWith(`${API_URL}/1`, updatedItem, { // Check if the put method is called with the updated item
            headers: {
                'Content-Type': 'application/json'
            }
        });

        expect(result.data).toEqual(mockData); // Check if the data fetched is equal to the mock data - ID 1, name 'Updated Item A'
    });

    it('should delete an item by ID', async () => { // Checks if an item is deleted based on ID
        axios.delete.mockResolvedValue({});
        const result = await itemService.delete(1); // Deleting the item with ID 1

        expect(axios.delete).toHaveBeenCalledWith(`${API_URL}/1`); // Check if the delete method is called with the item ID 1
        expect(result).toEqual({}); // Assertion check
    });
});