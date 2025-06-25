import { render, screen } from '@testing-library/react';
import App from './App';

test('renders Evidencija prisustva naslov', () => {
  render(<App />);
  const naslov = screen.getByText(/Evidencija prisustva/i);
  expect(naslov).toBeInTheDocument();
});
