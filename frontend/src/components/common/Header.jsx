import { Link, useLocation } from 'react-router-dom';
import { ShoppingCartIcon, UserIcon, HomeIcon } from '@heroicons/react/24/outline';

function Header() {
  const location = useLocation();

  return (
    <header className="bg-white shadow-sm sticky top-0 z-10">
      <div className="container mx-auto px-4 py-4">
        <div className="flex justify-between items-center">
          {/* Logo/Brand */}
          <Link to="/" className="flex items-center space-x-2">
            <div className="w-10 h-10 bg-gradient-to-r from-blue-600 to-blue-400 rounded-lg flex items-center justify-center">
              <span className="text-white font-bold text-xl">P</span>
            </div>
            <span className="text-xl font-bold text-gray-800 hidden sm:inline">ProductHub</span>
          </Link>

          {/* Navigation */}
          <nav className="hidden md:flex space-x-8">
            <Link
              to="/products"
              className={`flex items-center space-x-1 ${location.pathname === '/products' ? 'text-blue-600' : 'text-gray-600 hover:text-blue-500'}`}
            >
              <HomeIcon className="w-5 h-5" />
              <span>Products</span>
            </Link>
            <Link
              to="/products/new"
              className={`flex items-center space-x-1 ${location.pathname === '/products/new' ? 'text-blue-600' : 'text-gray-600 hover:text-blue-500'}`}
            >
              <ShoppingCartIcon className="w-5 h-5" />
              <span>Add Product</span>
            </Link>
          </nav>

          {/* User/Auth Section */}
          <div className="flex items-center space-x-4">
            <button className="p-2 rounded-full hover:bg-gray-100">
              <UserIcon className="w-5 h-5 text-gray-600" />
            </button>
          </div>
        </div>
      </div>
    </header>
  );
}

export default Header;