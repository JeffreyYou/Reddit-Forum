import { Form, Input, Button, message, Modal } from "antd";
import { UserOutlined, MailOutlined, LockOutlined } from "@ant-design/icons";
import styles from "./style.module.scss";
import { useNavigate } from "react-router-dom";

const RegisterPage = () => {
  const [form] = Form.useForm();

  //   const [isModalVisible, setIsModalVisible] = useState(true); // You can control the visibility as needed

  const navigate = useNavigate();

  const handleLogInClick = () => {
    navigate("/users/login");
  };

  const onFinish = async (values) => {
    console.log("Received values of form: ", values);
    // Here you would typically make an API call to the backend to handle registration
    // For example:
    // await axios.post('/api/register', values);

    // After calling the API, handle the response accordingly
    // If the email is already in use:
    // message.error('This email is already registered.');

    // If the registration is successful:
    // message.success('Registration successful! Please check your email to verify your account.');
  };

  // return (
  //   <Modal
  //     title="Sign Up"
  //     centered
  //     open={isModalVisible}
  //     footer={null} // No default footer
  //     onCancel={() => setIsModalVisible(false)}
  //   >
  //     <Form form={form} name="register" onFinish={onFinish} scrollToFirstError>
  //       <Form.Item
  //         name="firstName"
  //         rules={[
  //           {
  //             required: true,
  //             message: "Please input your First Name!",
  //           },
  //         ]}
  //       >
  //         <Input
  //           prefix={<UserOutlined className="site-form-item-icon" />}
  //           placeholder="First Name"
  //         />
  //       </Form.Item>

  //       <Form.Item
  //         name="lastName"
  //         rules={[
  //           {
  //             required: true,
  //             message: "Please input your Last Name!",
  //           },
  //         ]}
  //       >
  //         <Input
  //           prefix={<UserOutlined className="site-form-item-icon" />}
  //           placeholder="Last Name"
  //         />
  //       </Form.Item>

  //       <Form.Item
  //         name="email"
  //         rules={[
  //           {
  //             type: "email",
  //             message: "The input is not a valid E-mail!",
  //           },
  //           {
  //             required: true,
  //             message: "Please input your E-mail!",
  //           },
  //         ]}
  //       >
  //         <Input
  //           prefix={<MailOutlined className="site-form-item-icon" />}
  //           placeholder="E-mail"
  //         />
  //       </Form.Item>

  //       <Form.Item
  //         name="password"
  //         rules={[
  //           {
  //             required: true,
  //             message: "Please input your Password!",
  //           },
  //         ]}
  //         hasFeedback
  //       >
  //         <Input.Password
  //           prefix={<LockOutlined className="site-form-item-icon" />}
  //           placeholder="Password"
  //         />
  //       </Form.Item>

  //       <Form.Item>
  //         <Button type="primary" htmlType="submit">
  //           Register
  //         </Button>
  //       </Form.Item>
  //     </Form>
  //   </Modal>
  // );
  return (
    <div className={styles.register_container}>
      <h2>Sign Up</h2>
      <Form form={form} name="register" onFinish={onFinish} scrollToFirstError>
        <Form.Item
          name="firstName"
          rules={[{ required: true, message: "Please input your First Name!" }]}
        >
          <Input
            prefix={<UserOutlined className="site-form-item-icon" />}
            placeholder="First Name"
          />
        </Form.Item>

        <Form.Item
          name="lastName"
          rules={[{ required: true, message: "Please input your Last Name!" }]}
        >
          <Input
            prefix={<UserOutlined className="site-form-item-icon" />}
            placeholder="Last Name"
          />
        </Form.Item>

        <Form.Item
          name="email"
          rules={[
            { type: "email", message: "The input is not a valid E-mail!" },
            { required: true, message: "Please input your E-mail!" },
          ]}
        >
          <Input
            prefix={<MailOutlined className="site-form-item-icon" />}
            placeholder="E-mail"
          />
        </Form.Item>

        <Form.Item
          name="password"
          rules={[{ required: true, message: "Please input your Password!" }]}
          hasFeedback
        >
          <Input.Password
            prefix={<LockOutlined className="site-form-item-icon" />}
            placeholder="Password"
          />
        </Form.Item>

        <Form.Item>
          <Button type="primary" htmlType="submit" block>
            Register
          </Button>
        </Form.Item>
      </Form>
      <div className="additional-links">
        New to RedditHub?
        <a href="/users/login">Log In</a>
      </div>
    </div>
  );
};

export default RegisterPage;
