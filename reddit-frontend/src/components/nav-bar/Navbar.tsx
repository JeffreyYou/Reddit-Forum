import React from "react";
import { Layout, Menu, Input, Button, Row, Col, Switch } from "antd";
import { HomeOutlined } from "@ant-design/icons";
import { Link, useNavigate } from "react-router-dom";
import type { MenuProps } from "antd";
import { useThemeStore } from "../../store/theme-store";

import styles from "./style.module.scss";

type MenuItem = Required<MenuProps>["items"][number];
const { Header } = Layout;

const Navbar = () => {
  const { themeProps, setTheme } = useThemeStore();

  const navigate = useNavigate();

  const handleLoginClick = () => {
    navigate("/users/login");
  };
  function getItem(
    label: React.ReactNode,
    key: React.Key,
    icon?: React.ReactNode,
    children?: MenuItem,
    type?: "group"
  ): MenuItem {
    return {
      key,
      icon,
      children,
      label,
      type,
    } as MenuItem;
  }

  const toggleTheme = () => {
    themeProps.theme === "dark" ? setTheme("light") : setTheme("dark");
  };
  const items: MenuProps["items"] = [
    getItem(<Switch onChange={toggleTheme} />, "theme"),
    getItem(<Link to="/home">Home</Link>, "home"),
    getItem(<Link to="/users/login">Login</Link>, "login"),
    getItem(<Link to="/users/1/profile">Profile</Link>, "profile"),
    getItem(<Link to="/users">User Management</Link>, "user_management"),
    getItem(<Link to="/users/register">Register</Link>, "register"),
  ];

  return (
    <Header className={styles.navbar}>
      <Row justify="space-between" align="middle">
        <Col>
          <HomeOutlined className={styles.navbar_logo} />
        </Col>
        <Col flex="auto" className={styles.title}>
          RedditHub
        </Col>
        <Col>
          <Menu
            mode="horizontal"
            items={items}
            disabledOverflow={true}
            style={{ backgroundColor: "transparent" }}
          ></Menu>
        </Col>
        <Col>
          <Button type="primary" onClick={handleLoginClick}>
            Log In
          </Button>
        </Col>
      </Row>
    </Header>
  );
};

export default Navbar;
