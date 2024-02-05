import {
    BrowserRouter as Router,
    Route,
    Routes,
    Navigate,
  } from "react-router-dom";

import LoginPage from "./pages/login/LoginPage";
import RegisterPage from "./pages/register/RegisterPage";
import NavBar from "./components/nav-bar/Navbar";

const AppRouter = () => {
    return (
        <Router>
            <NavBar />

            <Routes>
                <Route path="/users/login" element={<LoginPage />} />
                <Route path="/users/register" element={<RegisterPage />} />
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
    )
}

export default AppRouter