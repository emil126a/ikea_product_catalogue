import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import ProductList from '../components/product/ProductList';
import Pagination from '../components/common/Pagination';
import { fetchProducts } from '../services/api';
import LoadingSpinner from '../components/common/LoadingSpinner';

function ProductListPage() {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [pagination, setPagination] = useState({
    page: 0,
    limit: 10,
    total: 0,
    totalPages: 1,
  });

  const navigate = useNavigate();

  useEffect(() => {
    let isMounted = true; // Prevent state updates after unmount
    const loadProducts = async () => {
      try {
        setLoading(true);
        const response = await fetchProducts(pagination.page, pagination.limit);
        console.log('API Response:', response.data); // Debug response

        if (isMounted && response.data.success) {
          setProducts(response.data.data);
          setPagination((prev) => ({
            ...prev,
            page: response.data.pagination.currentPage, // Update page
            total: response.data.pagination.totalElements,
            totalPages: response.data.pagination.totalPages,
            // Do not update limit unless explicitly required
          }));
          setError(null);
        } else if (isMounted) {
          setError('Failed to load products: API returned unsuccessful');
        }
      } catch (err) {
        if (isMounted) {
          setError('Failed to load products: ' + (err.message || 'Network error'));
        }
      } finally {
        if (isMounted) {
          setLoading(false);
        }
      }
    };
    loadProducts();
    return () => {
      isMounted = false; // Cleanup to prevent state updates after unmount
    };
  }, [pagination.page]); // Remove pagination.limit from dependencies

  const handlePageChange = (newPage) => {
    console.log(`handlePageChange: newPage=${newPage}, currentLimit=${pagination.limit}`);
    if (newPage >= 0 && newPage < pagination.totalPages) {
      setPagination((prev) => ({ ...prev, page: newPage }));
    }
  };

if (loading) {
  return <LoadingSpinner />;
}

  if (error) {
    return (
      <div className="container mx-auto px-4 py-8">
        <div className="bg-red-100 border-l-4 border-red-500 text-red-700 p-4" role="alert">
          <p className="font-bold">Error</p>
          <p>{error}</p>
          <button
            onClick={() => window.location.reload()}
            className="mt-2 px-4 py-2 bg-red-600 text-white rounded hover:bg-red-700"
          >
            Retry
          </button>
        </div>
      </div>
    );
  }

  console.log('Pagination Props:', pagination); // Debug props

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="flex justify-between items-center mb-8">
        <h2 className="text-3xl font-bold text-gray-800">Product Catalog</h2>
        <button
          onClick={() => navigate('/products/new')}
          className="btn-primary flex items-center"
        >
          <svg className="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 6v6m0 0v6m0-6h6m-6 0H6" />
          </svg>
          Add New Product
        </button>
      </div>

      <div className="bg-white shadow rounded-lg overflow-hidden">
        <ProductList
          products={products}
          onProductClick={(id) => navigate(`/products/${id}`)}
        />
        <Pagination
          currentPage={pagination.page}
          totalPages={pagination.totalPages}
          itemsPerPage={pagination.limit}
          totalItems={pagination.total}
          onPageChange={handlePageChange}
        />
      </div>
    </div>
  );
}

export default ProductListPage;