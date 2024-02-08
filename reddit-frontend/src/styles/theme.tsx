import { ConfigProvider, theme } from "antd";

const { defaultAlgorithm, darkAlgorithm } = theme;

const themeConfig = (theme: string) => {
  return {
    algorithm: theme === "dark" ? darkAlgorithm : defaultAlgorithm,
    token: {
      // colorPrimary: "#52c41a",
    },
    components: {
      Button: {
        // colorPrimary: "#52c41a",
        algorithm: true,
      },
      Layout: {
        headerBg: "#fff",
      },
      Menu: {
        // itemBg: "#a31313",
        // itemHoverColor: "#a31313",
        
      }
    },
  }
};

export const themeConfigGreen = (theme: string) => {
  return {
    algorithm: theme === "dark" ? darkAlgorithm : defaultAlgorithm,
    token: {
      colorPrimary: "#52c41a",
    },
    components: {
      Button: {
        colorPrimary: "#52c41a",
        algorithm: true,
      },
      Layout: {
        headerBg: "#fff",
      },
      Menu: {
        // itemBg: "#a31313",
        // itemHoverColor: "#a31313",
        
      }
    },
  }
};



export default themeConfig

  