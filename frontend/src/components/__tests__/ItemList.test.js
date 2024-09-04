import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { BrowserRouter, useNavigate } from 'react-router-dom';
import { act } from 'react';
import ItemList from '../ItemList';
import ItemService from '../../services/itemService';
import WarehouseService from '../../services/warehouseService';

// Service mocks
jest.mock('../../services/itemService.js');
jest.mock('../../services/warehouseService.js');

// Mock useNavigate
const mockNavigate = jest.fn();
jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),
    useNavigate: () => mockNavigate,
}));

const mockItems = [
    { id: 1, name: 'Item A', description: 'Description A', quantity: 10, sizeInCubicFt: 5, warehouse: { id: 1, name: 'Warehouse 1' } },
    { id: 2, name: 'Item B', description: 'Description B', quantity: 20, sizeInCubicFt: 10, warehouse: { id: 2, name: 'Warehouse 2' } },
];

const mockWarehouses = [
    { id: 1, name: 'Warehouse 1' },
    { id: 2, name: 'Warehouse 2' },
];

describe('ItemList', () => {
    beforeEach(() => {
        ItemService.getAll.mockResolvedValue({ data: mockItems });
        WarehouseService.getAll.mockResolvedValue({ data: mockWarehouses });
    });

    afterEach(() => {
        jest.clearAllMocks(); // Clear mock calls
    });

    it('should render the page title', async () => {
        await act(async () => {
            render(
                <BrowserRouter>
                    <ItemList />
                </BrowserRouter>
            );
        });
    
        await waitFor(() => {
            expect(screen.getByText('Items')).toBeInTheDocument();
        });
    });

    it('should render items and warehouses', async () => {
        await act(async () => {
            render(
                <BrowserRouter>
                    <ItemList />
                </BrowserRouter>
            );
        });

        await waitFor(() => {
            expect(screen.getByText('Item A')).toBeInTheDocument();
            expect(screen.getByText('Item B')).toBeInTheDocument();
            expect(screen.getByText('Warehouse 1')).toBeInTheDocument();
            expect(screen.getByText('Warehouse 2')).toBeInTheDocument();
        });
    });

    it('should sort items by name', async () => {
        await act(async () => {
            render(
                <BrowserRouter>
                    <ItemList />
                </BrowserRouter>
            );
        });

        await waitFor(() => {
            const options = screen.getAllByRole('option');
            fireEvent.change(screen.getByLabelText('Sort By:'), { target: { value: 'name' } });
            expect(options[0].selected).toBeTruthy();
        });
    });

    it('should filter items by warehouse', async () => {
        await act(async () => {
            render(
                <BrowserRouter>
                    <ItemList />
                </BrowserRouter>
            );
        });

        await waitFor(() => {
            fireEvent.change(screen.getByLabelText('Filter by Warehouse:'), { target: { value: '1' } });
            expect(screen.getByText('Item A')).toBeInTheDocument();
            expect(screen.queryByText('Item B')).not.toBeInTheDocument();
        });
    });

    it('should navigate to the edit item page', async () => {
        await act(async () => {
            render(
                <BrowserRouter>
                    <ItemList />
                </BrowserRouter>
            );
        });

        await waitFor(() => {
            const editButtons = screen.getAllByText('Edit');
            fireEvent.click(editButtons[0]); // Click the first Edit button
            expect(mockNavigate).toHaveBeenCalledWith('/edit-item/1');
        });
    });

    it('should delete an item', async () => {
        // Mock the delete method
        ItemService.delete.mockResolvedValue({});
    
        await act(async () => {
            render(
                <BrowserRouter>
                    <ItemList />
                </BrowserRouter>
            );
        });
    
        await waitFor(() => {
            // Ensure items are rendered
            expect(screen.getByText('Item A')).toBeInTheDocument();
            expect(screen.getByText('Item B')).toBeInTheDocument();
        });
    
        // Mock the confirmation dialog
        window.confirm = jest.fn(() => true);
    
        // Find the delete button for the first item
        const deleteButtons = screen.getAllByText('Delete');
        fireEvent.click(deleteButtons[0]); // Click the first Delete button
    
        await waitFor(() => {
            // Verify that the item is removed from the list
            expect(screen.queryByText('Item A')).not.toBeInTheDocument();
        });
    });

    it('should sort items by quantity', async () => {
        await act(async () => {
            render(
                <BrowserRouter>
                    <ItemList />
                </BrowserRouter>
            );
        });

        await waitFor(() => {
            expect(screen.getByText('Item A')).toBeInTheDocument();
            expect(screen.getByText('Item B')).toBeInTheDocument();
        })

        // Change criteria to quantity
        await act(async () => {
            fireEvent.change(screen.getByLabelText('Sort By:'), { target: { value: 'quantity' } });
        });

        await waitFor(() => {
            const items = screen.getAllByRole('heading', { level: 5 });
            // Verify that items are sorted by quantity
            expect(items[0]).toHaveTextContent('Item B'); // Item B has a higher quantity
            expect(items[1]).toHaveTextContent('Item A');
        });
    });

    it('should sort items by name', async () => {
        await act(async () => {
            render(
                <BrowserRouter>
                    <ItemList />
                </BrowserRouter>
            );
        });
    
        await waitFor(() => {
            expect(screen.getByText('Item A')).toBeInTheDocument();
            expect(screen.getByText('Item B')).toBeInTheDocument();
        });
    
        // Change criteria to name
        await act(async () => {
            fireEvent.change(screen.getByLabelText('Sort By:'), { target: { value: 'name' } });
        });
    
        await waitFor(() => {
            const items = screen.getAllByRole('heading', { level: 5 });
            // Verify that items are sorted by name
            expect(items[0]).toHaveTextContent('Item A'); // should be first
            expect(items[1]).toHaveTextContent('Item B');
        });
    });

    it('should sort items by item size', async () => {
        await act(async () => {
            render(
                <BrowserRouter>
                    <ItemList />
                </BrowserRouter>
            );
        });
    
        await waitFor(() => {
            expect(screen.getByText('Item A')).toBeInTheDocument();
            expect(screen.getByText('Item B')).toBeInTheDocument();
        });
    
        // Change criteria to size
        await act(async () => {
            fireEvent.change(screen.getByLabelText('Sort By:'), { target: { value: 'size' } });
        });
    
        await waitFor(() => {
            const items = screen.getAllByRole('heading', { level: 5 });
            // Verify that items are sorted by size
            expect(items[0]).toHaveTextContent('Item B'); // Item B has a larger size
            expect(items[1]).toHaveTextContent('Item A');
        });
    });

    it('should sort items by total size', async () => {
        await act(async () => {
            render(
                <BrowserRouter>
                    <ItemList />
                </BrowserRouter>
            );
        });
    
        await waitFor(() => {
            expect(screen.getByText('Item A')).toBeInTheDocument();
            expect(screen.getByText('Item B')).toBeInTheDocument();
        });
    
        // Change criteria to total size
        await act(async () => {
            fireEvent.change(screen.getByLabelText('Sort By:'), { target: { value: 'totalSize' } });
        });
    
        await waitFor(() => {
            const items = screen.getAllByRole('heading', { level: 5 });
            // Verify that items are sorted by total size
            expect(items[0]).toHaveTextContent('Item B'); // Item B has a larger total size
            expect(items[1]).toHaveTextContent('Item A');
        });
    });

    it('should filter items by all warehouses', async () => {
        await act(async () => {
            render(
                <BrowserRouter>
                    <ItemList />
                </BrowserRouter>
            );
        });
    
        await waitFor(() => {
            expect(screen.getByText('Item A')).toBeInTheDocument();
            expect(screen.getByText('Item B')).toBeInTheDocument();
        });
    
        // Change filter to all warehouses
        await act(async () => {
            fireEvent.change(screen.getByLabelText('Filter by Warehouse:'), { target: { value: 'all' } });
        });
    
        await waitFor(() => {
            // Verify that all items are displayed
            expect(screen.getByText('Item A')).toBeInTheDocument();
            expect(screen.getByText('Item B')).toBeInTheDocument();
        });
    });

    it('should filter items by warehouse', async () => {
        await act(async () => {
            render(
                <BrowserRouter>
                    <ItemList />
                </BrowserRouter>
            );
        });
    
        await waitFor(() => {
            expect(screen.getByText('Item A')).toBeInTheDocument();
            expect(screen.getByText('Item B')).toBeInTheDocument();
        });
    
        // Change filter to warehouse 1
        await act(async () => {
            fireEvent.change(screen.getByLabelText('Filter by Warehouse:'), { target: { value: '1' } });
        });
    
        await waitFor(() => {
            // Verify that only items from warehouse 1 are displayed
            expect(screen.getByText('Item A')).toBeInTheDocument();
            expect(screen.queryByText('Item B')).not.toBeInTheDocument();
        });
    });

    it('should handle empty item list', async () => {
        ItemService.getAll.mockResolvedValue({ data: [] });
        WarehouseService.getAll.mockResolvedValue({ data: mockWarehouses });
    
        await act(async () => {
            render(
                <BrowserRouter>
                    <ItemList />
                </BrowserRouter>
            );
        });
    
        await waitFor(() => {
            expect(screen.getByText('Items')).toBeInTheDocument();
            expect(screen.queryByText('Item A')).not.toBeInTheDocument();
            expect(screen.queryByText('Item B')).not.toBeInTheDocument();
        });
    });

    const mockItemsWithMissingFields = [
        { id: 1, name: '', description: '', quantity: 0, sizeInCubicFt: 0, warehouse: { id: 1, name: 'Warehouse 1' } },
        { id: 2, name: 'Item B', description: 'Description B', quantity: 20, sizeInCubicFt: 10, warehouse: { id: 2, name: 'Warehouse 2' } },
    ];
    
    it('should handle items with missing fields', async () => {
        ItemService.getAll.mockResolvedValue({ data: mockItemsWithMissingFields });
        WarehouseService.getAll.mockResolvedValue({ data: mockWarehouses });
    
        await act(async () => {
            render(
                <BrowserRouter>
                    <ItemList />
                </BrowserRouter>
            );
        });
    
        await waitFor(() => {
            expect(screen.getByText('Unnamed Item')).toBeInTheDocument();
            expect(screen.getByText('Item B')).toBeInTheDocument();
        });
    });
});