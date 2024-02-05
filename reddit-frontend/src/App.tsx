import { ConfigProvider } from "antd";
import themeConfig from "./styles/theme";
import AppRouter from "./router";


const App = () => {
  return (
    <ConfigProvider theme={themeConfig}>
        <AppRouter />
    </ConfigProvider>
  );
};



export default App;
