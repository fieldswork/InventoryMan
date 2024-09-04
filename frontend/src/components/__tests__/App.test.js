import React from 'react';
import { render, fireEvent, screen } from '@testing-library/react';
import App from '../../App';

const renderApp = () => {
  return render(<App />);
};

describe('App component', () => {
    it('renders the navigation bar', () => {
        renderApp();
        const navBarElements = screen.getAllByText('InventoryMan');
        expect(navBarElements.length).toBeGreaterThan(0);
    });

    it('renders the warehouse link', () => {
        renderApp();
        expect(screen.getByText('Warehouses')).toBeInTheDocument();
    });

    it('renders the items link', () => {
        renderApp();
        expect(screen.getByText('Items')).toBeInTheDocument();
    });

    it('renders the add warehouse link', () => {
        renderApp();
        expect(screen.getByText('Add Warehouse')).toBeInTheDocument();
    });

    it('renders the add item link', () => {
        renderApp();
        expect(screen.getByText('Add Item')).toBeInTheDocument();
    });

    it('navigates to the home page by default', () => {
        renderApp();
        expect(screen.getByText('A basic inventory management application created as part of the SkillStorm SDET Apprenticeship.')).toBeInTheDocument();
    });

    it('navigates to the warehouses page when clicked', () => {
        renderApp();
        fireEvent.click(screen.getByText('Warehouses'));
        expect(screen.getByText('Sort By:')).toBeInTheDocument();
    });
});