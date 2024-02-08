import React from "react";
import { Form, Input, Button, Divider } from "antd";
import {
  UserOutlined,
  MailOutlined,
  LockOutlined,
  GoogleOutlined,
  AppleOutlined,
} from "@ant-design/icons";
import { IRegisterFormValues } from "../../../store/interface";

interface RegisterFormProps {
  onFinish: (values: IRegisterFormValues) => void;
}

const RegisterForm: React.FC<RegisterFormProps> = ({ onFinish }) => {
  const [form] = Form.useForm<IRegisterFormValues>();

  return (
    <>
      <Form form={form} name="register" onFinish={onFinish} scrollToFirstError>
        <Form.Item
          name="firstName"
          rules={[{ required: true, message: "Please input your First Name!" }]}
        >
          <Input prefix={<UserOutlined />} placeholder="First Name" />
        </Form.Item>

        <Form.Item
          name="lastName"
          rules={[{ required: true, message: "Please input your Last Name!" }]}
        >
          <Input prefix={<UserOutlined />} placeholder="Last Name" />
        </Form.Item>

        <Form.Item
          name="email"
          rules={[
            { type: "email", message: "The input is not a valid E-mail!" },
            { required: true, message: "Please input your E-mail!" },
          ]}
        >
          <Input prefix={<MailOutlined />} placeholder="E-mail" />
        </Form.Item>

        <Form.Item
          name="password"
          rules={[{ required: true, message: "Please input your Password!" }]}
          hasFeedback
        >
          <Input.Password prefix={<LockOutlined />} placeholder="Password" />
        </Form.Item>

        <Form.Item>
          <Button type="primary" htmlType="submit" block>
            Register
          </Button>
        </Form.Item>
      </Form>
    </>
  );
};

export default RegisterForm;
