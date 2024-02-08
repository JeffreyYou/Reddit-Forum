import React from "react";
import { Form, Input, Button, Divider } from "antd";
import {
  LockOutlined,
  MailOutlined,
  GoogleOutlined,
  AppleOutlined,
} from "@ant-design/icons";

interface LoginFormProps {
  onSubmit: (values: ILoginFormValues) => void;
}

const LoginForm: React.FC<LoginFormProps> = ({ onSubmit }) => {
  return (
    <Form
      name="login_form"
      initialValues={{ remember: true }}
      onFinish={onSubmit}
    >
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
  );
};

export default LoginForm;

export interface ILoginFormValues {
  email: string;
  password: string;
}
