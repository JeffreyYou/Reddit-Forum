import React, { useEffect, useState } from "react";
import { Modal, Form, Input, Button, Divider } from "antd";
import { GoogleOutlined, AppleOutlined } from "@ant-design/icons";
import "./LoginPage.scss"; // Make sure to create a corresponding Sass file for styling
import { useNavigate } from "react-router-dom";

const LoginPage = () => {
  //   const [isModalVisible, setIsModalVisible] = useState(true); // You can control the visibility as needed

  const handleSubmit = (values) => {
    console.log("Received values of form: ", values);
    // Place your login logic here
    console.log(values);
  };

  const navigate = useNavigate();

  const handleRegisterClick = () => {
    navigate("/users/register");
  };

  //   return (
  //     <Modal
  //       title="Log In"
  //       centered
  //       open={isModalVisible}
  //       footer={null} // No default footer
  //       onCancel={() => setIsModalVisible(false)}
  //     >
  //       <Form
  //         name="login_form"
  //         initialValues={{ remember: true }}
  //         onFinish={handleSubmit}
  //       >
  //         <Form.Item
  //           name="username"
  //           rules={[{ required: true, message: "Please input your Username!" }]}
  //         >
  //           <Input placeholder="Username" />
  //         </Form.Item>
  //         <Form.Item
  //           name="password"
  //           rules={[{ required: true, message: "Please input your Password!" }]}
  //         >
  //           <Input.Password placeholder="Password" />
  //         </Form.Item>

  //         <Form.Item>
  //           <Button type="primary" htmlType="submit" block>
  //             Log In
  //           </Button>
  //         </Form.Item>

  //         <Divider>OR</Divider>

  //         <Form.Item>
  //           <Button block icon={<GoogleOutlined />}>
  //             Continue with Google
  //           </Button>
  //         </Form.Item>
  //         <Form.Item>
  //           <Button block icon={<AppleOutlined />}>
  //             Continue with Apple
  //           </Button>
  //         </Form.Item>
  //       </Form>
  //       {/* <a href="#">Forgot your username or password?</a> */}
  //       <br />
  //       New to RedditHub?
  //       <a onClick={handleRegisterClick}>Sign Up</a>
  //     </Modal>
  //   );
  return (
    <div className="login-container">
      <h2>Log In</h2>
      <Form
        name="login_form"
        initialValues={{ remember: true }}
        onFinish={handleSubmit}
      >
        <Form.Item
          name="username"
          rules={[{ required: true, message: "Please input your Username!" }]}
        >
          <Input placeholder="Username" />
        </Form.Item>
        <Form.Item
          name="password"
          rules={[{ required: true, message: "Please input your Password!" }]}
        >
          <Input.Password placeholder="Password" />
        </Form.Item>

        <Form.Item>
          <Button type="primary" htmlType="submit" block>
            Log In
          </Button>
        </Form.Item>

        <Divider>OR</Divider>

        <Form.Item>
          <Button block icon={<GoogleOutlined />}>
            Continue with Google
          </Button>
        </Form.Item>
        <Form.Item>
          <Button block icon={<AppleOutlined />}>
            Continue with Apple
          </Button>
        </Form.Item>
      </Form>
      <div className="additional-links">
        New to RedditHub?
        <a href="/users/register">Sign Up</a>
      </div>
    </div>
  );
};

export default LoginPage;
