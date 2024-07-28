import React, { useState } from 'react';
import WarehouseService from '../services/warehouseService';

const WarehouseForm = ({ warehouse, onSave }) => {
  const [name, setName] = useState(warehouse ? warehouse.name : '');
  const [capacity, setCapacity] = useState(warehouse ? warehouse.capacity : '');

  const handleSubmit = (e) => {
    e.preventDefault();
    const warehouseData = { name, capacity: parseInt(capacity) };
    if (warehouse) {
      WarehouseService.update(warehouse.id, warehouseData).then(() => onSave());
    } else {
      WarehouseService.create(warehouseData).then(() => onSave());
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <div className="form-group">
        <label>Name</label>
        <input
          type="text"
          className="form-control"
          value={name}
          onChange={(e) => setName(e.target.value)}
          required
        />
      </div>
      <div className="form-group">
        <label>Capacity</label>
        <input
          type="number"
          className="form-control"
          value={capacity}
          onChange={(e) => setCapacity(e.target.value)}
          required
        />
      </div>
      <button type="submit" className="btn btn-primary mt-3">
        {warehouse ? 'Update' : 'Create'}
      </button>
    </form>
  );
};

export default WarehouseForm;
