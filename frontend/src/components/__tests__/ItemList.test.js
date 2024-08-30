import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { BrowserRouter, useNavigate } from 'react-router-dom';
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
        render(
            <BrowserRouter>
                <ItemList />
            </BrowserRouter>
        );

        await waitFor(() => {
            expect(screen.getByText('Items')).toBeInTheDocument();
        });
    });

    it('should render items and warehouses', async () => {
        render(
            <BrowserRouter>
                <ItemList />
            </BrowserRouter>
        );

        await waitFor(() => {
            expect(screen.getByText('Item A')).toBeInTheDocument();
            expect(screen.getByText('Item B')).toBeInTheDocument();
            expect(screen.getByText('Warehouse 1')).toBeInTheDocument();
            expect(screen.getByText('Warehouse 2')).toBeInTheDocument();
        });
    });

    it('should sort items by name', async () => {
        render(
            <BrowserRouter>
                <ItemList />
            </BrowserRouter>
        );

        await waitFor(() => {
            const options = screen.getAllByRole('option');
            fireEvent.change(screen.getByLabelText('Sort By:'), { target: { value: 'name' } });
            expect(options[0].selected).toBeTruthy();
        });
    });

    it('should filter items by warehouse', async () => {
        render(
            <BrowserRouter>
                <ItemList />
            </BrowserRouter>
        );

        await waitFor(() => {
            fireEvent.change(screen.getByLabelText('Filter by Warehouse:'), { target: { value: '1' } });
            expect(screen.getByText('Item A')).toBeInTheDocument();
            expect(screen.queryByText('Item B')).not.toBeInTheDocument();
        });
    });

    it('should navigate to the edit item page', async () => {
        render(
            <BrowserRouter>
                <ItemList />
            </BrowserRouter>
        );

        await waitFor(() => {
            const editButtons = screen.getAllByText('Edit');
            fireEvent.click(editButtons[0]); // Click the first Edit button
            expect(mockNavigate).toHaveBeenCalledWith('/edit-item/1');
        });
    });

    // should delete an item

});