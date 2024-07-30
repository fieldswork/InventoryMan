import React, { useState, useEffect } from 'react';
import ItemService from '../services/itemService';
import { useNavigate } from 'react-router-dom';

const ItemList = () => {
  const [items, setItems] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    ItemService.getAll().then(response => {
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

  return (
    <div>
      <h2>Items</h2>
      <div className="row">
        {items.map(item => (
          <div key={item.id} className="col-md-6">
            <div className="card">
              <h5>{item.name}</h5>
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
