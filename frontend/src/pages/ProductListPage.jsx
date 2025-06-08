import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import ProductList from '../components/product/ProductList';
import { fetchProducts } from '../services/api';

function ProductListPage() {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const loadProducts = async () => {
      try {
        const response = await fetchProducts();
        setProducts(response.data.data);
        setLoading(false);
      } catch (err) {
        setError('Failed to load products');
        setLoading(false);
        console.error(err);
      }
    };
    loadProducts();
  }, []);

  const handleCreateNew = () => {
    navigate('/products/new');
  };

  const handleProductClick = (productId) => {
    navigate(`/products/${productId}`);
  };

  if (loading) return (
    <div className="flex justify-center items-center h-64">
      <div className="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-blue-500"></div>
    </div>
  );

  if (error) return (
    <div className="bg-red-100 border-l-4 border-red-500 text-red-700 p-4" role="alert">
      <p>{error}</p>
    </div>
  );

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="flex justify-between items-center mb-8">
        <h2 className="text-3xl font-bold text-gray-800">Product Catalog</h2>

      </div>

      <div className="card">
        <ProductList
          products={products}
          onProductClick={handleProductClick}
        />
      </div>
    </div>
  );
}

export default ProductListPage;