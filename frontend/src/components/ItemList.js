import React, { useState, useEffect } from 'react';
import ItemService from '../services/itemService';
import WarehouseService from '../services/warehouseService';
import { useNavigate } from 'react-router-dom';

const ItemList = () => {
  const [items, setItems] = useState([]);
  const [warehouses, setWarehouses] = useState([]);
  const [sortCriteria, setSortCriteria] = useState('name'); // default sort by name
  const [filterWarehouse, setFilterWarehouse] = useState('all'); // default filter by all warehouses
  const navigate = useNavigate();

  useEffect(() => {
    ItemService.getAll().then(response => {
      console.log('Fetched items:', response.data); // LOGGING <----------
      setItems(response.data);
    });
    WarehouseService.getAll().then(response => {
      setWarehouses(response.data);
    });
  }, []);

  const handleEdit = (itemId) => {
    navigate(`/edit-item/${itemId}`);
  };

  const handleDelete = (itemId) => {
    if (window.confirm('Are you sure you want to delete this item?')) {
      ItemService.delete(itemId).then(() => {
        setItems(items.filter(item => item.id !== itemId));
      });
    }
  };

  const handleSortChange = (e) => {
    setSortCriteria(e.target.value);
  };

  const handleFilterChange = (e) => {
    setFilterWarehouse(e.target.value);
  };

  const sortedItems = [...items].sort((a, b) => {
    if (sortCriteria === 'name') {
      const nameA = a.name || '';
      const nameB = b.name || '';
      return nameA.localeCompare(nameB);
    } else if (sortCriteria === 'quantity') {
      return b.quantity - a.quantity;
    } else if (sortCriteria === 'size') {
      return b.sizeInCubicFt - a.sizeInCubicFt;
    } else if (sortCriteria === 'totalSize') {
      const totalSizeA = a.sizeInCubicFt * a.quantity;
      const totalSizeB = b.sizeInCubicFt * b.quantity;
      return totalSizeB - totalSizeA;
    }
    return 0;
  });

  const filteredItems = filterWarehouse === 'all'
    ? sortedItems
    : sortedItems.filter(item => item.warehouse.id === parseInt(filterWarehouse));

  return (
    <div>
      <h2>Items</h2>
      <div className="mb-3">
        <label htmlFor="sortCriteria" className="form-label">Sort By:</label>
        <select id="sortCriteria" className="form-select" onChange={handleSortChange} value={sortCriteria}>
          <option value="name">Name (Alphabetical)</option>
          <option value="quantity">Quantity</option>
          <option value="size">Item Size</option>
          <option value="totalSize">Total Size</option>
        </select>
      </div>
      <div className="mb-3">
        <label htmlFor="filterWarehouse" className="form-label">Filter by Warehouse:</label>
        <select id="filterWarehouse" className="form-select" onChange={handleFilterChange} value={filterWarehouse}>
          <option value="all">All Warehouses</option>
          {warehouses.map(warehouse => (
            <option key={warehouse.id} value={warehouse.id}>{warehouse.name}</option>
          ))}
        </select>
      </div>
      <div className="row">
        {filteredItems.map(item => (
          <div key={item.id} className="col-md-6">
            <div className="card">
              <h5>{item.name || 'Unnamed Item'}</h5>
              <p>{item.description}</p>
              <p>Quantity: {item.quantity}</p>
              <p>Size: {item.sizeInCubicFt} cubic feet</p>
              <p>Warehouse: {item.warehouse.name}</p>
              <div className="d-flex justify-content-between mt-3">
                <button onClick={() => handleEdit(item.id)} className="btn btn-primary">Edit</button>
                <button onClick={() => handleDelete(item.id)} className="btn btn-danger">Delete</button>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default ItemList;
