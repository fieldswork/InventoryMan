import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import ItemForm from '../ItemForm';
import ItemService from '../../services/itemService';
import WarehouseService from '../../services/warehouseService';
import { act } from 'react';

// Service mocks
jest.mock('../../services/itemService.js');
jest.mock('../../services/warehouseService.js');

// Mock useParams
jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),
    useParams: () => ({ id: '1' }),
}));

const mockItem = {
    id: 1,
    name: 'Item A',
    description: 'Description A',
    quantity: 10,
    sizeInCubicFt: 5,
    warehouse: { id: 1, name: 'Warehouse 1' },
};

const mockWarehouses = [
    { id: 1, name: 'Warehouse 1' },
    { id: 2, name: 'Warehouse 2' },
];

describe('ItemForm', () => {
    beforeEach(() => {
        ItemService.get.mockResolvedValue({ data: mockItem });
        WarehouseService.getAll.mockResolvedValue({ data: mockWarehouses });
        WarehouseService.get.mockResolvedValue({ data: { capacity: 100, usedCapacity: 50 } });
        ItemService.update.mockResolvedValue({});
        ItemService.create.mockResolvedValue({});
    });

    afterEach(() => {
        jest.clearAllMocks();
    });

    it('should render the form fields', async () => {
        await act(async () => {
            render(
                <BrowserRouter>
                    <ItemForm onSave={jest.fn()} />
                </BrowserRouter>
            );
        });

        await waitFor(() => {
            expect(screen.getByLabelText('Name')).toBeInTheDocument();
            expect(screen.getByLabelText('Description')).toBeInTheDocument();
            expect(screen.getByLabelText('Quantity')).toBeInTheDocument();
            expect(screen.getByLabelText('Size (in cubic feet)')).toBeInTheDocument();
            expect(screen.getByLabelText('Warehouse')).toBeInTheDocument();
        });
    });

    it('should fetch and populate item data when editing', async () => {
        await act(async () => {
            render(
                <BrowserRouter>
                    <ItemForm onSave={jest.fn()} />
                </BrowserRouter>
            );
        });

        await waitFor(() => {
            expect(screen.getByDisplayValue('Item A')).toBeInTheDocument();
            expect(screen.getByDisplayValue('Description A')).toBeInTheDocument();
            expect(screen.getByDisplayValue('10')).toBeInTheDocument();
            expect(screen.getByDisplayValue('5')).toBeInTheDocument();
            expect(screen.getByLabelText('Warehouse').value).toBe('1');
        });
    });

    it('should display validation errors', async () => {
        await act(async () => {
            render(
                <BrowserRouter>
                    <ItemForm onSave={jest.fn()} />
                </BrowserRouter>
            );
        });

        fireEvent.change(screen.getByLabelText('Quantity'), { target: { value: '0' } });
        fireEvent.change(screen.getByLabelText('Size (in cubic feet)'), { target: { value: '0' } });
        fireEvent.submit(screen.getByRole('button', { name: /update/i }));

        await waitFor(() => {
            expect(screen.getByText('Quantity and size must be at least 1.')).toBeInTheDocument();
        });
    });

    it('should submit the form and call onSave', async () => {
        const mockOnSave = jest.fn();
        await act(async () => {
            render(
                <BrowserRouter>
                    <ItemForm onSave={mockOnSave} />
                </BrowserRouter>
            );
        });

        fireEvent.change(screen.getByLabelText('Quantity'), { target: { value: '10' } });
        fireEvent.change(screen.getByLabelText('Size (in cubic feet)'), { target: { value: '5' } });
        fireEvent.change(screen.getByLabelText('Warehouse'), { target: { value: '1' } });
        fireEvent.submit(screen.getByRole('button', { name: /update/i }));

        await waitFor(() => {
            expect(ItemService.update).toHaveBeenCalledWith('1', expect.any(Object));
            expect(mockOnSave).toHaveBeenCalled();
        });
    });
});
