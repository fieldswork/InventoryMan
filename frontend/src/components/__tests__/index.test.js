import React from 'react';
import ReactDOM from 'react-dom/client';
import App from '../../App';

it('renders without crashing', () => { // Index is the entry point of the application, we're checking that it renders without crashing
    const div = document.createElement('div');
    const root = ReactDOM.createRoot(div);
    root.render(
    <React.StrictMode>
        <App />
    </React.StrictMode>
    );
});