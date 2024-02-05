import NavBar from "./components/Navbar";
import { ConfigProvider } from "antd";
// import ContactAdminPage from "./components/ContactAdminPage";
// import HomePage from "./components/HomePage"; // Assuming you have a HomePage component
import { Navigate } from "react-router-dom";
import AppRouter from "./router";
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
        <AppRouter />

    </ConfigProvider>
  );
};

const ProtectedRoute = ({ children }) => {
  const isAuthenticated =
    true; /* Your logic to check if user is authenticated */
  return isAuthenticated ? children : <Navigate to="/users/login" />;
};

export default App;
