import { useState } from 'react';
import { Link, useLocation } from 'react-router-dom';
import {
  ShoppingCartIcon,

  HomeIcon,
  Bars3Icon,
  XMarkIcon
} from '@heroicons/react/24/outline';

function Header() {
  const location = useLocation();
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);

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

          {/* Desktop Navigation (hidden on mobile) */}
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

          {/* Mobile Menu Button (visible only on mobile) */}
          <div className="flex items-center space-x-4">
            <button
              className="md:hidden p-2 rounded-full hover:bg-gray-100"
              onClick={() => setMobileMenuOpen(!mobileMenuOpen)}
            >
              {mobileMenuOpen ? (
                <XMarkIcon className="w-6 h-6 text-gray-600" />
              ) : (
                <Bars3Icon className="w-6 h-6 text-gray-600" />
              )}
            </button>


          </div>
        </div>

        {/* Mobile Menu (visible only on mobile) */}
        {mobileMenuOpen && (
          <div className="md:hidden pt-4 pb-2">
            <nav className="flex flex-col space-y-3">
              <Link
                to="/products"
                onClick={() => setMobileMenuOpen(false)}
                className={`flex items-center space-x-2 px-3 py-2 rounded-lg ${location.pathname === '/products' ? 'bg-blue-50 text-blue-600' : 'text-gray-700 hover:bg-gray-100'}`}
              >
                <HomeIcon className="w-5 h-5" />
                <span>Products</span>
              </Link>
              <Link
                to="/products/new"
                onClick={() => setMobileMenuOpen(false)}
                className={`flex items-center space-x-2 px-3 py-2 rounded-lg ${location.pathname === '/products/new' ? 'bg-blue-50 text-blue-600' : 'text-gray-700 hover:bg-gray-100'}`}
              >
                <ShoppingCartIcon className="w-5 h-5" />
                <span>Add Product</span>
              </Link>
            </nav>
          </div>
        )}
      </div>
    </header>
  );
}

export default Header;