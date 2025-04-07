const NavItem = ({ children, onClick }) => {
    return (
        <li>
            <div
                onClick={onClick}
                className="cursor-pointer text-gray-700 hover:text-blue-500 px-3 py-2 rounded-md text-lg font-medium transition"
            >
                {children}
            </div>
        </li>
    );
};

export default NavItem;
