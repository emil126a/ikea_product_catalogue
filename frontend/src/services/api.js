import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
});

export const fetchProducts = () => api.get('/products');
export const fetchProductById = (id) => api.get(`/products/${id}`);
export const fetchProductTypes = () => api.get('/product-types');
export const fetchColors = () => api.get('/colors');
export const createProduct = (productData) => api.post('/products', productData);