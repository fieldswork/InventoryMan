import React, { useState, useEffect } from 'react';
import ItemService from '../services/itemService';
import WarehouseService from '../services/warehouseService';
import { useNavigate } from 'react-router-dom';

const ItemList = () => {
  const [items, setItems] = useState([]);
  const [warehouses, setWarehouses] = useState([]);
  const [sortCriteria, setSortCriteria] = useState('name'); // Default is to sort by name
  const [filterWarehouse, setFilterWarehouse] = useState('all'); // Default is to show all items as well
  const navigate = useNavigate();

  useEffect(() => { // Fetch all items and warehouses on component mount
    ItemService.getAll().then(response => {
      console.log('Fetched items:', response.data); // Logging fetched items
      setItems(response.data);
    });
    WarehouseService.getAll().then(response => { 
      setWarehouses(response.data);
    });
  }, []);

  const handleEdit = (itemId) => { // Navigate to edit item page
    navigate(`/edit-item/${itemId}`);
  };

  const handleDelete = (itemId) => { // Delete item and update state
    if (window.confirm('Are you sure you want to delete this item?')) {
      ItemService.delete(itemId).then(() => {
        setItems(items.filter(item => item.id !== itemId));
      });
    }
  };

  const handleSortChange = (e) => { // Update sort criteria state based on user selection
    setSortCriteria(e.target.value);
  };

  const handleFilterChange = (e) => { // Update filter warehouse state based on user selection
    setFilterWarehouse(e.target.value);
  };

  const sortedItems = [...items].sort((a, b) => { // Sort items based on sort criteria, using localeCompare for string comparison
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

  const filteredItems = filterWarehouse === 'all' // Filter items based on warehouse selection
    ? sortedItems // Show all items if no warehouse is selected
    : sortedItems.filter(item => item.warehouse.id === parseInt(filterWarehouse)); // Show items from selected warehouse

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
