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
import HomeAdminPage from "./pages/home-admin/HomeAdminPage";
import {useUserStore} from "./store/user-store";



const AppRouter = () => {
    const {user} = useUserStore();
    return (
        <Router>
            <NavBar />

            <Routes>
                <Route path="/" element={<HomePage />} />
                { user.type !== "user" && <Route path="/home" element={<HomePage />} />}
                { user.type === "user" && <Route path="/home" element={<HomeAdminPage />} />}
                <Route path="/users/login" element={<LoginPage />} />
                <Route path="/users/register" element={<RegisterPage />} />
                <Route path="/users/1/profile" element={<UserProfile />} />
                {/* <Route path="/contact-admin" element={<ContactAdminPage />} />
                        <Route
                        path="/home"
                        element={
                            <ProtectedRoute>
                            <HomeAdminPage />
                            </ProtectedRoute>
                        }
            /> */}
            </Routes>
        </Router>
    )
}

export default AppRouter

const ProtectedRoute = ({ children } : any) => {
    const isAuthenticated =
      true; /* Your logic to check if user is authenticated */
    return isAuthenticated ? children : <Navigate to="/users/login" />;
  };