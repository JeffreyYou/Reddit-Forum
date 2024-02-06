
import AppRouter from "./router";
import { useThemeStore } from "./store/theme-store";
import { ConfigProvider, theme } from "antd";

import themeConfig from "./styles/theme";
import { themeConfigGreen } from "./styles/theme";

const App = () => {
  
  const { themeProps } = useThemeStore();
  return (
    <ConfigProvider theme={themeConfig(themeProps.theme)}>
    {/* // <ConfigProvider theme={themeConfigGreen(themeProps.theme)}> */}
        <AppRouter />
    </ConfigProvider>
  );
};



export default App;
