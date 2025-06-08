import { useState, useEffect } from 'react';
import ProductList from '../components/product/ProductList';
import { fetchProducts } from '../services/api';

function ProductListPage() {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const loadProducts = async () => {
      try {
        const response = await fetchProducts();
        // Sort by creation date (newest first)
       // const sortedProducts = response.data.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt));

        console.log(response);
        setProducts(response.data.data);
        setLoading(false);
      } catch (err) {
        setError('Failed to load products'); // Fixed typo
        setLoading(false);
      }
    };
    loadProducts();
  }, []);

  if (loading) return <div className="text-center">Loading...</div>;
  if (error) return <div className="text-red-500 text-center">{error}</div>;

  return (
    <div>
      <h2 className="text-2xl font-bold mb-4">Product List</h2>
      <ProductList products={products} />
    </div>
  );
}

export default ProductListPage;