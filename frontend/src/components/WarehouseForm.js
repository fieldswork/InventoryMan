import React, { useState, useEffect } from 'react';
import WarehouseService from '../services/warehouseService';
import { useParams } from 'react-router-dom';
import UtilizationBar from './UtilizationBar';

const WarehouseForm = ({ onSave }) => {
  const { id } = useParams();
  const [name, setName] = useState('');
  const [capacity, setCapacity] = useState('');
  const [usedCapacity, setUsedCapacity] = useState('');
  const [error, setError] = useState('');

  useEffect(() => {
    if (id) {
      WarehouseService.get(id).then(response => {
        const warehouse = response.data;
        setName(warehouse.name);
        setCapacity(warehouse.capacity);
        setUsedCapacity(warehouse.usedCapacity);
      });
    }
  }, [id]);

  const handleSubmit = (e) => {
    e.preventDefault();
    const parsedCapacity = parseInt(capacity);
    const parsedUsedCapacity = parseFloat(usedCapacity);

    if (parsedCapacity <= 0 || parsedUsedCapacity < 0) {
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

    if (id) {
      WarehouseService.update(id, warehouseData).then(() => onSave());
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
      {id && (
        <div className="form-group">
          <label>Used Capacity</label>
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
