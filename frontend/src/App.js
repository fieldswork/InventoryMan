import React from 'react';
import { BrowserRouter as Router, Route, Routes, NavLink } from 'react-router-dom';
import WarehouseList from './components/WarehouseList';
import ItemList from './components/ItemList';
import WarehouseForm from './components/WarehouseForm';
import ItemForm from './components/ItemForm';
import HomePage from './components/HomePage';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';

function App() {
  const handleSave = () => { // Redirect to warehouses page after creating/editing warehouse or item
    window.location.href = '/warehouses';
  };

  return (
    <Router>
      <div>
        <nav className="navbar navbar-expand navbar-dark bg-dark">
          <NavLink to="/" className="navbar-brand" style={{ paddingLeft: '20px' }}>InventoryMan</NavLink>
          <div className="navbar-nav">
            <li className="nav-item">
              <NavLink to="/warehouses" className="nav-link" activeClassName="active">Warehouses</NavLink>
            </li>
            <li className="nav-item">
              <NavLink to="/items" className="nav-link" activeClassName="active">Items</NavLink>
            </li>
            <li className="nav-item">
              <NavLink to="/add-warehouse" className="nav-link" activeClassName="active">Add Warehouse</NavLink>
            </li>
            <li className="nav-item">
              <NavLink to="/add-item" className="nav-link" activeClassName="active">Add Item</NavLink>
            </li>
          </div>
        </nav>
        <div className="container mt-3">
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/warehouses" element={<WarehouseList />} />
            <Route path="/items" element={<ItemList />} />
            <Route path="/add-warehouse" element={<WarehouseForm onSave={handleSave} />} />
            <Route path="/add-item" element={<ItemForm onSave={handleSave} />} />
            <Route path="/edit-warehouse/:id" element={<WarehouseForm onSave={handleSave} />} />
            <Route path="/edit-item/:id" element={<ItemForm onSave={handleSave} />} />
          </Routes>
        </div>
      </div>
    </Router>
  );
}

export default App;
