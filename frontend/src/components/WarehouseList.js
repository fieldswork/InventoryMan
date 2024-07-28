import React, { useState, useEffect } from 'react';
import WarehouseService from '../services/warehouseService';

const WarehouseList = () => {
  const [warehouses, setWarehouses] = useState([]);

  useEffect(() => {
    WarehouseService.getAll().then(response => {
      setWarehouses(response.data);
    });
  }, []);

  return (
    <div>
      <h2>Warehouses</h2>
      <ul>
        {warehouses.map(warehouse => (
          <li key={warehouse.id}>{warehouse.name}</li>
        ))}
      </ul>
    </div>
  );
};

export default WarehouseList;
