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
      <ul>
        {items.map(item => (
          <li key={item.id}>
            {item.name}
            <button onClick={() => handleEdit(item.id)} className="btn btn-primary ml-3">Edit</button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ItemList;
