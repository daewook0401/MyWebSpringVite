const Footer = () => {
  return (
    <footer className="bg-white text-gray-800 py-6">
      <div className="max-w-7xl mx-auto px-4">
        <ul className="flex flex-wrap justify-center space-x-6">
          <li>
            <a href="/" className="hover:text-blue-500 transition">
              Home
            </a>
          </li>
          <li>
            <a href="/questions" className="hover:text-blue-500 transition">
              Questions
            </a>
          </li>
          <li>
            <a href="/help" className="hover:text-blue-500 transition">
              Help
            </a>
          </li>
          <li>
            <a href="/faqs" className="hover:text-blue-500 transition">
              FAQs
            </a>
          </li>
          <li>
            <a href="/about" className="hover:text-blue-500 transition">
              About
            </a>
          </li>
        </ul>
      </div>
    </footer>
  );
};

export default Footer;
