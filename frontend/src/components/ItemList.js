import React, { useState, useEffect } from 'react';
import ItemService from '../services/itemService';

const ItemList = () => {
  const [items, setItems] = useState([]);

  useEffect(() => {
    ItemService.getAll().then(response => {
      setItems(response.data);
    });
  }, []);

  return (
    <div>
      <h2>Items</h2>
      <ul>
        {items.map(item => (
          <li key={item.id}>{item.name}</li>
        ))}
      </ul>
    </div>
  );
};

export default ItemList;
