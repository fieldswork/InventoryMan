import React from 'react';
import { render } from '@testing-library/react';
import { act } from 'react';
import HomePage from '../HomePage';

test('renders the correct content', () => {
    let getByText;
    act(() => {
        ({ getByText } = render(<HomePage />));
    });
    expect(getByText('InventoryMan')).toBeInTheDocument();
    expect(getByText('A basic inventory management application created as part of the SkillStorm SDET Apprenticeship.')).toBeInTheDocument();
    expect(getByText((content, element) => content.startsWith('Source for this project is available on'))).toBeInTheDocument();
    expect(getByText('GitHub')).toBeInTheDocument();
});