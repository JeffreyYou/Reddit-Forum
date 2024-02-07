import React from 'react';
import { Form, Input, Button, message } from 'antd';
import { useContactAdminStore } from "../../store/contact-admin-store.ts";
import styles from './style.module.scss';

export const ContactAdminForm: React.FC = () => {
    const [form] = Form.useForm();
    const { submitContactAdminRequest } = useContactAdminStore();

    const onFinish = async (values: any) => {
        try {
            const response = await submitContactAdminRequest(values);
            if (response.success) {
                message.success(response.message);
                form.resetFields();
            } else {
                message.error('Failed to submit the request');
            }
        } catch (error) {
            message.error('An error occurred while submitting the request');
        }
    };

    return (
        <Form
            form={form}
            name="contact_admin"
            onFinish={onFinish}
            autoComplete="off"
            layout="vertical"
            className={styles['contact-admin-form']}
        >
            <Form.Item
                label="Subject"
                name="subject"
                rules={[{ required: true, message: 'Please input your subject!' }]}
                className={styles['contact-admin-form-item']}
            >
                <Input />
            </Form.Item>

            <Form.Item
                label="Email"
                name="email"
                rules={[{ required: true, message: 'Please input your email!' }]}
                className={styles['contact-admin-form-item']}
            >
                <Input type="email" />
            </Form.Item>

            <Form.Item
                label="Message"
                name="message"
                rules={[{ required: true, message: 'Please input your message!' }]}
                className={styles['contact-admin-form-item']}
            >
                <Input.TextArea rows={4} />
            </Form.Item>

            <Form.Item className={styles['contact-admin-form-item']}>
                <Button type="primary" htmlType="submit" className={styles['contact-admin-submit-btn']}>
                    Submit
                </Button>
            </Form.Item>
        </Form>
    );
};
