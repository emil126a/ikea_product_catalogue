function ProductList({ products }) {
  return (
    <table className="min-w-full bg-white border border-gray-300">
      <thead>
        <tr className="bg-gray-100">
          <th className="py-2 px-4 border-b">Product ID</th>
          <th className="py-2 px-4 border-b">Product Name</th>
          <th className="py-2 px-4 border-b">Product Type</th>
          <th className="py-2 px-4 border-b">Colours</th>
        </tr>
      </thead>
      <tbody>
        {products.map((product) => (
          <tr key={product.id} className="hover:bg-gray-50">
            <td className="py-2 px-4 border-b">{product.id}</td>
            <td className="py-2 px-4 border-b">{product.name}</td>
            <td className="py-2 px-4 border-b">{product.productType.name}</td>
            <td className="py-2 px-4 border-b">
              {product.colours.map((c) => c.name).join(', ')}
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}

export default ProductList;