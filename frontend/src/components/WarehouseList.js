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
      <div className="row">
        {warehouses.map(warehouse => (
          <div key={warehouse.id} className="col-md-6">
            <div className="card">
              <h5>{warehouse.name}</h5>
              <p>{warehouse.usedCapacity} / {warehouse.capacity} cubic feet utilized</p>
              <UtilizationBar usedCapacity={warehouse.usedCapacity} capacity={warehouse.capacity} />
              <button onClick={() => handleEdit(warehouse.id)} className="btn btn-primary mt-3">Edit</button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default WarehouseList;
