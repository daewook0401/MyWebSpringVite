import { Routes, Route } from "react-router-dom";
import CategoryCreate from "../components/Category/CategoryCreate";

function CategoryPage() {
  return (
    <Routes>
      <Route path="/new" element={<CategoryCreate />} />
    </Routes>
  );
}

export default CategoryPage;