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

const mockWarehouse = {
    id: 1,
    name: 'Warehouse A',
    capacity: 100,
    usedCapacity: 50,
};

describe('WarehouseForm', () => {
    beforeEach(() => {
        WarehouseService.get.mockResolvedValue({ data: mockWarehouse });
        WarehouseService.update.mockResolvedValue({});
        WarehouseService.create.mockResolvedValue({});
    });

    afterEach(() => {
        jest.clearAllMocks();
    });

    it('should render the form fields', async () => {
        await act(async () => {
        render(
            <BrowserRouter>
            <WarehouseForm onSave={jest.fn()} />
            </BrowserRouter>
        );
        });

        await waitFor(() => {
        expect(screen.getByLabelText('Name')).toBeInTheDocument();
        expect(screen.getByLabelText('Capacity')).toBeInTheDocument();
        expect(screen.getByText('Used Capacity')).toBeInTheDocument();
        });
    });

    it('should fetch and populate warehouse data when editing', async () => {
        await act(async () => {
        render(
            <BrowserRouter>
            <WarehouseForm onSave={jest.fn()} />
            </BrowserRouter>
        );
        });

        await waitFor(() => {
        expect(screen.getByDisplayValue('Warehouse A')).toBeInTheDocument();
        expect(screen.getByDisplayValue('100')).toBeInTheDocument();
        expect(screen.getByText('50%')).toBeInTheDocument();
        });
    });

    it('should display validation errors', async () => {
        await act(async () => {
        render(
            <BrowserRouter>
            <WarehouseForm onSave={jest.fn()} />
            </BrowserRouter>
        );
        });

        fireEvent.change(screen.getByLabelText('Capacity'), { target: { value: '0' } });
        fireEvent.submit(screen.getByRole('button', { name: /update/i }));

        await waitFor(() => {
        expect(screen.getByText('Capacity must be positive.')).toBeInTheDocument();
        });
    });

    it('should submit the form and call onSave', async () => {
        const mockOnSave = jest.fn();
        await act(async () => {
        render(
            <BrowserRouter>
            <WarehouseForm onSave={mockOnSave} />
            </BrowserRouter>
        );
        });

        fireEvent.change(screen.getByLabelText('Capacity'), { target: { value: '100' } });
        fireEvent.submit(screen.getByRole('button', { name: /update/i }));

        await waitFor(() => {
        expect(WarehouseService.update).toHaveBeenCalledWith('1', expect.any(Object));
        expect(mockOnSave).toHaveBeenCalled();
        });
    });

    it('should display validation error when used capacity exceeds capacity', async () => {
        // Mocking `useParams` to return an id of 1
        jest.mock('react-router-dom', () => ({
            ...jest.requireActual('react-router-dom'),
            useParams: () => ({ id: '1' }),
        }));
    
        const mockWarehouse = {
            id: 1,
            name: 'Warehouse A',
            capacity: 100, // Initial capacity
            usedCapacity: 150, // Exceeds the initial capacity
        };
    
        WarehouseService.get.mockResolvedValue({ data: mockWarehouse });
    
        await act(async () => {
            render(
                <BrowserRouter>
                    <WarehouseForm onSave={jest.fn()} />
                </BrowserRouter>
            );
        });
    
        await waitFor(() => {
            fireEvent.change(screen.getByLabelText('Capacity'), { target: { value: '100' } });
            fireEvent.submit(screen.getByRole('button', { name: /update/i }));
    
            expect(screen.getByText('Used capacity exceeds new capacity.')).toBeInTheDocument();
        });
    });   

    it('should display an error if capacity exceeds MAX_SAFE_INTEGER', async () => {
        await act(async () => {
            render(
                <BrowserRouter>
                    <WarehouseForm onSave={jest.fn()} />
                </BrowserRouter>
            );
        });
    
        fireEvent.change(screen.getByLabelText('Capacity'), { target: { value: `${Number.MAX_SAFE_INTEGER + 1}` } });
        fireEvent.submit(screen.getByRole('button', { name: /update/i }));
    
        await waitFor(() => {
            expect(screen.getByText('Capacity is too large.')).toBeInTheDocument();
        });
    });
    
    it('should not submit the form if name is empty', async () => {
        await act(async () => {
            render(
                <BrowserRouter>
                    <WarehouseForm onSave={jest.fn()} />
                </BrowserRouter>
            );
        });
    
        fireEvent.change(screen.getByLabelText('Name'), { target: { value: '' } });
        fireEvent.submit(screen.getByRole('button', { name: /create/i }));
    
        await waitFor(() => {
            expect(screen.getByText('Please fill out this field.')).toBeInTheDocument();
        });
    });
});