import React, { useState, useEffect } from 'react';
import ItemService from '../services/itemService';
import WarehouseService from '../services/warehouseService';

const ItemForm = ({ item, onSave }) => {
  const [name, setName] = useState(item ? item.name : '');
  const [description, setDescription] = useState(item ? item.description : '');
  const [quantity, setQuantity] = useState(item ? item.quantity : '');
  const [sizeInCubicFt, setSizeInCubicFt] = useState(item ? item.sizeInCubicFt : '');
  const [warehouseId, setWarehouseId] = useState(item ? item.warehouse.id : '');
  const [warehouses, setWarehouses] = useState([]);
  const [availableCapacity, setAvailableCapacity] = useState(null);
  const [error, setError] = useState('');

  useEffect(() => {
    WarehouseService.getAll().then(response => {
      setWarehouses(response.data);
    });
  }, []);

  useEffect(() => {
    if (warehouseId) {
      WarehouseService.get(warehouseId).then(response => {
        const warehouse = response.data;
        setAvailableCapacity(warehouse.capacity - warehouse.usedCapacity);
      });
    }
  }, [warehouseId]);

  const handleSubmit = (e) => {
    e.preventDefault();
    const totalSize = parseFloat(sizeInCubicFt) * parseInt(quantity);
    if (totalSize > availableCapacity) {
      setError('Total item size exceeds the available capacity of the warehouse.');
      return;
    }

    const itemData = {
      name,
      description,
      quantity: parseInt(quantity),
      sizeInCubicFt: parseFloat(sizeInCubicFt),
      warehouse: { id: parseInt(warehouseId) }
    };

    if (item) {
      ItemService.update(item.id, itemData).then(() => onSave());
    } else {
      ItemService.create(itemData).then(() => onSave());
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
        <label>Description</label>
        <input
          type="text"
          className="form-control"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          required
        />
      </div>
      <div className="form-group">
        <label>Quantity</label>
        <input
          type="number"
          className="form-control"
          value={quantity}
          onChange={(e) => setQuantity(e.target.value)}
          required
        />
      </div>
      <div className="form-group">
        <label>Size (in cubic feet)</label>
        <input
          type="number"
          step="0.01"
          className="form-control"
          value={sizeInCubicFt}
          onChange={(e) => setSizeInCubicFt(e.target.value)}
          required
        />
      </div>
      <div className="form-group">
        <label>Warehouse</label>
        <select
          className="form-control"
          value={warehouseId}
          onChange={(e) => setWarehouseId(e.target.value)}
          required
        >
          <option value="">Select a Warehouse</option>
          {warehouses.map((warehouse) => (
            <option key={warehouse.id} value={warehouse.id}>
              {warehouse.name}
            </option>
          ))}
        </select>
      </div>
      {error && <div className="alert alert-danger">{error}</div>}
      <button type="submit" className="btn btn-primary mt-3">
        {item ? 'Update' : 'Create'}
      </button>
    </form>
  );
};

export default ItemForm;
