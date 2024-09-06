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

const mockItems = [ // Properties for the mock items
    { id: 1, name: 'Item A', description: 'Description A', quantity: 10, sizeInCubicFt: 5, warehouse: { id: 1, name: 'Warehouse 1' } },
    { id: 2, name: 'Item B', description: 'Description B', quantity: 20, sizeInCubicFt: 10, warehouse: { id: 2, name: 'Warehouse 2' } },
];

const mockWarehouses = [ // Properties for the mock warehouses
    { id: 1, name: 'Warehouse 1' },
    { id: 2, name: 'Warehouse 2' },
];

describe('ItemList', () => { // ItemList is the list of item, displayed in a card format
    beforeEach(() => { // We mock the services and their responses before each test
        ItemService.getAll.mockResolvedValue({ data: mockItems });
        WarehouseService.getAll.mockResolvedValue({ data: mockWarehouses });
    });

    afterEach(() => {
        jest.clearAllMocks(); // Clear mock calls after each test
    });

    it('should render the page title', async () => { // Checks if the page title is rendered - should be Items
        await act(async () => {
            render(
                <BrowserRouter>
                    <ItemList />
                </BrowserRouter>
            );
        });
    
        await waitFor(() => {
            expect(screen.getByText('Items')).toBeInTheDocument(); // Looking for Items text
        });
    });

    it('should render items and warehouses', async () => { // Checks if the items and warehouses listings are rendered
        await act(async () => {
            render(
                <BrowserRouter>
                    <ItemList />
                </BrowserRouter>
            );
        });

        await waitFor(() => { // Checking for each item and warehouse from the mock data
            expect(screen.getByText('Item A')).toBeInTheDocument();
            expect(screen.getByText('Item B')).toBeInTheDocument();
            expect(screen.getByText('Warehouse 1')).toBeInTheDocument();
            expect(screen.getByText('Warehouse 2')).toBeInTheDocument();
        });
    });

    it('should sort items by name', async () => { // Checks if the items are sorted by name
        await act(async () => {
            render(
                <BrowserRouter>
                    <ItemList />
                </BrowserRouter>
            );
        });

        await waitFor(() => {
            const options = screen.getAllByRole('option'); // Finds all the options, 
            fireEvent.change(screen.getByLabelText('Sort By:'), { target: { value: 'name' } }); // Changes the sorting criteria to name,
            expect(options[0].selected).toBeTruthy(); // And checks if the first option is selected
        });
    });

    it('should filter items by warehouse', async () => { // Checks if the items are filtered by warehouse
        await act(async () => {
            render(
                <BrowserRouter>
                    <ItemList />
                </BrowserRouter>
            );
        });

        await waitFor(() => { 
            fireEvent.change(screen.getByLabelText('Filter by Warehouse:'), { target: { value: '1' } }); // Changes the warehouse filter to warehouse 1
            expect(screen.getByText('Item A')).toBeInTheDocument(); // Item A should be the only item displayed from the mock data
            expect(screen.queryByText('Item B')).not.toBeInTheDocument(); // Item B should not be displayed, as it is in warehouse 2
        });
    });

    it('should navigate to the edit item page', async () => { // Checks if the edit item page is navigated to
        await act(async () => {
            render(
                <BrowserRouter>
                    <ItemList />
                </BrowserRouter>
            );
        });

        await waitFor(() => {
            const editButtons = screen.getAllByText('Edit'); // Finds all the edit buttons
            fireEvent.click(editButtons[0]); // Click the first Edit button - should navigate to the edit item page
            expect(mockNavigate).toHaveBeenCalledWith('/edit-item/1'); // Checks if the navigation is to the edit item page with the correct item ID
        });
    });

    it('should delete an item', async () => { // Checks if an item is deleted from the list 
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

    it('should sort items by quantity', async () => { // Checks if the items are sorted by quantity
        await act(async () => {
            render(
                <BrowserRouter>
                    <ItemList />
                </BrowserRouter>
            );
        });

        await waitFor(() => { // Waits for the items to be rendered before sorting
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

    it('should sort items by name', async () => { // Checks if the items are sorted by name
        await act(async () => {
            render(
                <BrowserRouter>
                    <ItemList />
                </BrowserRouter>
            );
        });
    
        await waitFor(() => { // Waits for the items to be rendered before sorting them
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

    it('should sort items by item size', async () => { // Checks if the items are sorted by size
        await act(async () => {
            render(
                <BrowserRouter>
                    <ItemList />
                </BrowserRouter>
            );
        });
    
        await waitFor(() => { // Waits for the items to be rendered before sorting them
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

    it('should sort items by total size', async () => { // Checks if the items are sorted by total size
        await act(async () => {
            render(
                <BrowserRouter>
                    <ItemList />
                </BrowserRouter>
            );
        });
    
        await waitFor(() => { // Waits for the items to be rendered before sorting them
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

    it('should filter items by all warehouses', async () => { // Checks if the items are filtered by all warehouses
        await act(async () => {
            render(
                <BrowserRouter>
                    <ItemList />
                </BrowserRouter>
            );
        });
    
        await waitFor(() => { // Waits for the items to be rendered before filtering them
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

    it('should filter items by warehouse', async () => { // Checks if the items are filtered by warehouse
        await act(async () => {
            render(
                <BrowserRouter>
                    <ItemList />
                </BrowserRouter>
            );
        });
    
        await waitFor(() => { // Waits for the items to be rendered before filtering them
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

    it('should handle empty item list', async () => { // Checks if the empty item list is handled
        ItemService.getAll.mockResolvedValue({ data: [] });
        WarehouseService.getAll.mockResolvedValue({ data: mockWarehouses });
    
        await act(async () => {
            render(
                <BrowserRouter>
                    <ItemList />
                </BrowserRouter>
            );
        });
    
        await waitFor(() => { // Waits for the items to be rendered - should be none, as the list is empty (branch test coverage case)
            expect(screen.getByText('Items')).toBeInTheDocument();
            expect(screen.queryByText('Item A')).not.toBeInTheDocument();
            expect(screen.queryByText('Item B')).not.toBeInTheDocument();
        });
    });

    const mockItemsWithMissingFields = [ // Mock items with missing fields for the next test
        { id: 1, name: '', description: '', quantity: 0, sizeInCubicFt: 0, warehouse: { id: 1, name: 'Warehouse 1' } },
        { id: 2, name: 'Item B', description: 'Description B', quantity: 20, sizeInCubicFt: 10, warehouse: { id: 2, name: 'Warehouse 2' } },
    ];
    
    it('should handle items with missing fields', async () => { // Checks if the items with missing fields are handled - should display "Unnamed Item"
        ItemService.getAll.mockResolvedValue({ data: mockItemsWithMissingFields });
        WarehouseService.getAll.mockResolvedValue({ data: mockWarehouses });
    
        await act(async () => {
            render(
                <BrowserRouter>
                    <ItemList />
                </BrowserRouter>
            );
        });
    
        await waitFor(() => { // Waits for the items to be rendered
            expect(screen.getByText('Unnamed Item')).toBeInTheDocument();
            expect(screen.getByText('Item B')).toBeInTheDocument();
        });
    });
});