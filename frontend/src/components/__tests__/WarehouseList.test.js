import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import { act } from 'react-dom/test-utils';
import WarehouseList from '../WarehouseList';
import WarehouseService from '../../services/warehouseService';

// Service mocks
jest.mock('../../services/warehouseService.js');

// Mock useNavigate
const mockNavigate = jest.fn();
jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),
    useNavigate: () => mockNavigate,
}));

const mockWarehouses = [
    { id: 1, name: 'Warehouse A', usedCapacity: 50, capacity: 100 },
    { id: 2, name: 'Warehouse B', usedCapacity: 30, capacity: 100 },
];

describe('WarehouseList', () => {
    beforeEach(() => {
        WarehouseService.getAll.mockResolvedValue({ data: mockWarehouses });
    });

    afterEach(() => {
        jest.clearAllMocks(); // Clear mock calls
    });

    it('should render the page title', async () => {
        await act(async () => {
            render(
                <BrowserRouter>
                    <WarehouseList />
                </BrowserRouter>
            );
        });

        await waitFor(() => {
            expect(screen.getByText('Warehouses')).toBeInTheDocument();
        });
    });

    it('should render warehouses', async () => {
        await act(async () => {
            render(
                <BrowserRouter>
                    <WarehouseList />
                </BrowserRouter>
            );
        });

        await waitFor(() => {
            expect(screen.getByText('Warehouse A')).toBeInTheDocument();
            expect(screen.getByText('Warehouse B')).toBeInTheDocument();
        });
    });

    it('should sort warehouses by name', async () => {
        await act(async () => {
            render(
                <BrowserRouter>
                    <WarehouseList />
                </BrowserRouter>
            );
        });

        await waitFor(() => {
            fireEvent.change(screen.getByLabelText('Sort By:'), { target: { value: 'name' } });
            const warehouses = screen.getAllByRole('heading', { level: 5 });
            expect(warehouses[0]).toHaveTextContent('Warehouse A');
            expect(warehouses[1]).toHaveTextContent('Warehouse B');
        });
    });

    it('should sort warehouses by utilization', async () => {
        await act(async () => {
            render(
                <BrowserRouter>
                    <WarehouseList />
                </BrowserRouter>
            );
        });

        await waitFor(() => {
            fireEvent.change(screen.getByLabelText('Sort By:'), { target: { value: 'utilization' } });
            const warehouses = screen.getAllByRole('heading', { level: 5 });
            expect(warehouses[0]).toHaveTextContent('Warehouse A'); // Higher utilization
            expect(warehouses[1]).toHaveTextContent('Warehouse B');
        });
    });

    it('should navigate to the edit warehouse page', async () => {
        await act(async () => {
            render(
                <BrowserRouter>
                    <WarehouseList />
                </BrowserRouter>
            );
        });

        await waitFor(() => {
            const editButtons = screen.getAllByText('Edit');
            fireEvent.click(editButtons[0]); // Click the first Edit button
            expect(mockNavigate).toHaveBeenCalledWith('/edit-warehouse/1');
        });
    });

    it('should delete a warehouse', async () => {
        // Mock the delete method
        WarehouseService.delete.mockResolvedValue({});

        await act(async () => {
            render(
                <BrowserRouter>
                    <WarehouseList />
                </BrowserRouter>
            );
        });

        await waitFor(() => {
            expect(screen.getByText('Warehouse A')).toBeInTheDocument();
            expect(screen.getByText('Warehouse B')).toBeInTheDocument();
        });

        // Mock the confirmation dialog
        window.confirm = jest.fn(() => true);

        // Find the delete button for the first warehouse
        const deleteButtons = screen.getAllByText('Delete');
        fireEvent.click(deleteButtons[0]); // Click the first Delete button

        await waitFor(() => {
            expect(screen.queryByText('Warehouse A')).not.toBeInTheDocument();
        });
    });
});