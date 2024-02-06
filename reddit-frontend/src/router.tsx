import {
  BrowserRouter as Router,
  Route,
  Routes,
  Navigate,
} from "react-router-dom";

import LoginPage from "./pages/login/LoginPage";
import RegisterPage from "./pages/register/RegisterPage";
import NavBar from "./components/nav-bar/Navbar";
import HomePage from "./pages/home/HomePage";
import UserProfile from "./pages/user-profile";
import UserManagement from "./pages/user-management";

const AppRouter = () => {
  return (
    <Router>
      <NavBar />

      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/home" element={<HomePage />} />
        <Route path="/users/login" element={<LoginPage />} />
        <Route path="/users/register" element={<RegisterPage />} />
        <Route path="/users/1/profile" element={<UserProfile />} />
        <Route path="/users" element={<UserManagement />} />
        {/* <Route path="/contact-admin" element={<ContactAdminPage />} />
                        <Route
                        path="/home"
                        element={
                            <ProtectedRoute>
                            <HomePage />
                            </ProtectedRoute>
                        }
            /> */}
      </Routes>
    </Router>
  );
};

export default AppRouter;

const ProtectedRoute = ({ children }: any) => {
  const isAuthenticated =
    true; /* Your logic to check if user is authenticated */
  return isAuthenticated ? children : <Navigate to="/users/login" />;
};
