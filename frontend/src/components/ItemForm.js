import React, { useState, useEffect } from 'react';
import ItemService from '../services/itemService';
import WarehouseService from '../services/warehouseService';
import { useParams } from 'react-router-dom';

const ItemForm = ({ onSave }) => {
  const { id } = useParams();
  const [name, setName] = useState('');
  const [description, setDescription] = useState('');
  const [quantity, setQuantity] = useState('');
  const [sizeInCubicFt, setSizeInCubicFt] = useState('');
  const [warehouseId, setWarehouseId] = useState('');
  const [warehouses, setWarehouses] = useState([]);
  const [availableCapacity, setAvailableCapacity] = useState(null);
  const [initialItem, setInitialItem] = useState(null);
  const [error, setError] = useState('');

  useEffect(() => { // Fetch all warehouses and item data if editing
    WarehouseService.getAll().then(response => {
      setWarehouses(response.data);
    });

    if (id) {
      ItemService.get(id).then(response => {
        const item = response.data;
        setName(item.name);
        setDescription(item.description);
        setQuantity(item.quantity);
        setSizeInCubicFt(item.sizeInCubicFt);
        setWarehouseId(item.warehouse.id);
        setInitialItem(item);
      });
    }
  }, [id]);

  useEffect(() => { // Fetch warehouse data to calculate available capacity
    if (warehouseId) {
      WarehouseService.get(warehouseId).then(response => {
        const warehouse = response.data;
        setAvailableCapacity(warehouse.capacity - warehouse.usedCapacity);
      });
    }
  }, [warehouseId]);

  const handleSubmit = (e) => { // Validate and submit form data
    e.preventDefault();
    const parsedQuantity = Math.ceil(parseFloat(quantity)); // Rounds up to nearest whole number
    const parsedSizeInCubicFt = parseFloat(sizeInCubicFt);
    
    if (parsedQuantity < 1 || parsedSizeInCubicFt < 1) {
      setError('Quantity and size must be at least 1.');
      return;
    }

    if (parsedQuantity > Number.MAX_SAFE_INTEGER || parsedSizeInCubicFt > Number.MAX_SAFE_INTEGER) {
      setError('Quantity, size, or both values are too large.');
      return;
    }

    let totalSize = parsedSizeInCubicFt * parsedQuantity;
    if (initialItem && initialItem.warehouse.id === parseInt(warehouseId)) { // Subtract initial item size if editing
      const initialSize = initialItem.sizeInCubicFt * initialItem.quantity;
      totalSize -= initialSize;
    }

    if (totalSize > availableCapacity) { // Validation for warehouse is set to display error here!
      setError('Total item size exceeds the available capacity of the warehouse.');
      return;
    }

    const itemData = {
      name,
      description,
      quantity: parsedQuantity,
      sizeInCubicFt: parsedSizeInCubicFt,
      warehouse: { id: parseInt(warehouseId) }
    };

    if (id) { // Update or create item if form data is valid
      ItemService.update(id, itemData).then(() => onSave());
    } else { // If id is not present, create a new item
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
          {warehouses.map(warehouse => (
            <option key={warehouse.id} value={warehouse.id}>
              {warehouse.name}
            </option>
          ))}
        </select>
      </div>
      {error && <div className="alert alert-danger">{error}</div>}
      <button type="submit" className="btn btn-primary mt-3">
        {id ? 'Update' : 'Create'}
      </button>
    </form>
  );
};

export default ItemForm;
