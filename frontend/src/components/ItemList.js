import React, { useState, useEffect } from 'react';
import ItemService from '../services/itemService';
import { useNavigate } from 'react-router-dom';

const ItemList = () => {
  const [items, setItems] = useState([]);
  const [sortCriteria, setSortCriteria] = useState('name'); // default sort by name
  const navigate = useNavigate();

  useEffect(() => {
    ItemService.getAll().then(response => {
      console.log('Fetched items:', response.data); // LOGGING <----------
      setItems(response.data);
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

  const sortedItems = [...items].sort((a, b) => {
    if (sortCriteria === 'name') { // assigning an empty string for name if null
      const nameA = a.name || '';
      const nameB = b.name || '';
      return nameA.localeCompare(nameB);
    } else if (sortCriteria === 'quantity') {
      return b.quantity - a.quantity;
    }
    return 0;
  });

  return (
    <div>
      <h2>Items</h2>
      <div className="mb-3">
        <label htmlFor="sortCriteria" className="form-label">Sort By:</label>
        <select id="sortCriteria" className="form-select" onChange={handleSortChange} value={sortCriteria}>
          <option value="name">Name (Alphabetical)</option>
          <option value="quantity">Quantity</option>
        </select>
      </div>
      <div className="row">
        {sortedItems.map(item => (
          <div key={item.id} className="col-md-6">
            <div className="card">
              <h5>{item.name || 'Unnamed Item'}</h5>
              <p>{item.description}</p>
              <p>Quantity: {item.quantity}</p>
              <p>Size: {item.sizeInCubicFt} cubic feet</p>
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
