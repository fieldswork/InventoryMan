import React from 'react';
import { render, fireEvent, screen } from '@testing-library/react';
import App from '../../App';

const renderApp = () => {
  return render(<App />); // render the App component and return it
};

describe('App component', () => { // The app contains the navigation bar and links to the other pages
    it('renders the navigation bar', () => { // The navigation bar should be rendered
        renderApp();
        const navBarElements = screen.getAllByText('InventoryMan'); // Check if the expected text is appearing
        expect(navBarElements.length).toBeGreaterThan(0); // Should be more than one navbar element
    });

    it('renders the warehouse link', () => { // Checks if the warehouse link is rendered
        renderApp();
        expect(screen.getByText('Warehouses')).toBeInTheDocument();
    });

    it('renders the items link', () => { // Checks if the items link is rendered
        renderApp();
        expect(screen.getByText('Items')).toBeInTheDocument();
    });

    it('renders the add warehouse link', () => { // Checks if the add warehouse link is rendered
        renderApp();
        expect(screen.getByText('Add Warehouse')).toBeInTheDocument();
    });

    it('renders the add item link', () => { // Checks if the add item link is rendered
        renderApp();
        expect(screen.getByText('Add Item')).toBeInTheDocument();
    });

    it('navigates to the home page by default', () => { // Checks if the home page is rendered by default
        renderApp();
        expect(screen.getByText('A basic inventory management application created as part of the SkillStorm SDET Apprenticeship.')).toBeInTheDocument();
        // Using landing page text to check if the home page loads properly
    });

    it('navigates to the warehouses page when clicked', () => { // Checks if the warehouses page is rendered when clicked
        renderApp();
        fireEvent.click(screen.getByText('Warehouses'));
        expect(screen.getByText('Sort By:')).toBeInTheDocument();
    });
});