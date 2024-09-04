import reportWebVitals from '../../reportWebVitals';

describe('reportWebVitals', () => {
  beforeEach(() => {
    jest.useFakeTimers();
  });

  afterEach(() => {
    jest.runOnlyPendingTimers();
    jest.useRealTimers();
  });

  it('should not call any web-vitals functions if onPerfEntry is not provided', () => {
    reportWebVitals(); // nothing should happen
  });

  it('should not call any web-vitals functions if onPerfEntry is not a function', () => {
    reportWebVitals('not a function'); // nothing should happen here either
  });
});