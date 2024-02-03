import React from "react";
import {
  BrowserRouter as Router,
  Route,
  Routes,
  Navigate,
} from "react-router-dom";
import LoginPage from "./components/LoginPage";
import NavBar from "./components/Navbar";
import { ConfigProvider } from "antd";
import RegisterPage from "./components/RegisterPage";
// import ContactAdminPage from "./components/ContactAdminPage";
// import HomePage from "./components/HomePage"; // Assuming you have a HomePage component

const App = () => {
  return (
    <ConfigProvider
      theme={{
        token: {
          // Seed Token，影响范围大
          colorPrimary: "#52c41a",

          // 派生变量，影响范围小
          colorBgContainer: "#f6ffed",
        },
        components: {
          Button: {
            colorPrimary: "#52c41a", // Custom primary color for buttons
            algorithm: true,
          },
          // Input: {
          //   colorPrimary: "#eb2f96", // Custom primary color for inputs
          // },
        },
      }}
    >
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
          {/* Add other routes here */}
        </Routes>
      </Router>
    </ConfigProvider>
  );
};

const ProtectedRoute = ({ children }) => {
  const isAuthenticated =
    true; /* Your logic to check if user is authenticated */
  return isAuthenticated ? children : <Navigate to="/users/login" />;
};

export default App;
