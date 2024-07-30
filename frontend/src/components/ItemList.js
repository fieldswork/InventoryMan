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
              <button onClick={() => handleEdit(item.id)} className="btn btn-primary mt-3">Edit</button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default ItemList;
