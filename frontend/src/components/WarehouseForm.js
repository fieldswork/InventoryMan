import React, { useState, useEffect } from 'react';
import WarehouseService from '../services/warehouseService';
import { useParams } from 'react-router-dom';
import UtilizationBar from './UtilizationBar';

const WarehouseForm = ({ onSave }) => { // Warehouse form component, used for both creating and editing warehouses
  const { id } = useParams();
  const [name, setName] = useState('');
  const [capacity, setCapacity] = useState('');
  const [usedCapacity, setUsedCapacity] = useState('');
  const [error, setError] = useState('');

  useEffect(() => { // Fetch warehouse data if editing
    if (id) {
      WarehouseService.get(id).then(response => { // Fetch warehouse data
        const warehouse = response.data;
        setName(warehouse.name);
        setCapacity(warehouse.capacity);
        setUsedCapacity(warehouse.usedCapacity);
      });
    }
  }, [id]);

  const handleSubmit = (e) => { // Validate and submit form data to create or update warehouse
    e.preventDefault();
    const parsedCapacity = parseInt(capacity);
    const parsedUsedCapacity = parseFloat(usedCapacity);

    if (parsedCapacity <= 0 || parsedUsedCapacity < 0) { // More form validation
      setError('Capacity must be positive.');
      return;
    }

    if (parsedCapacity < parsedUsedCapacity) {
      setError('Used capacity exceeds new capacity.');
      return;
    }

    if (parsedCapacity > Number.MAX_SAFE_INTEGER || parsedUsedCapacity > Number.MAX_SAFE_INTEGER) {
      setError('Capacity is too large.');
      return;
    }

    const warehouseData = {
      name,
      capacity: parsedCapacity,
      usedCapacity: parsedUsedCapacity
    };

    if (id) { // Update warehouse if editing, otherwise create new warehouse
      WarehouseService.update(id, warehouseData).then(() => onSave());
    } else {
      WarehouseService.create(warehouseData).then(() => onSave());
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <div className="form-group">
        <label htmlFor="name">Name</label>
        <input
          type="text"
          id="name"
          className="form-control"
          value={name}
          onChange={(e) => setName(e.target.value)}
          required
        />
      </div>
      <div className="form-group">
        <label htmlFor="capacity">Capacity</label>
        <input
          type="number"
          id="capacity"
          className="form-control"
          value={capacity}
          onChange={(e) => setCapacity(e.target.value)}
          required
        />
      </div>
      {id && (
        <div className="form-group">
          <label htmlFor="usedCapacity">Used Capacity</label>
          <UtilizationBar usedCapacity={usedCapacity} capacity={capacity} />
        </div>
      )}
      {error && <div className="alert alert-danger">{error}</div>}
      <button type="submit" className="btn btn-primary mt-3">
        {id ? 'Update' : 'Create'}
      </button>
    </form>
  );
};

export default WarehouseForm;
