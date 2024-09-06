import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import WarehouseForm from '../WarehouseForm';
import WarehouseService from '../../services/warehouseService';
import { act } from 'react';

// Service mocks
jest.mock('../../services/warehouseService.js');

// Mock useParams
jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),
    useParams: () => ({ id: '1' }),
}));

const mockWarehouse = { // Mocking a warehouse with properties listed below
    id: 1,
    name: 'Warehouse A',
    capacity: 100,
    usedCapacity: 50,
};

describe('WarehouseForm', () => { // WarehouseForm is the form for adding and editing warehouses
    beforeEach(() => { // Before each test, mock the services and their responses
        WarehouseService.get.mockResolvedValue({ data: mockWarehouse });
        WarehouseService.update.mockResolvedValue({});
        WarehouseService.create.mockResolvedValue({});
    });

    afterEach(() => { // Cleanup after each test
        jest.clearAllMocks();
    });

    it('should render the form fields', async () => { // Checks if the form fields are rendered, e.g. name, capacity, used capacity
        await act(async () => {
        render(
            <BrowserRouter>
            <WarehouseForm onSave={jest.fn()} />
            </BrowserRouter>
        );
        });

        await waitFor(() => { // Wait for the form fields to be rendered, then check if they are rendered as expected
        expect(screen.getByLabelText('Name')).toBeInTheDocument();
        expect(screen.getByLabelText('Capacity')).toBeInTheDocument();
        expect(screen.getByText('Used Capacity')).toBeInTheDocument();
        });
    });

    it('should fetch and populate warehouse data when editing', async () => { // Checks if the warehouse data is fetched and populated when editing
        await act(async () => {
        render(
            <BrowserRouter>
            <WarehouseForm onSave={jest.fn()} />
            </BrowserRouter>
        );
        });

        await waitFor(() => { // Wait for the form fields to be rendered - name, capacity, used capacity
        expect(screen.getByDisplayValue('Warehouse A')).toBeInTheDocument();
        expect(screen.getByDisplayValue('100')).toBeInTheDocument();
        expect(screen.getByText('50%')).toBeInTheDocument();
        });
    });

    it('should display validation errors', async () => { // Checks if validation errors are displayed
        await act(async () => {
        render(
            <BrowserRouter>
            <WarehouseForm onSave={jest.fn()} />
            </BrowserRouter>
        );
        });

        // Submit the form without entering a name, capacity, or used capacity
        fireEvent.change(screen.getByLabelText('Capacity'), { target: { value: '0' } });
        fireEvent.submit(screen.getByRole('button', { name: /update/i }));

        await waitFor(() => { // Wait for the validation errors to be displayed, then check if they are displayed as expected
        expect(screen.getByText('Capacity must be positive.')).toBeInTheDocument();
        });
    });

    it('should submit the form and call onSave', async () => { // Checks if the form is submitted and onSave is called
        const mockOnSave = jest.fn(); // Mocking the onSave function
        await act(async () => {
        render(
            <BrowserRouter>
            <WarehouseForm onSave={mockOnSave} />
            </BrowserRouter>
        );
        });

        // Changing the capacity, then submitting the form with the valid data
        fireEvent.change(screen.getByLabelText('Capacity'), { target: { value: '100' } });
        fireEvent.submit(screen.getByRole('button', { name: /update/i }));

        await waitFor(() => { // Wait for the form to be submitted, then check if the onSave function was called
        expect(WarehouseService.update).toHaveBeenCalledWith('1', expect.any(Object));
        expect(mockOnSave).toHaveBeenCalled();
        });
    });

    it('should display validation error when used capacity exceeds capacity', async () => { // Checks if a validation error is displayed when used capacity exceeds capacity
        // Mocking `useParams` to return an id of 1
        jest.mock('react-router-dom', () => ({
            ...jest.requireActual('react-router-dom'),
            useParams: () => ({ id: '1' }),
        }));
    
        const mockWarehouse = { // Mocking a warehouse with invalid used capacity
            id: 1,
            name: 'Warehouse A',
            capacity: 100, // Initial capacity
            usedCapacity: 150, // Exceeds the initial capacity
        };
    
        WarehouseService.get.mockResolvedValue({ data: mockWarehouse }); // Mocking the get method to return the mock warehouse
    
        await act(async () => { // Rendering the WarehouseForm component
            render(
                <BrowserRouter>
                    <WarehouseForm onSave={jest.fn()} />
                </BrowserRouter>
            );
        });
    
        await waitFor(() => { // Waiting for the form to be rendered, then checking if the validation error is displayed
            fireEvent.change(screen.getByLabelText('Capacity'), { target: { value: '100' } });
            fireEvent.submit(screen.getByRole('button', { name: /update/i }));
    
            expect(screen.getByText('Used capacity exceeds new capacity.')).toBeInTheDocument(); // Error text assertion check
        });
    });   

    it('should display an error if capacity exceeds MAX_SAFE_INTEGER', async () => { // MAX_SAFE_INTEGER is the maximum safe integer value, what we use as a max cap
        await act(async () => {
            render(
                <BrowserRouter>
                    <WarehouseForm onSave={jest.fn()} />
                </BrowserRouter>
            );
        });
    
        // Changing the capacity to a value that exceeds MAX_SAFE_INTEGER, then submitting the form
        fireEvent.change(screen.getByLabelText('Capacity'), { target: { value: `${Number.MAX_SAFE_INTEGER + 1}` } });
        fireEvent.submit(screen.getByRole('button', { name: /update/i }));
    
        await waitFor(() => { // Checking if the error message is displayed when form is submitted
            expect(screen.getByText('Capacity is too large.')).toBeInTheDocument();
        });
    });
});