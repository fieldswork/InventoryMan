import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

const UtilizationBar = ({ usedCapacity, capacity }) => {
  const percentage = (usedCapacity / capacity) * 100;

  let progressBarClass = 'bg-primary'; // Blue under 75% capacity
  if (percentage >= 100) {
    progressBarClass = 'bg-danger'; // Red at 100%
  } else if (percentage >= 75) {
    progressBarClass = 'bg-warning'; // Yellow if between 75-100%
  }

  return (
    <div className="progress" style={{ height: '25px' }}>
      <div
        className={`progress-bar progress-bar-striped ${progressBarClass}`}
        role="progressbar"
        style={{ width: `${Math.min(percentage, 100)}%`, color: 'black' }}
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
