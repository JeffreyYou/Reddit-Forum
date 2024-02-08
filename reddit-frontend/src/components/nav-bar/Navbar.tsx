import React from "react";
import { Layout, Menu, Input, Button, Row, Col, Switch, Space } from "antd";
import { HomeOutlined, RedditOutlined, BarsOutlined, UserOutlined } from "@ant-design/icons";
import { Link, useNavigate } from "react-router-dom";
import type { MenuProps } from 'antd';
import { useThemeStore } from "../../store/theme-store";

import styles from "./style.module.scss";

type MenuItem = Required<MenuProps>['items'][number];
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
    children?: MenuItem[],
    type?: 'group',
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
    themeProps.theme === 'dark' ? setTheme('light') : setTheme('dark');
  }

  const items: MenuProps['items'] = [

    getItem(<Link to="/home">Home</Link>, 'home', <HomeOutlined style={{fontSize: '16px'}}/>),
    getItem(<Link to="/users/1/profile">Profile</Link>, 'profile', <UserOutlined style={{fontSize: '16px'}}/>),
    getItem("Menu", 'login-menu', <BarsOutlined style={{fontSize: '16px'}}/>, [
      getItem(<Link to="/users/login">Login</Link>, 'login'),
      getItem(<Link to="/users/register">Register</Link>, 'register'),
      getItem(<Link to="/contactus">Contact Us</Link>, 'contact-us'),
      getItem(<Link to="/signout">Signout</Link>, 'signout')]),

  ];



  return (
    <Header className={styles.navbar}>
      <Row align="middle">
        <Col className={styles.title_wrapper}><RedditOutlined className={styles.navbar_logo} /><div className={styles.title}>RedditHub</div></Col>
        <Col flex="auto" className={styles.title}></Col>
        <Col className={styles.navigation}>
          <Menu mode="horizontal" items={items} disabledOverflow={true} style={{ backgroundColor: 'transparent' }}></Menu>
        </Col>
      </Row>
    </Header>
  );
};

export default Navbar;
