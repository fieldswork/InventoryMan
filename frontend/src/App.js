import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import WarehouseList from './components/WarehouseList';
import ItemList from './components/ItemList';
import WarehouseForm from './components/WarehouseForm';
import ItemForm from './components/ItemForm';
import 'bootstrap/dist/css/bootstrap.min.css';

function App() {
  const handleSave = () => {
    window.location.href = '/warehouses';
  };

  return (
    <Router>
      <div>
        <nav className="navbar navbar-expand navbar-dark bg-dark">
          <a href="/warehouses" className="navbar-brand">Inventory Man</a>
          <div className="navbar-nav">
            <li className="nav-item">
              <a href="/warehouses" className="nav-link">Warehouses</a>
            </li>
            <li className="nav-item">
              <a href="/items" className="nav-link">Items</a>
            </li>
            <li className="nav-item">
              <a href="/add-warehouse" className="nav-link">Add Warehouse</a>
            </li>
            <li className="nav-item">
              <a href="/add-item" className="nav-link">Add Item</a>
            </li>
          </div>
        </nav>
        <div className="container mt-3">
          <Routes>
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
