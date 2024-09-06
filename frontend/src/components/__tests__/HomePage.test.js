import React from 'react';
import { render } from '@testing-library/react';
import { act } from 'react';
import HomePage from '../HomePage';

test('renders the correct content', () => { // Homepage is the landing page with the project description
    let getByText;
    act(() => { // Act so that the component is fully rendered before getting the text content
        ({ getByText } = render(<HomePage />)); // Render the homepage, get the text content
    });
    expect(getByText('InventoryMan')).toBeInTheDocument(); // Checking for the title, description, and the GitHub link
    expect(getByText('A basic inventory management application created as part of the SkillStorm SDET Apprenticeship.')).toBeInTheDocument();
    expect(getByText((content, element) => content.startsWith('Source for this project is available on'))).toBeInTheDocument();
    expect(getByText('GitHub')).toBeInTheDocument();
});