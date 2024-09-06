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

const mockWarehouses = [ // Mocking a list of warehouses with properties listed below
    { id: 1, name: 'Warehouse A', usedCapacity: 50, capacity: 100 },
    { id: 2, name: 'Warehouse B', usedCapacity: 30, capacity: 100 },
];

describe('WarehouseList', () => { // WarehouseList is the page that lists all the warehouses
    beforeEach(() => {
        WarehouseService.getAll.mockResolvedValue({ data: mockWarehouses });
    });

    afterEach(() => {
        jest.clearAllMocks(); // Clear mock calls
    });

    it('should render the page title', async () => { // Checks if the page title is rendered
        await act(async () => {
            render(
                <BrowserRouter>
                    <WarehouseList />
                </BrowserRouter>
            );
        });

        await waitFor(() => { // Wait for the page title to be rendered
            expect(screen.getByText('Warehouses')).toBeInTheDocument();
        });
    });

    it('should render warehouses', async () => { // Checks if the warehouses are rendered
        await act(async () => {
            render(
                <BrowserRouter>
                    <WarehouseList />
                </BrowserRouter>
            );
        });

        await waitFor(() => { // Wait for the warehouses to be rendered
            expect(screen.getByText('Warehouse A')).toBeInTheDocument(); // Check if Warehouse A renders and for Warehouse B too
            expect(screen.getByText('Warehouse B')).toBeInTheDocument();
        });
    });

    it('should sort warehouses by name', async () => { // Checks if the warehouses are sorted by name
        await act(async () => {
            render(
                <BrowserRouter>
                    <WarehouseList />
                </BrowserRouter>
            );
        });

        await waitFor(() => { // Wait for the warehouses to be rendered
            fireEvent.change(screen.getByLabelText('Sort By:'), { target: { value: 'name' } }); // Sort the warehouses by name
            const warehouses = screen.getAllByRole('heading', { level: 5 }); // Get all the warehouses, which are headings for their names
            expect(warehouses[0]).toHaveTextContent('Warehouse A'); // Check if Warehouse A is the first warehouse
            expect(warehouses[1]).toHaveTextContent('Warehouse B'); // .. and Warehouse B is the second
        });
    });

    it('should sort warehouses by utilization', async () => { // Checks if the warehouses are sorted by utilization
        await act(async () => {
            render(
                <BrowserRouter>
                    <WarehouseList />
                </BrowserRouter>
            );
        });

        await waitFor(() => { // Wait for the warehouses to be rendered
            fireEvent.change(screen.getByLabelText('Sort By:'), { target: { value: 'utilization' } }); // Sort the warehouses by utilization
            const warehouses = screen.getAllByRole('heading', { level: 5 });
            expect(warehouses[0]).toHaveTextContent('Warehouse A'); // Higher utilization
            expect(warehouses[1]).toHaveTextContent('Warehouse B'); // Lower utilization - should be second
        });
    });

    it('should navigate to the edit warehouse page', async () => { // Checks if the user can navigate to the edit warehouse page
        await act(async () => {
            render(
                <BrowserRouter>
                    <WarehouseList />
                </BrowserRouter>
            );
        });

        await waitFor(() => { // Wait for the warehouses to be rendered, then click the edit button for the first warehouse
            const editButtons = screen.getAllByText('Edit');
            fireEvent.click(editButtons[0]); // Click the first Edit button
            expect(mockNavigate).toHaveBeenCalledWith('/edit-warehouse/1'); // Check if the user is navigated to the edit warehouse page
        });
    });

    it('should not delete a warehouse if the user cancels the confirmation', async () => { // Checks if the user cancels the confirmation to delete a warehouse
        window.confirm = jest.fn(() => false);
    
        await act(async () => {
            render(
                <BrowserRouter>
                    <WarehouseList />
                </BrowserRouter>
            );
        });
    
        await waitFor(() => { // Wait for the warehouses to be rendered
            expect(screen.getByText('Warehouse A')).toBeInTheDocument();
            expect(screen.getByText('Warehouse B')).toBeInTheDocument();
        });
    
        const deleteButtons = screen.getAllByText('Delete'); // Find the delete button for the first warehouse
        fireEvent.click(deleteButtons[0]); // Simulate clicking the first Delete button - should not delete the warehouse, since window.confirm is false implicitly
    
        await waitFor(() => { // Wait for the warehouses to be rendered, then check if they are still there (should be)
            expect(screen.getByText('Warehouse A')).toBeInTheDocument();
            expect(screen.getByText('Warehouse B')).toBeInTheDocument();
        });
    });
    
    it('should delete a warehouse', async () => { // Checks if the warehouse is deleted when the user confirms the deletion
        // Mock the delete method
        WarehouseService.delete.mockResolvedValue({});

        await act(async () => {
            render(
                <BrowserRouter>
                    <WarehouseList />
                </BrowserRouter>
            );
        });

        await waitFor(() => { // Warehouses for deletion
            expect(screen.getByText('Warehouse A')).toBeInTheDocument();
            expect(screen.getByText('Warehouse B')).toBeInTheDocument();
        });

        // Mock the confirmation dialog
        window.confirm = jest.fn(() => true);

        // Find the delete button for the first warehouse
        const deleteButtons = screen.getAllByText('Delete');
        fireEvent.click(deleteButtons[0]); // Click the first Delete button

        await waitFor(() => { // A should be deleted, B should still be there
            expect(screen.queryByText('Warehouse A')).not.toBeInTheDocument();
        });
    });
});