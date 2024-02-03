import React from "react";
import { Layout, Menu, Input, Button, Row, Col } from "antd";
import { HomeOutlined } from "@ant-design/icons";
import "./Navbar.scss";
import { Link, useNavigate } from "react-router-dom";

const { Header } = Layout;
const { Search } = Input;

const Navbar = () => {
  const navigate = useNavigate();

  const handleLoginClick = () => {
    navigate("/users/login");
  };

  return (
    <Header className="navbar">
      <Row justify="space-between" align="middle">
        <Col>
          {/* Logo and Home Icon could be a link to the home page */}
          <HomeOutlined className="navbar-logo" />
        </Col>
        <Col flex="auto">
          {/* Search bar */}
          {/* <Search placeholder="Search..." style={{ width: 200 }} /> */}
          RedditHub
        </Col>
        <Col>
          {/* Navigation Menu */}
          <Menu mode="horizontal">
            <Menu.Item key="home">
              <Link to="/home">Home</Link>
            </Menu.Item>
            <Menu.Item key="login">
              <Link to="/users/login">Login</Link>
            </Menu.Item>
            <Menu.Item key="register">
              <Link to="/users/register">Register</Link>
            </Menu.Item>
            {/* Add additional navigation links as needed */}
          </Menu>
        </Col>
        <Col>
          {/* Log In button - could be a Link or a button that opens a modal */}
          <Button type="primary" onClick={handleLoginClick}>
            Log In
          </Button>
        </Col>
      </Row>
    </Header>
  );
};

export default Navbar;
