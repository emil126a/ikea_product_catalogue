import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { createProduct, fetchProductTypes, fetchColors } from '../services/api';
import LoadingSpinner from '../components/common/LoadingSpinner';

function CreateProductPage() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    name: '',
    description: '',
    productTypeId: '',
    colourIds: [],
  });
  const [productTypes, setProductTypes] = useState([]);
  const [colors, setColors] = useState([]);
  const [errors, setErrors] = useState({});
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      setIsLoading(true);
      try {
        const [typesResponse, colorsResponse] = await Promise.all([
          fetchProductTypes(),
          fetchColors(),
        ]);
        setProductTypes(typesResponse.data.data || []);
        setColors(colorsResponse.data.data || []);
      } catch (err) {
        setErrors({ general: err.response?.data?.message || 'Failed to load product types and colors' });
        console.error(err);
      } finally {
        setIsLoading(false);
      }
    };
    fetchData();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
    setErrors((prev) => ({ ...prev, [name]: '' }));
  };

  const handleColorChange = (e) => {
    const { value, checked } = e.target;
    setFormData((prev) => ({
      ...prev,
      colourIds: checked
        ? [...prev.colourIds, Number(value)]
        : prev.colourIds.filter((id) => id !== Number(value)),
    }));
    if (checked) {
      setErrors((prev) => ({ ...prev, colourIds: '' }));
    }
  };

  const validateForm = () => {
    const newErrors = {};
    if (!formData.name.trim()) {
      newErrors.name = 'Product name is required';
    }
    if (!formData.productTypeId) {
      newErrors.productTypeId = 'Product type is required';
    }
    if (formData.colourIds.length === 0) {
      newErrors.colourIds = 'At least one color is required';
    }
    return newErrors;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const validationErrors = validateForm();
    if (Object.keys(validationErrors).length > 0) {
      setErrors(validationErrors);
      return;
    }

    setIsSubmitting(true);
    setErrors({});

    try {
      const payload = {
        ...formData,
        productTypeId: Number(formData.productTypeId),
      };
      await createProduct(payload);
      navigate('/products');
    } catch (err) {
      const errorDetails = err.response?.data?.error?.details || {};
      setErrors({
        general: err.response?.data?.message || 'Failed to create product',
        ...errorDetails,
      });
      console.error(err);
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="max-w-2xl mx-auto">
        <div className="flex justify-between items-center mb-8">
          <h2 className="text-2xl font-bold text-gray-800">Create New Product</h2>
          <button
            onClick={() => navigate('/products')}
            className="text-gray-600 hover:text-gray-800 flex items-center cursor-pointer"
          >
            <svg className="w-5 h-5 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M10 19l-7-7m0 0l7-7m-7 7h18" />
            </svg>
            Back to Products
          </button>
        </div>

        {errors.general && (
          <div className="bg-red-100 border-l-4 border-red-500 text-red-700 p-4 mb-6" role="alert">
            <p>{errors.general}</p>
          </div>
        )}

        {isLoading ? (
          <LoadingSpinner />
        ) : (
          <div className="card p-6">
            <form onSubmit={handleSubmit}>
              <div className="mb-6">
                <label htmlFor="name" className="block text-sm font-medium text-gray-700 mb-1">
                  Product Name *
                </label>
                <input
                  type="text"
                  id="name"
                  name="name"
                  value={formData.name}
                  onChange={handleChange}
                  className={`w-full px-3 py-2 border rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 ${
                    errors.name ? 'border-red-500' : 'border-gray-300'
                  }`}
                />
                {errors.name && <p className="text-red-500 text-sm mt-1">{errors.name}</p>}
              </div>

              <div className="mb-6">
                <label htmlFor="description" className="block text-sm font-medium text-gray-700 mb-1">
                  Description
                </label>
                <textarea
                  id="description"
                  name="description"
                  rows={4}
                  value={formData.description}
                  onChange={handleChange}
                  className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
                />
              </div>

              <div className="mb-6">
                <label htmlFor="productTypeId" className="block text-sm font-medium text-gray-700 mb-1">
                  Product Type *
                </label>
                <select
                  id="productTypeId"
                  name="productTypeId"
                  value={formData.productTypeId}
                  onChange={handleChange}
                  className={`w-full px-3 py-2 border rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 ${
                    errors.productTypeId ? 'border-red-500' : 'border-gray-300'
                  }`}
                >
                  <option value="">Select a product type</option>
                  {productTypes.map((type) => (
                    <option key={type.id} value={type.id}>
                      {type.name}
                    </option>
                  ))}
                </select>
                {errors.productTypeId && <p className="text-red-500 text-sm mt-1">{errors.productTypeId}</p>}
              </div>

              <div className="mb-8">
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Available Colors *
                </label>
                <div className="grid grid-cols-2 md:grid-cols-3 gap-3">
                  {colors.map((color) => (
                    <div key={color.id} className="flex items-center">
                      <input
                        type="checkbox"
                        id={`color-${color.id}`}
                        name="colourIds"
                        value={color.id}
                        checked={formData.colourIds.includes(color.id)}
                        onChange={handleColorChange}
                        className="h-4 w-4 text-blue-600 focus:ring-blue-500 border-gray-300 rounded"
                      />
                      <label htmlFor={`color-${color.id}`} className="ml-2 flex items-center">
                        <span
                          className="w-4 h-4 rounded-full inline-block mr-2"
                          style={{ backgroundColor: color.hexcode }}
                        />
                        <span className="text-sm text-gray-700">{color.name}</span>
                      </label>
                    </div>
                  ))}
                </div>
                {errors.colourIds && <p className="text-red-500 text-sm mt-2">{errors.colourIds}</p>}
              </div>

              <div className="flex justify-end">
                <button
                  type="submit"
                  disabled={isSubmitting}
                  className={`btn-primary flex items-center ${isSubmitting ? 'opacity-50 cursor-not-allowed' : ''}`}
                >
                  {isSubmitting ? (
                    <>
                      <svg
                        className="animate-spin -ml-1 mr-2 h-4 w-4 text-white"
                        xmlns="http://www.w3.org/2000/svg"
                        fill="none"
                        viewBox="0 0 24 24"
                      >
                        <circle
                          className="opacity-25"
                          cx="12"
                          cy="12"
                          r="10"
                          stroke="currentColor"
                          strokeWidth="4"
                        ></circle>
                        <path
                          className="opacity-75"
                          fill="currentColor"
                          d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"
                        ></path>
                      </svg>
                      Creating...
                    </>
                  ) : (
                    'Create Product'
                  )}
                </button>
              </div>
            </form>
          </div>
        )}
      </div>
    </div>
  );
}

export default CreateProductPage;