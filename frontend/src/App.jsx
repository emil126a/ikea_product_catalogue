import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Header from './components/common/Header';
import ProductListPage from './pages/ProductListPage';
import ProductDetailPage from './pages/ProductDetailPage';
import CreateProductPage from './pages/CreateProductPage';

function App() {
  return (
    <Router>
      <Header />
      <main className="min-h-screen bg-gray-50">
        <Routes>
          <Route path="/products" element={<ProductListPage />} />
          <Route path="/products/new" element={<CreateProductPage />} />
          <Route path="/products/:id" element={<ProductDetailPage />} />
        </Routes>
      </main>
    </Router>
  );
}

export default App;