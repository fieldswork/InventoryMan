// src/components/UtilizationBar.js
import React from 'react';

const UtilizationBar = ({ usedCapacity, capacity }) => {
  return (
    <div className="progress" style={{ height: '25px' }}>
      <div
        className="progress-bar"
        role="progressbar"
        style={{ width: `${(usedCapacity / capacity) * 100}%` }}
        aria-valuenow={(usedCapacity / capacity) * 100}
        aria-valuemin="0"
        aria-valuemax="100"
      >
        {usedCapacity} / {capacity} (cubic feet)
      </div>
    </div>
  );
};

export default UtilizationBar;
