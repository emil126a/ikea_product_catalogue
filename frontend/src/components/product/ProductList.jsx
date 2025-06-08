function ProductList({ products, onProductClick }) {
  return (
    <div className="overflow-x-auto">
      <table className="min-w-full divide-y divide-gray-200">
        <thead className="bg-gray-50">
          <tr>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">ID</th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Name</th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Type</th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Colors</th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
          </tr>
        </thead>
        <tbody className="bg-white divide-y divide-gray-200">
          {products.map((product) => (
            <tr
              key={`color-${product.id}`}
              className="hover:bg-gray-50 cursor-pointer"
              onClick={() => onProductClick(product.id)}
            >
              <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">{product.id}</td>
              <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{product.name}</td>
              <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                <span className="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-blue-100 text-blue-800">
                  {product.productType.name}
                </span>
              </td>
              <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                <div className="flex space-x-1">
                  {product.colours.map((color) => (
                    <span
                      key={color.id}
                      className="w-4 h-4 rounded-full inline-block"
                      style={{ backgroundColor: color.hexCode || '#ccc' }}
                      title={color.name}
                    />
                  ))}
                </div>
              </td>
              <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                <button
                  onClick={(e) => {
                    e.stopPropagation();
                    onProductClick(product.id);
                  }}
                  className="text-blue-600 hover:text-blue-900"
                >
                  View Details
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default ProductList;