import reportWebVitals from '../../reportWebVitals';

describe('reportWebVitals', () => { // reportWebVitals was included at the start of the project, just checking it works (by not working)
  beforeEach(() => {
    jest.useFakeTimers(); // Simulate time passing
  });

  afterEach(() => { // Cleanup after each test
    jest.runOnlyPendingTimers();
    jest.useRealTimers();
  });

  it('should not call any web-vitals functions if onPerfEntry is not provided', () => { // onPerfEntry is not provided, so nothing should happen
    reportWebVitals(); // nothing should happen
  });

  it('should not call any web-vitals functions if onPerfEntry is not a function', () => { // Ditto above
    reportWebVitals('not a function'); // nothing should happen here either
  });
});