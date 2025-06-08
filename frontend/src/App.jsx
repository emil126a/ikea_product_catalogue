import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import ProductListPage from './pages/ProductListPage';

function App() {
  return (
    <Router>
      <div className="min-h-screen bg-gray-100">
        <nav className="bg-blue-600 p-4">
          <div className="container mx-auto">
            <h1 className="text-white text-2xl font-bold">Product Management</h1>
          </div>
        </nav>
        <div className="container mx-auto p-4">
          <Routes>
            <Route path="/" element={<ProductListPage />} />
          </Routes>
        </div>
      </div>
    </Router>
  );
}

export default App;