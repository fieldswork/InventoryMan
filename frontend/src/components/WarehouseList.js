import React, { useState, useEffect } from 'react';
import WarehouseService from '../services/warehouseService';
import { useNavigate } from 'react-router-dom';
import UtilizationBar from './UtilizationBar';

const WarehouseList = () => {
  const [warehouses, setWarehouses] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    WarehouseService.getAll().then(response => {
      setWarehouses(response.data);
    });
  }, []);

  const handleEdit = (warehouseId) => {
    navigate(`/edit-warehouse/${warehouseId}`);
  };

  return (
    <div>
      <h2>Warehouses</h2>
      <ul>
        {warehouses.map(warehouse => (
          <li key={warehouse.id} className="d-flex align-items-center justify-content-between">
            <div>
              {warehouse.name}
              <UtilizationBar usedCapacity={warehouse.usedCapacity} capacity={warehouse.capacity} />
            </div>
            <button onClick={() => handleEdit(warehouse.id)} className="btn btn-primary ml-3">Edit</button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default WarehouseList;
