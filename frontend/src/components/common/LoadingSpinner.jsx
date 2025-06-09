function LoadingSpinner({ size = '48px', color = 'blue' }) {
  const sizeClass = `h-[${size}] w-[${size}]`;

  return (
    <div className="flex justify-center items-center h-64">
      <div
        className={`animate-spin rounded-full border-t-2 border-b-2 border-${color}-500`}
        style={{ width: size, height: size }}
      ></div>
    </div>
  );
}

export default LoadingSpinner;
