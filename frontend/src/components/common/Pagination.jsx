import { ChevronLeftIcon, ChevronRightIcon } from '@heroicons/react/24/outline';

export default function Pagination({
  currentPage = 0,
  totalPages = 1,
  onPageChange,
  itemsPerPage = 10,
  totalItems = 0,
}) {
  const maxVisiblePages = 5;
  const safeCurrentPage = Number.isFinite(currentPage) && currentPage >= 0 && currentPage < totalPages ? currentPage : 0;
  const safeTotalPages = Number.isFinite(totalPages) && totalPages >= 1 ? totalPages : 1;
  const safeTotalItems = Number.isFinite(totalItems) && totalItems >= 0 ? totalItems : 0;
  const displayPage = safeCurrentPage + 1; // Convert 0-based to 1-based

  let startPage, endPage;
  if (safeTotalPages <= maxVisiblePages) {
    startPage = 1;
    endPage = safeTotalPages;
  } else {
    const maxPagesBeforeCurrent = Math.floor(maxVisiblePages / 2);
    const maxPagesAfterCurrent = Math.ceil(maxVisiblePages / 2) - 1;

    if (displayPage <= maxPagesBeforeCurrent) {
      startPage = 1;
      endPage = maxVisiblePages;
    } else if (displayPage + maxPagesAfterCurrent >= safeTotalPages) {
      startPage = safeTotalPages - maxVisiblePages + 1;
      endPage = safeTotalPages;
    } else {
      startPage = displayPage - maxPagesBeforeCurrent;
      endPage = displayPage + maxPagesAfterCurrent;
    }
  }

  const pages = Array.from({ length: endPage - startPage + 1 }, (_, i) => startPage + i);

  // Calculate the range of items shown
  const startItem = safeCurrentPage * itemsPerPage + 1;
  const endItem = Math.min((safeCurrentPage + 1) * itemsPerPage, safeTotalItems) || startItem;

  console.log(`Pagination: currentPage=${safeCurrentPage}, itemsPerPage=${itemsPerPage}, totalItems=${safeTotalItems}, startItem=${startItem}, endItem=${endItem}`);

  return (
    <div className="flex items-center justify-between border-t border-gray-200 px-4 py-3 sm:px-6">
      <div className="hidden sm:flex sm:flex-1 sm:items-center sm:justify-between">
        <div>
          <p className="text-sm text-gray-700">
            Showing <span className="font-medium">{startItem}</span> to{' '}
            <span className="font-medium">{endItem}</span> of{' '}
            <span className="font-medium">{safeTotalItems}</span> results
          </p>
        </div>
        <div>
          <nav className="isolate inline-flex -space-x-px rounded-md shadow-sm">
            <button
              onClick={() => onPageChange(safeCurrentPage - 1)}
              disabled={safeCurrentPage === 0}
              className="relative inline-flex items-center rounded-l-md px-2 py-2 text-gray-400 ring-1 ring-inset ring-gray-300 hover:bg-gray-100 hover:text-blue-700 hover:ring-blue-500 focus:z-20 focus:outline-offset-0 disabled:opacity-50 group"
            >
              <span className="sr-only">Previous</span>
              <ChevronLeftIcon
                className="h-5 w-5 transition-transform duration-200 group-hover:scale-110 group-hover:text-blue-700 group-disabled:group-hover:scale-100 group-disabled:group-hover:text-gray-400"
                aria-hidden="true"
              />
            </button>

            {startPage > 1 && (
              <>
                <button
                  onClick={() => onPageChange(0)}
                  className="relative inline-flex items-center px-4 py-2 text-sm font-semibold text-gray-900 ring-1 ring-inset ring-gray-300 hover:bg-gray-100 hover:text-blue-700 hover:ring-blue-500 focus:z-20 focus:outline-offset-0"
                >
                  1
                </button>
                {startPage > 2 && (
                  <span className="relative inline-flex items-center px-4 py-2 text-sm font-semibold text-gray-700 ring-1 ring-inset ring-gray-300">
                    ...
                  </span>
                )}
              </>
            )}

            {pages.map((page) => (
              <button
                key={page}
                onClick={() => onPageChange(page - 1)}
                className={`relative inline-flex items-center px-4 py-2 text-sm font-semibold ${
                  safeCurrentPage === page - 1
                    ? 'bg-blue-600 text-white focus:z-20 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-blue-600'
                    : 'text-gray-900 ring-1 ring-inset ring-gray-300 hover:bg-gray-100 hover:text-blue-700 hover:ring-blue-500 focus:z-20 focus:outline-offset-0'
                }`}
              >
                {page}
              </button>
            ))}

            {endPage < safeTotalPages && (
              <>
                {endPage < safeTotalPages - 1 && (
                  <span className="relative inline-flex items-center px-4 py-2 text-sm font-semibold text-gray-700 ring-1 ring-inset ring-gray-300">
                    ...
                  </span>
                )}
                <button
                  onClick={() => onPageChange(safeTotalPages - 1)}
                  className="relative inline-flex items-center px-4 py-2 text-sm font-semibold text-gray-900 ring-1 ring-inset ring-gray-300 hover:bg-gray-100 hover:text-blue-700 hover:ring-blue-500 focus:z-20 focus:outline-offset-0"
                >
                  {safeTotalPages}
                </button>
              </>
            )}

            <button
              onClick={() => onPageChange(safeCurrentPage + 1)}
              disabled={safeCurrentPage === safeTotalPages - 1}
              className="relative inline-flex items-center rounded-r-md px-2 py-2 text-gray-400 ring-1 ring-inset ring-gray-300 hover:bg-gray-100 hover:text-blue-700 hover:ring-blue-500 focus:z-20 focus:outline-offset-0 disabled:opacity-50 group"
            >
              <span className="sr-only">Next</span>
              <ChevronRightIcon
                className="h-5 w-5 transition-transform duration-200 group-hover:scale-110 group-hover:text-blue-700 group-disabled:group-hover:scale-100 group-disabled:group-hover:text-gray-400"
                aria-hidden="true"
              />
            </button>
          </nav>
        </div>
      </div>
    </div>
  );
}