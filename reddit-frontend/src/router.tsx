import {
    BrowserRouter as Router,
    Route,
    Routes,
    Navigate,
  } from "react-router-dom";

import LoginPage from "./components/LoginPage";
import RegisterPage from "./components/RegisterPage";
import NavBar from "./components/Navbar";

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