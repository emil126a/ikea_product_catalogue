import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { fetchProductById } from '../services/api';

function ProductDetailPage() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [product, setProduct] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const loadProduct = async () => {
      try {
        const response = await fetchProductById(id);
        setProduct(response.data);
        setLoading(false);
      } catch (err) {
        setError('Failed to load product details');
        setLoading(false);
        console.error(err);
      }
    };
    loadProduct();
  }, [id]);

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

  if (!product) return <div>Product not found</div>;

  return (
    <div className="container mx-auto px-4 py-8">
      <button
        onClick={() => navigate(-1)}
        className="mb-6 flex items-center text-blue-600 hover:text-blue-800"
      >
        <svg className="w-5 h-5 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M10 19l-7-7m0 0l7-7m-7 7h18" />
        </svg>
        Back to Products
      </button>

      <div className="card p-6">
        <div className="flex flex-col md:flex-row gap-8">
          <div className="md:w-1/3">
            <div className="bg-gray-100 rounded-lg h-64 flex items-center justify-center">
              <span className="text-gray-400">Product Image</span>
            </div>
          </div>
          <div className="md:w-2/3">
            <h1 className="text-3xl font-bold text-gray-800 mb-2">{product.name}</h1>
            <div className="flex items-center mb-4">
              <span className="px-3 py-1 rounded-full text-sm font-semibold bg-blue-100 text-blue-800">
                {product.productType.name}
              </span>
            </div>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-8">
              <div>
                <h3 className="text-sm font-medium text-gray-500">Product ID</h3>
                <p className="mt-1 text-sm text-gray-900">{product.id}</p>
              </div>
              <div>
                <h3 className="text-sm font-medium text-gray-500">Colors</h3>
                <div className="mt-1 flex space-x-2">
                  {product.colours.map((color) => (
                    <div key={color.id} className="flex items-center">
                      <span
                        className="w-4 h-4 rounded-full inline-block mr-1"
                        style={{ backgroundColor: color.hexCode || '#ccc' }}
                      />
                      <span className="text-sm text-gray-900">{color.name}</span>
                    </div>
                  ))}
                </div>
              </div>
            </div>

            <div className="border-t border-gray-200 pt-4">
              <h3 className="text-lg font-medium text-gray-900 mb-2">Description</h3>
              <p className="text-gray-600">
                {product.description || 'No description available'}
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default ProductDetailPage;