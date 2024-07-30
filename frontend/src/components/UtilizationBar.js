// src/components/UtilizationBar.js
import React from 'react';

const UtilizationBar = ({ usedCapacity, capacity }) => {
  const percentage = (usedCapacity / capacity) * 100;
  return (
    <div className="progress" style={{ height: '25px' }}>
      <div
        className="progress-bar progress-bar-striped"
        role="progressbar"
        style={{ width: `${percentage}%` }}
        aria-valuenow={percentage}
        aria-valuemin="0"
        aria-valuemax="100"
      >
        {`${Math.round(percentage)}%`}
      </div>
    </div>
  );
};

export default UtilizationBar;
