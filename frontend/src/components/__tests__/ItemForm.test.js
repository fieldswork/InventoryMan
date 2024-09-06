import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import ItemForm from '../ItemForm';
import ItemService from '../../services/itemService';
import WarehouseService from '../../services/warehouseService';
import { act } from 'react';

// Service mocks for itemService and warehouseService
jest.mock('../../services/itemService.js');
jest.mock('../../services/warehouseService.js');

// Mock useParams - gets the id from the URL
jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),
    useParams: () => ({ id: '1' }),
}));

// Mocking an item with properties listed below
const mockItem = {
    id: 1,
    name: 'Item A',
    description: 'Description A',
    quantity: 10,
    sizeInCubicFt: 5,
    warehouse: { id: 1, name: 'Warehouse 1' },
};

// Mocking a list of warehouses
const mockWarehouses = [
    { id: 1, name: 'Warehouse 1' },
    { id: 2, name: 'Warehouse 2' },
];

describe('ItemForm', () => { // ItemForm is the form for adding and editing items
    beforeEach(() => { // Before each test, mock the services and their responses
        ItemService.get.mockResolvedValue({ data: mockItem });
        WarehouseService.getAll.mockResolvedValue({ data: mockWarehouses });
        WarehouseService.get.mockResolvedValue({ data: { capacity: 100, usedCapacity: 50 } });
        ItemService.update.mockResolvedValue({});
        ItemService.create.mockResolvedValue({});
    });

    afterEach(() => { // Cleanup after each test
        jest.clearAllMocks();
    });

    it('should render the form fields', async () => { // Checks if the form fields are rendered, e.g. name, description, quantity, size, warehouse
        await act(async () => {
            render(
                <BrowserRouter>
                    <ItemForm onSave={jest.fn()} />
                </BrowserRouter>
            );
        });

        await waitFor(() => { // Wait for the form fields to be rendered
            expect(screen.getByLabelText('Name')).toBeInTheDocument();
            expect(screen.getByLabelText('Description')).toBeInTheDocument();
            expect(screen.getByLabelText('Quantity')).toBeInTheDocument();
            expect(screen.getByLabelText('Size (in cubic feet)')).toBeInTheDocument();
            expect(screen.getByLabelText('Warehouse')).toBeInTheDocument();
        });
    });

    it('should fetch and populate item data when editing', async () => { // Checks if the item data is fetched and populated when editing
        await act(async () => {
            render(
                <BrowserRouter>
                    <ItemForm onSave={jest.fn()} />
                </BrowserRouter>
            );
        });

        await waitFor(() => { // This is the data that is to populate the form fields when editing
            expect(screen.getByDisplayValue('Item A')).toBeInTheDocument();
            expect(screen.getByDisplayValue('Description A')).toBeInTheDocument();
            expect(screen.getByDisplayValue('10')).toBeInTheDocument();
            expect(screen.getByDisplayValue('5')).toBeInTheDocument();
            expect(screen.getByLabelText('Warehouse').value).toBe('1');
        });
    });

    it('should display validation errors', async () => { // Testing validation for quantity and size
        await act(async () => {
            render(
                <BrowserRouter>
                    <ItemForm onSave={jest.fn()} />
                </BrowserRouter>
            );
        });

        // Changing quantity and size to 0, hitting the button to attempt to update the item
        fireEvent.change(screen.getByLabelText('Quantity'), { target: { value: '0' } });
        fireEvent.change(screen.getByLabelText('Size (in cubic feet)'), { target: { value: '0' } });
        fireEvent.submit(screen.getByRole('button', { name: /update/i }));

        await waitFor(() => {
            expect(screen.getByText('Quantity and size must be at least 1.')).toBeInTheDocument(); // Since 0 size/quantity is invalid, this error message should appear
        });
    });

    it('should submit the form and call onSave', async () => { // Testing the form submission and the onSave function for a valid item
        const mockOnSave = jest.fn(); // Mocking the onSave function
        await act(async () => {
            render(
                <BrowserRouter>
                    <ItemForm onSave={mockOnSave} />
                </BrowserRouter>
            );
        });

        // Changing the quantity, size, and warehouse, then submitting the form with the valid data
        fireEvent.change(screen.getByLabelText('Quantity'), { target: { value: '10' } });
        fireEvent.change(screen.getByLabelText('Size (in cubic feet)'), { target: { value: '5' } });
        fireEvent.change(screen.getByLabelText('Warehouse'), { target: { value: '1' } });
        fireEvent.submit(screen.getByRole('button', { name: /update/i }));

        await waitFor(() => { // Wait for the form to be submitted, then check if the onSave function was called
            expect(ItemService.update).toHaveBeenCalledWith('1', expect.any(Object));
            expect(mockOnSave).toHaveBeenCalled();
        });
    });
});
